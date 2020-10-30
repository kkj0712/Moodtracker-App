package com.cookandroid.moodtracker.Network;

import com.cookandroid.moodtracker.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {

    static public int getUserInfoJson(String response, ArrayList<UserInfo> userList) throws JSONException {
        String imageNameStr;
        String writeDateStr;
        String writeTimeStr;
        String memoStr;
        int numStr;

        JSONObject rootJSON = new JSONObject(response);
        JSONArray jsonArray = new JSONArray(rootJSON.getString("datas"));

        for (int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObj = (JSONObject)jsonArray.get(i);

            if(jsonObj.getString("imagename").toString().equals("null"))
                imageNameStr="-";
            else
                imageNameStr=jsonObj.getString("imagename").toString();

            if(jsonObj.getString("writedate").toString().equals("null"))
                writeDateStr="-";
            else
                writeDateStr=jsonObj.getString("writedate").toString();

            if(jsonObj.getString("writetime").toString().equals("null"))
                writeTimeStr="-";
            else
                writeTimeStr=jsonObj.getString("writetime").toString();

            if(jsonObj.getString("memo").toString().equals("null"))
                memoStr="-";
            else
                memoStr=jsonObj.getString("memo").toString();

            if(jsonObj.getInt("num")==0)
                numStr=0;
            else
                numStr=jsonObj.getInt("num");

            userList.add(new UserInfo(imageNameStr, writeDateStr, writeTimeStr, memoStr, numStr));
        }
        return jsonArray.length();
    }
    static public int getResultJson(String response) throws JSONException{
        JSONArray jsonArray = new JSONArray(response);
        JSONObject jsonObject = new JSONObject(jsonArray.getString(0));
        return Integer.parseInt(jsonObject.getString("RESULT_OK"));
    }

}
