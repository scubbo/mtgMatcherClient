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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by jackjack on 28/06/2014.
 */
public class HttpWrapper {
    private static final String TAG = "GCMDemo";

    public void sendPost(String url, Map<String, String> data, final Callbackable<Object, Object> callbackable) {
        new AsyncTask<Object, Void, String>() {

            @Override
            protected String doInBackground(Object[] objects) {
                String url = (String) objects[0];
                Map<String, String> data = (HashMap<String, String>) objects[1];
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);

                List<NameValuePair> params = new ArrayList<NameValuePair>(data.size());
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }

                try {
                    httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    Log.i(TAG, String.valueOf(e.getStackTrace()));
                    e.printStackTrace();
                }

                //Execute and get the response.
                try {
                    HttpResponse response = httpclient.execute(httppost);

                    InputStream is = null;
                    try {
                        is = response.getEntity().getContent();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return sb.toString();

                } catch (IOException e) {
                    Log.i(TAG, String.valueOf(e.getStackTrace()));
                    e.printStackTrace();
                    return null; //necessary for signature
                }
            }

            @Override
            protected void onPostExecute(String response) {
                try {
                    callbackable.pass(response).call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.execute(url, data);
    }

    public void sendPost(String url, Map<String, String> data) {
        sendPost(url,data,Callbackable.getNullCallbackable(String.class,Integer.class));
    }

}
