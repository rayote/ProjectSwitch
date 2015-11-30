package kkook.team.projectswitch;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AddFriendActivity extends AppCompatActivity {

	private ListView userList;
	private ListItemAdapter adapter;
	private Button btn;
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
		adapter = new ListItemAdapter(getApplicationContext());
		// 리스트뷰 참조 및 Adapter달기
		userList = (ListView) findViewById(R.id.listView);
		userList.setAdapter(adapter);
		// Data 추가
		Item u1 = new Item(getResources().getDrawable(R.drawable.demo_profile_01), "성덕선", "010-1234-5678");
		adapter.add(u1);

		Item u2 = new Item(getResources().getDrawable(R.drawable.demo_profile_02), "성보라", "010-8765-4321");
		adapter.add(u2);

		Item u3 = new Item(getResources().getDrawable(R.drawable.demo_profile_03), "김정팔", "010-0000-0000");
		adapter.add(u3);


		// Data가 변경 되있음을 알려준다.
		adapter.notifyDataSetChanged();

		userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView parent, View view,
									int position, long id) {
				btn.setText("clicked");
				if (adapter.getnSelectedItemNum() != 0)
					btn.setText("확인");
				else
					btn.setText("다음에 하기");
				//Log.i("testy", "I Clicked on Row " + position + " and it worked!");
			}
		});

	}
}

