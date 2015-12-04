package kkook.team.projectswitch.util;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

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

/**
 * Created by Askai on 2015-12-04.
 */
public class ThreadGCMSender extends Thread {
	private static final String TAG = "[GCM] ThreadGCMSender";
	Context context;
	String msg;

	public ThreadGCMSender(Context context) {
		this(context , null);
	}

	public ThreadGCMSender(Context context, String msg) {
		this.context = context;
		this.msg = msg;
	}

	public void sendMessage(String msg) {
		this.msg = msg;

		start();
	}

	@Override
	public void run() {
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
			conn.setRequestProperty("Authorization", "key=" + context.getString(R.string.gcm_serverAPIKey));
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// Send GCM message content.
			Log.i(TAG, "Send this message= " + jGcmData.toString());
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(jGcmData.toString().getBytes());

			// Read GCM response.
			InputStream inputStream = conn.getInputStream();
			String resp = IOUtils.toString(inputStream);
			Log.i(TAG, "Response of the server = " + resp + "\n"
					+ "Check your device/emulator for notification or logcat for "
					+ "confirmation of the receipt of the GCM message.");

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
