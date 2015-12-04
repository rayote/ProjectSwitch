package kkook.team.projectswitch.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.kakao.auth.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kkook.team.projectswitch.common.FriendItem;
import kkook.team.projectswitch.R;
import kkook.team.projectswitch.common.SharedApplication;
import kkook.team.projectswitch.util.DataSendTask;

public class MainActivity extends AppCompatActivity {
	RelativeLayout relativeLayout;

	private ArrayList<FriendItem> generalFriend;
	private ArrayList<FriendItem> addedFriend;
	private ArrayList<FriendItem> blockedFriend;

	ImageView imgBtnSwitch;
	TextView textUp;
	TextView textDown;
	TextView textLeft;
	TextView textRight;
	ArrayList<ImageView> alImgBtnSideSwitch;
	float deltaX = 0, deltaY = 0;
	private enum DIRECTION {
		TOP(0), RIGHT(1), BOTTOM(2), LEFT(3);

		private int val;
		private DIRECTION(int val) {
			this.val = val;
		}
		public int value() {
			return this.val;
		}
	};
	final int DEFAULT_SELECTED_MINUTES = 15;
	int selectedMin = DEFAULT_SELECTED_MINUTES;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

/*		generalFriend = new ArrayList<FriendItem>();
		addedFriend = new ArrayList<FriendItem>();
		blockedFriend = new ArrayList<FriendItem>();
*/
		generalFriend =  getIntent().getParcelableArrayListExtra("general");
		addedFriend =  getIntent().getParcelableArrayListExtra("added");
		blockedFriend =  getIntent().getParcelableArrayListExtra("blocked");

		// FIXME:
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
					JSONObject kakaoInfo = new JSONObject();
					kakaoInfo.put("kakao_id", userProfile.getId());
					kakaoInfo.put("nickname", userProfile.getNickname());
					kakaoInfo.put("profile_image", userProfile.getProfileImagePath());
					kakaoInfo.put("thumbnail_image", userProfile.getThumbnailImagePath());

					JSONObject jsonObject = new JSONObject();
					jsonObject.put("op", "insert_member");
//					jsonObject.put("selected_min", getIntent().getIntExtra("selectedMin", -1));
					jsonObject.put("kakao", kakaoInfo);

					TelephonyManager telManager = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
					String phone = telManager.getLine1Number();
					jsonObject.put("phone", phone);

					String gcmUserToken = "";
					if(SharedApplication.GCM_UserToken != null)
						gcmUserToken = SharedApplication.GCM_UserToken;
					jsonObject.put("gcm_user_token", gcmUserToken);

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


		textUp = (TextView) findViewById(R.id.tvBtnSideTop);
		textUp.setTextColor(getResources().getColor(R.color.lightgray));
		textDown = (TextView) findViewById(R.id.tvBtnSideBottom);
		textDown.setTextColor(getResources().getColor(R.color.lightgray));
		textLeft = (TextView) findViewById(R.id.tvBtnSideLeft);
		textLeft.setTextColor(getResources().getColor(R.color.lightgray));
		textRight = (TextView) findViewById(R.id.tvBtnSideRight);
		textRight.setTextColor(getResources().getColor(R.color.lightgray));

		alImgBtnSideSwitch = new ArrayList<ImageView>();
		alImgBtnSideSwitch.add((ImageView) findViewById(R.id.imgBtnSideTop));
		alImgBtnSideSwitch.add((ImageView) findViewById(R.id.imgBtnSideRight));
		alImgBtnSideSwitch.add((ImageView) findViewById(R.id.imgBtnSideBottom));
		alImgBtnSideSwitch.add((ImageView) findViewById(R.id.imgBtnSideLeft));

		relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
		imgBtnSwitch = (ImageView) findViewById(R.id.imgBtnSwitch);
		imgBtnSwitch.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int X = (int) event.getRawX();
				int Y = (int) event.getRawY();
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						((ImageView) v).setImageResource(R.drawable.btn_switch_yellow);
						int initLeftMargin = (relativeLayout.getWidth() - v.getWidth()) / 2;
						int initTopMargin = (relativeLayout.getHeight() - v.getHeight()) / 2;
						layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
						deltaX = X - initLeftMargin;
						deltaY = Y - initTopMargin;
						break;
					case MotionEvent.ACTION_UP:
						//((ImageView) v).setImageResource(R.drawable.bg_switch_gray);
						layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
						v.setLayoutParams(layoutParams);

						Intent i = new Intent(getApplicationContext(), FriendListActivity.class);
						i.putExtra("selectedMin", selectedMin);
						i.putParcelableArrayListExtra("general", generalFriend);
						i.putParcelableArrayListExtra("added", addedFriend);
						i.putParcelableArrayListExtra("blocked", blockedFriend);
						//if(generalFriend != null)
						startActivity(i);
						finish();
						break;
					case MotionEvent.ACTION_MOVE:
						// Drag the center switch only within the gray area
						final int maxMove = 150;
						final int centerPosX = relativeLayout.getWidth() / 2;
						final int centerPosY = relativeLayout.getHeight() / 2;

						final double dist = Math.sqrt(Math.pow(X - centerPosX, 2) + Math.pow(Y - centerPosY, 2));
						final double angleX = Math.acos((double) (X - centerPosX) / dist);
						final double angleY = Math.asin((double) (Y - centerPosY) / dist);

						if (dist > maxMove) {
							X = (int) (centerPosX + Math.cos(angleX) * maxMove);
							Y = (int) (centerPosY + Math.sin(angleY) * maxMove);
						}

						layoutParams.leftMargin = (int) (X - deltaX);
						layoutParams.topMargin = (int) (Y - deltaY);
						v.setLayoutParams(layoutParams);

						// Turn on particular quick slot by dragging the center switch
						// FIXME: get a time value of quick slots from saved preference
//						((TextView) findViewById(R.id.tvBtnSideTop)).setText(String.format("angleX= %f", angleX));
//						((TextView) findViewById(R.id.tvBtnSideBottom)).setText(String.format("angleY= %f", angleY));
						final int minMove = 120;
						if (dist > minMove) {
							if (angleX >= 0 && angleX < 1.6 && angleY < 0) {
								alImgBtnSideSwitch.get(DIRECTION.TOP.value()).setImageResource(R.drawable.bg_arrowbutton_y);
								textUp.setTextColor(getResources().getColor(R.color.yellow));
								selectedMin = 15;
							} else
								textUp.setTextColor(getResources().getColor(R.color.lightgray));
							alImgBtnSideSwitch.get(DIRECTION.TOP.value()).setImageResource(R.drawable.bg_arrowbutton_g);
							if (angleX >= 0 && angleY >= -1.6 && angleY < 0 && !(angleX >= 0 && angleX < 1.6 && angleY < 0)) {
								textRight.setTextColor(getResources().getColor(R.color.yellow));
								alImgBtnSideSwitch.get(DIRECTION.RIGHT.value()).setImageResource(R.drawable.bg_arrowbutton_y);
								selectedMin = 30;
							} else
								textRight.setTextColor(getResources().getColor(R.color.lightgray));
							alImgBtnSideSwitch.get(DIRECTION.RIGHT.value()).setImageResource(R.drawable.bg_arrowbutton_g);
							if (angleX >= 0 && angleX < 1.6 && angleY >= 0) {
								textDown.setTextColor(getResources().getColor(R.color.yellow));
								alImgBtnSideSwitch.get(DIRECTION.BOTTOM.value()).setImageResource(R.drawable.bg_arrowbutton_y);
								selectedMin = 45;
							} else
								textDown.setTextColor(getResources().getColor(R.color.lightgray));
							alImgBtnSideSwitch.get(DIRECTION.BOTTOM.value()).setImageResource(R.drawable.bg_arrowbutton_g);
							if (angleX >= 1.6 && angleY >= 0 && angleY < 1.6) {
								textLeft.setTextColor(getResources().getColor(R.color.yellow));
								alImgBtnSideSwitch.get(DIRECTION.LEFT.value()).setImageResource(R.drawable.bg_arrowbutton_y);
								selectedMin = 60;
							} else
								textLeft.setTextColor(getResources().getColor(R.color.lightgray));
							alImgBtnSideSwitch.get(DIRECTION.LEFT.value()).setImageResource(R.drawable.bg_arrowbutton_g);
						}
						break;
				}

				relativeLayout.invalidate();
				return true;
			}
		});

	}

}
