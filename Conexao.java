package com.example.myfirstapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.net.ParseException;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class Conexao extends ListActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		String result = null;
		InputStream is = null;
		StringBuilder sb = null;
		ArrayList nameValuePairs = new ArrayList();
		List r = new ArrayList();
		
		try{
		
			//http post
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://pedrodata.com/android/index.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		}
		catch(Exception e){
			Toast.makeText(getBaseContext(),e.toString() ,Toast.LENGTH_LONG).show();
		}
		
		//Convert response to string
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			
			sb = new StringBuilder();
			
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{	
				sb.append(line + "\n");
			}
		
			is.close();
		
			result = sb.toString();
		}
		catch(Exception e)
		{
			Toast.makeText(getBaseContext(),e.toString() ,Toast.LENGTH_LONG).show();
		}
		//END Convert response to string
		try{
			JSONArray jArray = new JSONArray(result);
			JSONObject json_data=null;
			//for(int i=0;i {
			for(int i = 0; i< jArray.length(); i ++){
				json_data = jArray.getJSONObject(i);
				r.add(json_data.getString("category"));
			}
			setListAdapter(new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, r));
		}
		catch(JSONException e1){
			Toast.makeText(getBaseContext(),e1.toString() ,Toast.LENGTH_LONG).show();
		} catch (ParseException e1) {
			Toast.makeText(getBaseContext(),e1.toString() ,Toast.LENGTH_LONG).show();
		}
	
	}
}
