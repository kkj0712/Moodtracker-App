package com.cookandroid.moodtracker;

public class UserInfo {
    String imagename, writedate, writetime, memo;
    int num;
    public UserInfo(String imagename, String writedate, String writetime, String memo, int num){
        this.imagename = imagename;
        this.writedate = writedate;
        this.writetime = writetime;
        this.memo = memo;
        this.num=num;
    }
}
