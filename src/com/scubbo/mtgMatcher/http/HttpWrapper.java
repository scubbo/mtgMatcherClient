package com.scubbo.mtgMatcher.http;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jackjack on 28/06/2014.
 */
public class HttpWrapper {
    private static final String TAG = "GCMDemo";

    public void sendPost(String url, Map<String, String> data) {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                String url = (String) objects[0];
                Map<String,String> data = (HashMap<String,String>) objects[1];
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);

                List<NameValuePair> params = new ArrayList<NameValuePair>(data.size());
                for (Map.Entry<String,String> entry: data.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
                }

                try {
                    httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    Log.i(TAG, String.valueOf(e.getStackTrace()));
                    e.printStackTrace();
                }

                //Execute and get the response.
                HttpResponse response = null;
                try {
                    response = httpclient.execute(httppost);
                } catch (IOException e) {
                    Log.i(TAG, String.valueOf(e.getStackTrace()));
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(url, data);

    }
}
