package kkook.team.projectswitch.view;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import kkook.team.projectswitch.common.Definition;
import kkook.team.projectswitch.common.FriendItem;
import kkook.team.projectswitch.util.ListItemAdapter;
import kkook.team.projectswitch.R;

public class AddFriendActivity extends AppCompatActivity {

	private ListView userList;
	private ListItemAdapter adapter;
	private Button btn;

	private ArrayList<FriendItem> generalFriend;
	private ArrayList<FriendItem> addedFriend;
	private ArrayList<FriendItem> blockedFriend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friend);


		getSupportActionBar().setTitle("친구 추가하기");

		btn = (Button)findViewById(R.id.btnSkip);
		btn.setText("다음에 하기");

		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				i.putParcelableArrayListExtra("general", generalFriend);
				i.putParcelableArrayListExtra("added", addedFriend);
				i.putParcelableArrayListExtra("blocked", blockedFriend);
				startActivity(i);
				finish();
			}
		});

		// 액션바를 가져옴

		// 뷰를 가져옴..
		View v = getLayoutInflater().inflate(R.layout.custom_actionbar,
				null);

		// 액션바에 커스텀뷰를 설정

		getSupportActionBar().setCustomView(v, new ActionBar.LayoutParams(
				ActionBar.LayoutParams.MATCH_PARENT,
				ActionBar.LayoutParams.MATCH_PARENT));

		//커스텀뷰를 써야하므로 옵션에서 설정
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray)));

		((Button) findViewById(R.id.btnSkip)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
				finish();
			}
		});

		// Data 추가
		FriendItem u1 = new FriendItem(getResources().getDrawable(R.drawable.demo_profile_01), "성덕선", "subtext");
		FriendItem u2 = new FriendItem(getResources().getDrawable(R.drawable.demo_profile_02), "성보라", "subtext");
		FriendItem u3 = new FriendItem(getResources().getDrawable(R.drawable.demo_profile_03), "김정팔", "subtext");

		generalFriend = new ArrayList<FriendItem>();
		addedFriend = new ArrayList<FriendItem>();
		blockedFriend = new ArrayList<FriendItem>();


		generalFriend.add(u1);
		generalFriend.add(u2);
		generalFriend.add(u3);

		adapter = new ListItemAdapter(getApplicationContext(), Definition.ADDFRIEND);
		// 리스트뷰 참조 및 Adapter달기
		userList = (ListView) findViewById(R.id.listView);
		userList.setAdapter(adapter);

		adapter.addArray(generalFriend);

		// Data가 변경 되있음을 알려준다.
		adapter.notifyDataSetChanged();

		userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView parent, View view,
									int position, long id) {
				FriendItem item= adapter.getItem(position);

				if(item.getDataType() == Definition.UNSELETED)
				{
					view.setBackground(getResources().getDrawable(R.drawable.oval_frame_yellow));
					adapter.setSeletedItemNum(1);
					addedFriend.add(item);
					generalFriend.remove(item);
					item.setDataType(Definition.SELETED);
				}
				else if(item.getDataType() == Definition.SELETED)
				{
					view.setBackground(getResources().getDrawable(R.drawable.oval_frame));
					adapter.setSeletedItemNum(-1);
					item.setDataType(Definition.UNSELETED);
					addedFriend.remove(item);
					generalFriend.add(item);
				}


				if (adapter.getnSelectedItemNum() != 0)
					btn.setText("확인");
				else
					btn.setText("다음에 하기");
				//Log.i("testy", "I Clicked on Row " + position + " and it worked!");
			}
		});

	}
}
