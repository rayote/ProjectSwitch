package kkook.team.projectswitch.view;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.kakao.auth.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import kkook.team.projectswitch.common.Definition;
import kkook.team.projectswitch.common.FriendItem;
import kkook.team.projectswitch.common.SharedApplication;
import kkook.team.projectswitch.util.DataSendTask;
import kkook.team.projectswitch.util.ListItemAdapter;
import kkook.team.projectswitch.R;

public class AddFriendActivity extends AppCompatActivity {

	private ListView userList;
	private ListItemAdapter adapter;
	private Button btn;

	private ArrayList<FriendItem> generalFriend;
	private ArrayList<FriendItem> addedFriend;
	private ArrayList<FriendItem> blockedFriend;

	class SignUpHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			String response = msg.getData().getString("response");
			try {
				if(response != null) {
					JSONArray jsonArray = new JSONArray(response);
					if(jsonArray.length() == 0) {
						Log.wtf(getClass().getSimpleName(), "New user is registered.");
						signUp();
					} else {
						Log.wtf(getClass().getSimpleName(), "Current user is already registered.");
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

	class CandidateListHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			String response = msg.getData().getString("response");
			try {
				if(response != null) {
					JSONArray jsonArray = new JSONArray(response);
					Log.wtf(getClass().getSimpleName(), "length= " + jsonArray.length() + ";   " + jsonArray.toString());
					for(int i=0; i<jsonArray.length(); i++) {
						JSONObject obj = jsonArray.getJSONObject(i);

						// TODO: 친구후보 프로필 이미지 경로 받아오기
//						try {
//							URL url = new URL(pathThumb);
//							URLConnection conn = url.openConnection();
//							conn.connect();
//							BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
//							final Bitmap bm = BitmapFactory.decodeStream(bis);
//							bis.close();
//						} catch (MalformedURLException e) {
//							e.printStackTrace();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}

						FriendItem fi = new FriendItem(getResources().getDrawable(R.drawable.demo_profile_01), obj.getString("nickname"), "subtext");
						generalFriend.add(fi);
						Log.wtf(getClass().getSimpleName(), fi.toString());
					}

					adapter.addArray(generalFriend);
					adapter.notifyDataSetChanged();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

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

		// FIXME: 현재 사용자 전화번호를 통해 회원가입 여부 확인 (auth_id 기준으로 변경 필요?)
		TelephonyManager telManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
		String tel = telManager.getLine1Number();
		try {
			JSONObject jsonObject = new JSONObject();
			// Set the suffix of server APIs
			jsonObject.put("op", "/api/v1/friends/candidate");

			jsonObject.put("auth_id", SharedApplication.KakaoUserId);
			jsonObject.put("phone_numbers", tel);

			new DataSendTask(new SignUpHandler()).execute(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// Data 추가
//		FriendItem u1 = new FriendItem(getResources().getDrawable(R.drawable.demo_profile_01), "성덕선", "subtext");
//		FriendItem u2 = new FriendItem(getResources().getDrawable(R.drawable.demo_profile_02), "성보라", "subtext");
//		FriendItem u3 = new FriendItem(getResources().getDrawable(R.drawable.demo_profile_03), "김정팔", "subtext");

		generalFriend = new ArrayList<FriendItem>();
		addedFriend = new ArrayList<FriendItem>();
		blockedFriend = new ArrayList<FriendItem>();

//		generalFriend.add(u1);
//		generalFriend.add(u2);
//		generalFriend.add(u3);

		adapter = new ListItemAdapter(getApplicationContext(), Definition.ADDFRIEND);
		// 리스트뷰 참조 및 Adapter달기
		userList = (ListView) findViewById(R.id.listView);
		userList.setAdapter(adapter);

//		adapter.addArray(generalFriend);

		// Data가 변경 되있음을 알려준다.
//		adapter.notifyDataSetChanged();

		// 주소록 전화번호를 통해 스위치 친구 정보를 요청
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String[] projection = new String[] {
				ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER,
				ContactsContract.CommonDataKinds.Phone.NUMBER };
		String[] selection = null;
		Cursor contactCursor = managedQuery(uri, projection, null, selection, null);
		String phones = "";
		while(contactCursor.moveToNext()) {
			String phone = contactCursor.getString(1);
			phone = phone.replaceAll("[^0-9]", "");
			if(phone.startsWith("+82")) {
				Log.wtf(getClass().getSimpleName(), contactCursor.getString(0) + ",    " + contactCursor.getString(1) + ",    " + phone);
//				break;
			}

			if(contactCursor.getPosition() > 0)		phones += ",";
			phones += phone;
		}

		try {
			JSONObject jsonObject = new JSONObject();
			// Set the suffix of server APIs
			jsonObject.put("op", "/api/v1/friends/candidate");

			jsonObject.put("auth_id", SharedApplication.KakaoUserId);
			jsonObject.put("phone_numbers", phones);

			new DataSendTask(new CandidateListHandler()).execute(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}

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

	private void signUp() {
		// FIXME: 현재 사용자정보를 서버에 등록
//		Toast.makeText(getApplicationContext(), "SharedApplication: " + ((SharedApplication) getApplication()).testStr, Toast.LENGTH_SHORT).show();
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
				try {
//					JSONObject kakaoInfo = new JSONObject();
//					kakaoInfo.put("kakao_id", userProfile.getId());
//					kakaoInfo.put("nickname", userProfile.getNickname());
//					kakaoInfo.put("profile_image", userProfile.getProfileImagePath());
//					kakaoInfo.put("thumbnail_image", userProfile.getThumbnailImagePath());

					JSONObject jsonObject = new JSONObject();
					// Set the suffix of server APIs
					jsonObject.put("op", "/api/v1/signup");
//					jsonObject.put("selected_min", getIntent().getIntExtra("selectedMin", -1));
//					jsonObject.put("kakao", kakaoInfo);

					TelephonyManager telManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
					String phone = telManager.getLine1Number();
//					if(phone.length() > 10)
//						phone = String.format("%s-%s-%s", phone.substring(0, 3), phone.substring(3, 7), phone.substring(7));
//					phone = phone.replaceAll("[^0-9]", "");
					jsonObject.put("phone_number", phone);

					String gcmUserToken = "";
					if (SharedApplication.GCM_UserToken != null)
						gcmUserToken = SharedApplication.GCM_UserToken;
					jsonObject.put("gcm_user_token", gcmUserToken);

					// Initial information from Kakao account
					if (SharedApplication.KakaoUserId == null)
						SharedApplication.KakaoUserId = Long.toString(userProfile.getId());
					String auth_id = SharedApplication.KakaoUserId;
					jsonObject.put("auth_id", auth_id);
					jsonObject.put("nickname", userProfile.getNickname());
					jsonObject.put("thumbnail_image", userProfile.getThumbnailImagePath());
					jsonObject.put("profile_image", userProfile.getProfileImagePath());

					new DataSendTask().execute(jsonObject);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onNotSignedUp() {
//				showSignup();
			}
		});
	}
}
