package kkook.team.projectswitch.util;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import kkook.team.projectswitch.common.SharedApplication;

/**
 * Created by Askai on 2015-12-04.
 */
public class DataSendTask extends AsyncTask<JSONObject, Integer, String> {
	final String TAG = getClass().getSimpleName();
	boolean result;
	Handler handler = null;

	public DataSendTask() {
		this(null);
	}

	public DataSendTask(Handler handler) {
		this.handler = handler;
	}

	@Override
	protected String doInBackground(JSONObject... params) {
		result = false;
		String request, response = null;

		try {
			String op_api = params[0].get("op").toString();
			URL url = new URL(SharedApplication.SERVER_URL + op_api);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
//			conn.setRequestProperty("Accept", "application/json");
//			conn.setConnectTimeout(1000);
			conn.setDoOutput(true);
//			conn.setDoInput(true);

			request = params[0].toString();
			Log.wtf(TAG, "SENT: " + request);
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(request.getBytes("UTF-8"));
			outputStream.flush();

			int resCode = conn.getResponseCode();
			Log.wtf(TAG, "resCode: " + resCode);
			if(resCode == SharedApplication.HTTP_OK) {
				result = true;

				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				InputStream is = conn.getInputStream();
				byte[] byteBuffer = new byte[1024];
				byte[] byteData = null;
				int nLength = 0;
				while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
					baos.write(byteBuffer, 0, nLength);
				}
				byteData = baos.toByteArray();

				response = new String(byteData);
				Log.wtf(TAG, "RECEIVED: " + response);

//				if(response.length() > 0) {
//					JSONObject jsonObject = new JSONObject(response);
//					Boolean result = (Boolean) jsonObject.get("result");
//					String age = (String) jsonObject.get("age");
//				}
			}

			conn.disconnect();

		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), "Sending or receiving JSON occurs Error!!");
			Log.e(getClass().getSimpleName(), e.toString());
			e.printStackTrace();

			result = false;
		}

		return response;
	}

	@Override
	protected void onPostExecute(String response) {
//		super.onPostExecute(aBoolean);
		if(handler != null) {
			Bundle b = new Bundle();
			b.putString("response", response);
			Message msg = new Message();
			msg.setData(b);
			handler.sendMessage(msg);
		}
	}
}
