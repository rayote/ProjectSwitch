package kkook.team.projectswitch;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.ErrorResult;
import com.kakao.sdk.common.KakaoLoginActivity;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.SimpleTimeZone;

import kkook.team.projectswitch.gcm.TestActivity;

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

	private ArrayList<Item> generalFriend;
	private ArrayList<Item> addedFriend;
	private ArrayList<Item> blockedFriend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_list);

		generalFriend = new ArrayList<Item>();
		addedFriend = new ArrayList<Item>();
		blockedFriend = new ArrayList<Item>();

		generalFriend =  getIntent().getParcelableArrayListExtra("general");
		addedFriend =  getIntent().getParcelableArrayListExtra("added");
		blockedFriend =  getIntent().getParcelableArrayListExtra("blocked");

		int selectedMin= (int) getIntent().getSerializableExtra("selectedMin");


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
		ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBarTime);
		progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_IN);

		int maxTime = selectedMin*60;
		int remainTime = 12*60;

		TextView tv = (TextView)findViewById(R.id.tvTime);
		TextView tv_maxtime = (TextView)findViewById(R.id.maxTime);
		if(remainTime%60 > 10)
			tv.setText(remainTime/60+":"+remainTime%60+":00");
		else
			tv.setText(remainTime/60+":0"+remainTime%60+":00");
		tv_maxtime.setText(maxTime/60+"min.");

		progressBar.setMax(maxTime);
		progressBar.setProgress(remainTime);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);


		adapter = new ListItemAdapter(getApplicationContext(),Definition.ADDFRIENDNAVI);
		// 리스트뷰 참조 및 Adapter달기
		userList = (ListView) findViewById(R.id.listViewNavi);
		userList.setAdapter(adapter);

		if(generalFriend != null)
		adapter.addArray(generalFriend);

		Item u1 = new Item(getResources().getDrawable(R.drawable.demo_profile_01), "성덕선", "subtext");
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
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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

			case R.id.action_test_gcm:
				i = new Intent(getApplicationContext(), TestActivity.class);
				startActivity(i);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		switch (id) {
			case R.id.nav_manage_friend:
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
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
