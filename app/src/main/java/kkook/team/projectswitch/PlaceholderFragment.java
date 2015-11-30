package kkook.team.projectswitch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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

	private ListView userInfoPage;
	private ListItemAdapter adapterInfo;
	Item u1;
	Item u2;
	Item u3;


	@Override public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);


		u1 = new Item(getResources().getDrawable(R.drawable.ic_switch_on), "김씨", "010-1234-5678");
		u2 = new Item(getResources().getDrawable(R.drawable.ic_switch_on), "이씨", "010-8765-4321");
		u3 = new Item(getResources().getDrawable(R.drawable.ic_switch_on), "박씨", "010-0000-0000");


		adapterSendMsg = new ListItemAdapter(getActivity());
		// 리스트뷰 참조 및 Adapter달기
		userListPage = (ListView) getActivity().findViewById(R.id.listViewPage1);
		userListPage.setAdapter(adapterSendMsg);

		// Data 추가
		adapterSendMsg.add(u1);

		adapterSendMsg.add(u2);

		adapterSendMsg.add(u3);

		// Data가 변경 되있음을 알려준다.
		adapterSendMsg.notifyDataSetChanged();



	}
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
		//TextView textView = (TextView) rootView.findViewById(R.id.section_label);
		//textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));



		return rootView;
	}
}