package kkook.team.projectswitch.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.ErrorResult;
import com.kakao.sdk.common.KakaoLoginActivity;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import android.view.View.OnClickListener;

import kkook.team.projectswitch.common.Definition;
import kkook.team.projectswitch.common.FriendItem;
import kkook.team.projectswitch.common.RoundedAvatarDrawable;
import kkook.team.projectswitch.util.ListItemAdapter;
import kkook.team.projectswitch.R;
import kkook.team.projectswitch.util.SectionsPagerAdapter;
import kkook.team.projectswitch.gcm.TestActivity;
import kkook.team.projectswitch.util.SessionTimer;
import kkook.team.projectswitch.util.Utils;

public class FriendListActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	UserProfile userProfile;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	private ListView userList;
	private ListItemAdapter adapter;

	private TextView textView;

	private ArrayList<FriendItem> generalFriend;
	private ArrayList<FriendItem> addedFriend;
	private ArrayList<FriendItem> blockedFriend;

	private ProgressBar progressBar;
	private SessionTimer sessionTimer;
	private SessionTimerHandler sessionTimerHandler;
	class SessionTimerHandler extends Handler {
		TextView tvTime;
		SessionTimerHandler(TextView tvTime) {
			this.tvTime = tvTime;
		}

		@Override
		public void handleMessage(Message msg) {
//			super.handleMessage(msg);

			int remainTime = msg.what;
			progressBar.setProgress(remainTime);

			int sec = remainTime % 60;
			int min = remainTime / 60;
			int hour = min / 60;

			tvTime.setText(String.format("%02d:%02d:%02d", hour, min, sec));
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_list);

		generalFriend = new ArrayList<FriendItem>();
		addedFriend = new ArrayList<FriendItem>();
		blockedFriend = new ArrayList<FriendItem>();

		generalFriend =  getIntent().getParcelableArrayListExtra("general");
		addedFriend =  getIntent().getParcelableArrayListExtra("added");
		blockedFriend =  getIntent().getParcelableArrayListExtra("blocked");

		int selectedMin = (int) getIntent().getSerializableExtra("selectedMin");


		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

	/*	FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});*/

		ImageButton ib = (ImageButton)findViewById(R.id.magagefriend);
		ib.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), ManageFriendActivity.class);
				startActivity(i);

			}
		});
		progressBar = (ProgressBar)findViewById(R.id.progressBarTime);
//		progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_IN);
		progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.gradient_color));

		int maxTime = selectedMin*60;
		TextView tv_maxtime = (TextView)findViewById(R.id.maxTime);
		tv_maxtime.setText(maxTime / 60 + "min.");

		// Start a session timer
		TextView tvSessionTime = (TextView)findViewById(R.id.tvTime);
		sessionTimerHandler = new SessionTimerHandler(tvSessionTime);
		sessionTimer = new SessionTimer(sessionTimerHandler, progressBar, selectedMin);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);


		adapter = new ListItemAdapter(this, Definition.ADDFRIENDNAVI);
		// 리스트뷰 참조 및 Adapter달기
		userList = (ListView) findViewById(R.id.listViewNavi);
		userList.setAdapter(adapter);

		if(generalFriend != null)
		adapter.addArray(generalFriend);

		FriendItem u1 = new FriendItem(getResources().getDrawable(R.drawable.demo_profile_01), "성덕선", "subtext");
		adapter.add(u1);

		// Data가 변경 되있음을 알려준다.
		adapter.notifyDataSetChanged();

		UserManagement.requestMe(new MeResponseCallback() {
			@Override
			public void onFailure(ErrorResult errorResult) {
				String message = "failed to get user info. msg=" + errorResult;
				Logger.d(message);

//				redirectLoginActivity();
			}

			@Override
			public void onSessionClosed(ErrorResult errorResult) {
//				redirectLoginActivity();
			}

			@Override
			public void onSuccess(UserProfile userProfile) {
				Logger.d("UserProfile : " + userProfile);
				String msg = String.format("Selected minutes before expired= %d",	getIntent().getIntExtra("selectedMin", -1));
				msg += String.format("\n\n[Kakao UserProfile]\nid= %d,\nnickname= %s,\nprofileImage= %s",
						userProfile.getId(), userProfile.getNickname(), userProfile.getProfileImagePath());
//				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

				final String pathThumb = userProfile.getThumbnailImagePath();
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							URL url = new URL(pathThumb);
							URLConnection conn = url.openConnection();
							conn.connect();
							BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
							final Bitmap bm = BitmapFactory.decodeStream(bis);
							bis.close();
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
//									Toast.makeText(getApplicationContext(), "Byte= " + bm.getByteCount(), Toast.LENGTH_SHORT).show();
									((ImageView) findViewById(R.id.ivProfile)).setImageDrawable(new RoundedAvatarDrawable(bm));

									Drawable drawable = new BitmapDrawable(Utils.blur(getApplicationContext(), bm, 6));
									((RelativeLayout) findViewById(R.id.rlProfile)).setBackground(drawable);
								}
							});

						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}

			@Override
			public void onNotSignedUp() {
//				showSignup();
			}




		});

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		textView = (TextView) findViewById(R.id.toolbar_app_tv);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int state) {
				if(state==0)
					textView.setText("SWITCH ON!");
				else if(state==1)
					textView.setText("Interaction Info");

			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int position) {

				//textView.setText("Postion : "+position);
			}
		});


		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(mViewPager);

	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		Intent i;
		switch (id) {
			case  R.id.action_settings:
				i = new Intent(getApplicationContext(), SettingActivity.class);
				startActivity(i);
				return true;

			case R.id.action_kakao_logout:
				UserManagement.requestLogout(new LogoutResponseCallback() {
					@Override
					public void onCompleteLogout() {
//					redirectLoginActivity();
						final Intent intent = new Intent(getApplicationContext(), KakaoLoginActivity.class);
						startActivity(intent);
						finish();
					}
				});
				break;

//			case R.id.action_test_gcm:
//				i = new Intent(getApplicationContext(), TestActivity.class);
//				startActivity(i);
//				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		switch (id) {
	/*		case R.id.nav_manage_friend:
				Intent i = new Intent(getApplicationContext(), ManageFriendActivity.class);
				startActivity(i);
				break;

			case R.id.nav_gallery:
				break;

			case R.id.nav_slideshow:
				break;

			case R.id.nav_manage:
				break;

			case R.id.nav_share:
				break;

			case R.id.nav_send:
				break;
				*/
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
