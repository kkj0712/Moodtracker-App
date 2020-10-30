package com.cookandroid.moodtracker.Network;

import android.os.AsyncTask;
import android.util.Log;

import com.cookandroid.moodtracker.Custom_Adapter;
import com.cookandroid.moodtracker.UserInfo;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class NetworkGet extends AsyncTask<String, Void, String> {
    private URL Url;
    private String URL_Adress = "http://10.100.206.53:8888/testDB/testDB.jsp";
    private Custom_Adapter adapter;

    public NetworkGet(Custom_Adapter adapter) {this.adapter = adapter;}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //-------doInBackground--- [](Params -> Result{}
    @Override

    protected String doInBackground(String... strings) {
        String res ="";

        try {
            Url = new URL(URL_Adress);
            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();

            //전송모드 설정
            conn.setDefaultUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            // content-type 설정
            conn.setRequestProperty("Content-type","application/x-www-form-urlencoded; charset=utf-8");

            //전송값 설정
            StringBuffer buffer = new StringBuffer();
            buffer.append("memo").append("=").append(strings[0]);

            //서버로 전송
            OutputStreamWriter outStream = new OutputStreamWriter(conn.getOutputStream(),"utf-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();

            StringBuilder builder = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line=in.readLine()) != null){
                builder.append(line +"\n");
            }
            res = builder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("Get result",res);
        return res; //return Result
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        ArrayList<UserInfo> userList = new ArrayList<UserInfo>(); // 데이터 받을 곳
        int count = 0;
        try {
            count = JsonParser.getUserInfoJson(s, userList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (count == 0) {
        }else {
            adapter.setDatas(userList);
            adapter.notifyDataSetInvalidated();
        }
    }
}
