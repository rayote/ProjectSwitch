package kkook.team.projectswitch.view;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;

import kkook.team.projectswitch.common.ManagePlaceholderFragment;
import kkook.team.projectswitch.R;

public class ManageFriendActivity extends AppCompatActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private ManageSectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	private  TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_friend);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_friend);
		setSupportActionBar(toolbar);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new ManageSectionsPagerAdapter(getSupportFragmentManager());

		textView = (TextView)findViewById(R.id.toolbar_friend_tv);
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int state) {
				if (state == 0)
					textView.setText("친구목록");
				else if (state == 1)
					textView.setText("차단목록");

			}
		/*mManageSectionsPagerAdapter = new ManageSectionsPagerAdapter(getSupportFragmentManager());

			textView = (TextView)findViewById(R.id.toolbar_friend_tv);
			// Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) findViewById(R.id.container);
			mViewPager.setAdapter(mManageSectionsPagerAdapter);

			mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

				@Override
				public void onPageSelected(int state) {
					if (state == 0)
						textView.setText("친구목록");
					else if (state == 1)
						textView.setText("차단목록");

				}*/

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

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
		//textView.setText("친구목록 관리");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("친구목록 관리");
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_manage_friend, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (id) {
			case android.R.id.home:
				finish();
				return true;

			case R.id.action_manage_group:
				Intent i = new Intent(getApplicationContext(), ManageFriendGroupActivity.class);
				startActivity(i);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}


	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class ManageSectionsPagerAdapter extends FragmentPagerAdapter {

		public ManageSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a ManagePlaceholderFragment (defined as a static inner class below).
			return ManagePlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return "친구목록";
				case 1:
					return "차단목록";
			}
			return null;
		}
	}
}
