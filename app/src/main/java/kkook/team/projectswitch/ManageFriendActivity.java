package kkook.team.projectswitch;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;

public class ManageFriendActivity extends AppCompatActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private ManageSectionsPagerAdapter mManageSectionsPagerAdapter;

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
		mManageSectionsPagerAdapter = new ManageSectionsPagerAdapter(getSupportFragmentManager());

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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class ManagePlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private ListView userListPage;
		private ListItemAdapter adapterManage;
		private TextView tv;
		@Override public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			Item u1 = new Item(getResources().getDrawable(R.drawable.ic_switch_on), "김씨", "010-1234-5678");
			Item u2 = new Item(getResources().getDrawable(R.drawable.ic_switch_on), "이씨", "010-8765-4321");
			Item u3 = new Item(getResources().getDrawable(R.drawable.ic_switch_on), "박씨", "010-0000-0000");

			adapterManage = new ListItemAdapter(getActivity());
			// 리스트뷰 참조 및 Adapter달기
			userListPage = (ListView) getActivity().findViewById(R.id.listViewPage2);
			userListPage.setAdapter(adapterManage);

			// Data 추가
			adapterManage.add(u1);

			adapterManage.add(u2);

			adapterManage.add(u3);

			// Data가 변경 되있음을 알려준다.
			adapterManage.notifyDataSetChanged();
		}
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section
		 * number.
		 */
		public static ManagePlaceholderFragment newInstance(int sectionNumber) {
			ManagePlaceholderFragment fragment = new ManagePlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);

			return fragment;
		}

		public ManagePlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_manage_friend, container, false);
			//tv = (TextView) rootView.findViewById(R.id.toolbar_friend_tv);
			//tv.setText("친구관리");
			return rootView;
		}
	}
}
