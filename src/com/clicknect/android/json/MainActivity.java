package com.clicknect.android.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);

		String json = getJSON("http://graph.facebook.com/iamnbty/");
		try {
			
			JSONObject object = new JSONObject(json);
			Log.i("Main Activity", "------------------------------------------------");
			Log.i("Main Activity", "JSON Object with "+object.length()+" items.");
			Log.i("Main Activity", "------------------------------------------------");
			Log.i("Main Activity", "  id: "+object.getString("id"));
			Log.i("Main Activity", "  name: "+object.getString("name"));
			Log.i("Main Activity", "  first_name: "+object.getString("first_name"));
			Log.i("Main Activity", "  last_name: "+object.getString("last_name"));
			Log.i("Main Activity", "  username: "+object.getString("username"));
			Log.i("Main Activity", "  gender: "+object.getString("gender"));
			Log.i("Main Activity", "  locale: "+object.getString("locale"));
			Log.i("Main Activity", "------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getJSON(String url) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e("Get JSON", "Failed to get JSON from the link.");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

}
