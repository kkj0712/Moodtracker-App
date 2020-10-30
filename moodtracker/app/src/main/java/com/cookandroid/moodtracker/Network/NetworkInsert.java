package com.cookandroid.moodtracker.Network;

import android.os.AsyncTask;
import android.widget.Toast;

import com.cookandroid.moodtracker.Custom_Adapter;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//사용 new NetworkInsert() execute("id","val2", "val3" ...)  id 값 2,3...
//---------------------------------------Params, Progress, Result
public class NetworkInsert extends AsyncTask<String, Void, String> {
    private URL Url;
    private String URL_Adress = "http://10.100.206.53:8888/testDB/testDB3_insert.jsp";
    private Custom_Adapter adapter;

    public NetworkInsert(Custom_Adapter adapter) {this.adapter = adapter;}

    @Override
    protected void onPreExecute() { super.onPreExecute();}

    //----dbInBackground--- [] (Params) -> Result{}
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

            //content-type 설정
            conn.setRequestProperty("Content-type","application/x-www-form-urlencoded; charset=utf-8");

            //전송값 설정
            StringBuffer buffer = new StringBuffer();
            buffer.append("imagename").append("=").append(strings[0]);
            buffer.append("&writedate").append("=").append(strings[1]);
            buffer.append("&writetime").append("=").append(strings[2]);
            buffer.append("&memo").append("=").append(strings[3]);

            //서버로 전송
            OutputStreamWriter outStream = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();

            StringBuilder builder = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null){
                builder.append(line+"\n");
            }
            res = builder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;  //return Result
    }

    //---[](Result){}

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        int res = 0;

        try {
            res = JsonParser.getResultJson(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (res==0){
            //Result_OK == 0
        } else {
            new NetworkGet(adapter).execute("");
        }
    }
}
