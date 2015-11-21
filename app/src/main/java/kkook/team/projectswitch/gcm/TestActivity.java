package kkook.team.projectswitch.gcm;
/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import kkook.team.projectswitch.R;

public class TestActivity extends AppCompatActivity {

	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private static final String TAG = "TestActivity";

	private BroadcastReceiver mRegistrationBroadcastReceiver;
	private ProgressBar mRegistrationProgressBar;
	private TextView mInformationTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_gcm);

		mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
		mRegistrationBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
				SharedPreferences sharedPreferences =
						PreferenceManager.getDefaultSharedPreferences(context);
				boolean sentToken = sharedPreferences
						.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
				if (sentToken) {
					mInformationTextView.setText("Token retrieved and sent to server! You can now use gcmsender to send downstream messages to this app.");
					((Button) findViewById(R.id.btnSend)).setVisibility(View.VISIBLE);
					((EditText) findViewById(R.id.etMessage)).setVisibility(View.VISIBLE);

				} else {
					mInformationTextView.setText("An error occurred while either fetching the InstanceID token, sending the fetched token to the server or subscribing to the PubSub topic. Please try running the sample again.");
				}
			}
		};
		mInformationTextView = (TextView) findViewById(R.id.informationTextView);

		if (checkPlayServices()) {
			// Start IntentService to register this application with GCM.
			Intent intent = new Intent(this, RegistrationIntentService.class);
			startService(intent);

			((Button) findViewById(R.id.btnSend)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					new Thread(runSender).start();
				}
			});
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
				new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
	}

	@Override
	protected void onPause() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
		super.onPause();
	}

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
				finish();
			}
			return false;
		}
		return true;
	}

	private Runnable runSender = new Runnable() {
		@Override
		public void run() {
			String msg = ((EditText) findViewById(R.id.etMessage)).getText().toString();

			try {
				// Prepare JSON containing the GCM message content. What to send and where to send.
				JSONObject jGcmData = new JSONObject();
				JSONObject jData = new JSONObject();
				jData.put("message", msg);
				// Where to send GCM message.
//				if (args.length > 1 && args[1] != null) {
//					jGcmData.put("to", args[1].trim());
//				} else {
					jGcmData.put("to", "/topics/global");
//				}
				// What to send in GCM message.
				jGcmData.put("data", jData);

				// Create connection to send GCM Message request.
				URL url = new URL("https://android.googleapis.com/gcm/send");
				HttpURLConnection conn = null;
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("Authorization", "key=" + getString(R.string.gcm_serverAPIKey));
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);

				// Send GCM message content.
				setTextInfo("Send this message= " + jGcmData.toString());
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(jGcmData.toString().getBytes());

				// Read GCM response.
				InputStream inputStream = conn.getInputStream();
				String resp = IOUtils.toString(inputStream);
				setTextInfo("Response of the server = " + resp + "\n"
						+ "Check your device/emulator for notification or logcat for "
						+ "confirmation of the receipt of the GCM message.");
				Log.i(TAG, resp);

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	private Handler handlerTextInfo = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mInformationTextView.setText(msg.obj.toString());
		}
	};

	private void setTextInfo(String text) {
		handlerTextInfo.sendMessage(Message.obtain(handlerTextInfo, 0, text));
	}
}