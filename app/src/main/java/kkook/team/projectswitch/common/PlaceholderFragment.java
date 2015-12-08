package kkook.team.projectswitch.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import kkook.team.projectswitch.R;
import kkook.team.projectswitch.util.ListItemAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private ListView userListPage;
	private ListItemAdapter adapterSendMsg;

	FriendItem u1;
	FriendItem u2;
	FriendItem u3;

	/**
	 * Returns a new instance of this fragment for the given section
	 * number.
	 */
	public static PlaceholderFragment newInstance(int sectionNumber) {
		PlaceholderFragment fragment = new PlaceholderFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);


		return fragment;
	}
/*
	@Override public Object instantiateItem(View pager, int position) {
		View v = null;
		if(position==0){
			v = mInflater.inflate(R.layout.fragment_main1, null);
		}
		else{
			v = mInflater.inflate(R.layout.fragment_main1, null);
		}

		((ViewPager)pager).addView(v, 0);
		return v;
	}
	*/

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = null;
		if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
			rootView = inflater.inflate(R.layout.fragment_main1, container, false);
			u1 = new FriendItem(getResources().getDrawable(R.drawable.ic_switch_on), "김씨", "010-1234-5678");
			u2 = new FriendItem(getResources().getDrawable(R.drawable.ic_switch_on), "이씨", "010-8765-4321");
			u3 = new FriendItem(getResources().getDrawable(R.drawable.ic_switch_on), "박씨", "010-0000-0000");


			adapterSendMsg = new ListItemAdapter(rootView.getContext(), Definition.SENDMSG);
			// 리스트뷰 참조 및 Adapter달기
			userListPage = (ListView) rootView.findViewById(R.id.listViewPage1);
			userListPage.setAdapter(adapterSendMsg);

			// Data 추가
			adapterSendMsg.add(u1);

			adapterSendMsg.add(u2);

			adapterSendMsg.add(u3);

			// Data가 변경 되있음을 알려준다.
			adapterSendMsg.notifyDataSetChanged();

		} else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
			rootView = inflater.inflate(R.layout.fragment_main1, container, false);
			adapterSendMsg = new ListItemAdapter(rootView.getContext(), Definition.INTERACTIONINFO);
			// 리스트뷰 참조 및 Adapter달기
			userListPage = (ListView) rootView.findViewById(R.id.listViewPage1);
			userListPage.setAdapter(adapterSendMsg);


			// Data 추가
			adapterSendMsg.add(new FriendItem(getResources().getDrawable(R.drawable.ic_on_time), "On-Time Setting", ""));
			adapterSendMsg.add(new FriendItem(getResources().getDrawable(R.drawable.ic_term), "Terms & Privacy", ""));
			adapterSendMsg.add(new FriendItem(getResources().getDrawable(R.drawable.ic_contact), "Contact us", ""));
			adapterSendMsg.add(new FriendItem(getResources().getDrawable(R.drawable.ic_drop), "Drop out", ""));
			adapterSendMsg.add(new FriendItem(getResources().getDrawable(R.drawable.ic_sponsor), "Sponsor List", ""));

			// Data가 변경 되있음을 알려준다.
			adapterSendMsg.notifyDataSetChanged();

		}
		//TextView textView = (TextView) rootView.findViewById(R.id.section_label);
		//textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));


		return rootView;
	}

	public ListView getListView() {
		return userListPage;
	}

	public ListItemAdapter getListViewAdapter() {
		return adapterSendMsg;
	}

	public void setListData(FriendItem item) {
		if (adapterSendMsg != null) {
			adapterSendMsg.add(item);
		}
	}
}