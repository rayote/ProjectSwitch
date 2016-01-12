package kkook.team.projectswitch.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.kakao.sdk.common.KakaoLoginActivity;

import kkook.team.projectswitch.R;
import kkook.team.projectswitch.gcm.QuickstartPreferences;
import kkook.team.projectswitch.gcm.RegistrationIntentService;

public class SplashActivity extends AppCompatActivity {
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private static final String TAG = "[GCM] SplashActivity";
	private BroadcastReceiver mRegistrationBroadcastReceiver;

	ImageView iv_01;
	ImageView iv_02;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		iv_01 = ((ImageView) findViewById(R.id.imgProjectSwitchIcon));
		iv_02 = ((ImageView) findViewById(R.id.imgProjectSwitch));
		iv_02.setVisibility(View.INVISIBLE);

		iv_01.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha));
		//iv.setImageDrawable(getResources().getDrawable(R.drawable.bg_switch));
		//iv.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate));

//		((TextView) findViewById(R.id.tvTouchToGoNext)).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.sanim.set_blink_fast));

		iv_01.postDelayed(new Runnable() {
			@Override
			public void run() {

				iv_01.setVisibility(View.INVISIBLE);
				iv_02.setVisibility(View.VISIBLE);
				iv_02.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_fast));
				iv_02.postDelayed(new Runnable() {
					@Override
					public void run() {
						startActivity(new Intent(SplashActivity.this, KakaoLoginActivity.class));

						// FIXME: Skip kakao login page
//						startActivity(new Intent(SplashActivity.this, AddFriendActivity.class));
						finish();
					}
				}, 1500);
			}
		}, 500);


		mRegistrationBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				SharedPreferences sharedPreferences =
						PreferenceManager.getDefaultSharedPreferences(context);
				boolean sentToken = sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
				if (sentToken) {
					Log.i(TAG, "Token retrieved and sent to server! You can now use gcmsender to send downstream messages to this app.");

				} else {
					Log.i(TAG, "An error occurred while either fetching the InstanceID token, sending the fetched token to the server or subscribing to the PubSub topic. Please try running the sample again.");
				}
			}
		};
		if (checkPlayServices()) {
			// Start IntentService to register this application with GCM.
			Intent intent = new Intent(this, RegistrationIntentService.class);
			startService(intent);
		}
	}

	/*@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP) {
			startActivity(new Intent(this, KakaoLoginActivity.class));
			finish();
		}
		return super.onTouchEvent(event);
	}*/


	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
		GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
		int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (apiAvailability.isUserResolvableError(resultCode)) {
				apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
						.show();
			} else {
				Log.i(TAG, "This device is not supported.");
//				finish();
			}
			return false;
		}
		return true;
	}
}
