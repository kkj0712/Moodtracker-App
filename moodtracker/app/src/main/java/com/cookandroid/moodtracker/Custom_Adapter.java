package com.cookandroid.moodtracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.cookandroid.moodtracker.Network.NetworkDelete;
import com.cookandroid.moodtracker.Network.NetworkUpdate;

import org.w3c.dom.Text;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Custom_Adapter extends BaseAdapter {
    private Activity mAct;
    LayoutInflater mInflater;
    ArrayList<UserInfo> mUserInfoObjArr;
    int mListLayout;
    String radioStr="";

    public Custom_Adapter(Activity a, int listLayout, ArrayList<UserInfo> UserInfoObjArrayListT){
        mAct = a;
        mListLayout = listLayout;
        mUserInfoObjArr = UserInfoObjArrayListT;
        mInflater = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDatas(ArrayList<UserInfo> arrayList){
        mUserInfoObjArr = arrayList;
    }

    @Override
    public int getCount() {
        return mUserInfoObjArr.size();
    }

    @Override
    public Object getItem(int i) {
        return mUserInfoObjArr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = mInflater.inflate(mListLayout, viewGroup, false);

        //userinfo.xml의 id 값들을 다른 변수에 담기
        final ImageView tvImageIcon=(ImageView) view.findViewById(R.id.imageIcon);
        final String imageStr=mUserInfoObjArr.get(i).imagename;
        switch (imageStr){
            case "a": tvImageIcon.setImageResource(R.drawable.aplus); break;
            case "b": tvImageIcon.setImageResource(R.drawable.bplus); break;
            case "c": tvImageIcon.setImageResource(R.drawable.cplus); break;
            case "d": tvImageIcon.setImageResource(R.drawable.dplus); break;
            case "e": tvImageIcon.setImageResource(R.drawable.eplus); break;
            default:
        }

        final TextView tvWritedate = (TextView) view.findViewById(R.id.tv_writedate);
        tvWritedate.setText(mUserInfoObjArr.get(i).writedate);

        final TextView tvWritetime = (TextView) view.findViewById(R.id.tv_writetime);
        tvWritetime.setText(mUserInfoObjArr.get(i).writetime);

        final TextView tvMemo = (TextView) view.findViewById(R.id.tv_memo);
        tvMemo.setText(mUserInfoObjArr.get(i).memo);

        final TextView tvNum = (TextView) view.findViewById(R.id.tv_num);
        int su=mUserInfoObjArr.get(i).num;
        tvNum.setText(""+su);

        //수정하기 아이콘을 눌렀을때 dialog_update.xml이 뜸
        Button updateButton = (Button) view.findViewById(R.id.btn_update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View v = mAct.getLayoutInflater().inflate(R.layout.dialog_update,null);
                //dialog_update.xml의 변수들
                final TextView dateTv, timeTv;
                final RadioGroup radioGroup;
                final EditText edtMood;
                final TextView tvNumber;

                TextView dTv=v.findViewById(R.id.dateTv);
                TextView tTv=v.findViewById(R.id.timeTv);
                final RadioGroup rGroup=v.findViewById(R.id.radioGroup);
                EditText eMood=v.findViewById(R.id.edtMood);
                TextView tvN=v.findViewById(R.id.tvNumber);

                //userinfo.xml의 id값의 내용을 가져와서 dialog_update에 뿌림
                //imageStr로 a,b,c,d,e를 가져올 수 있다.
                //a이면 dialog_update의 radioGroup의 rg_btn1이 checked된 상태
                RadioButton rgA=v.findViewById(R.id.rg_btn1);
                RadioButton rgB=v.findViewById(R.id.rg_btn2);
                RadioButton rgC=v.findViewById(R.id.rg_btn3);
                RadioButton rgD=v.findViewById(R.id.rg_btn4);
                RadioButton rgE=v.findViewById(R.id.rg_btn5);

                switch (imageStr){
                    case "a": rgA.setChecked(true); break;
                    case "b": rgB.setChecked(true); break;
                    case "c": rgC.setChecked(true); break;
                    case "d": rgD.setChecked(true); break;
                    case "e": rgE.setChecked(true); break;
                    default:
                }

                dTv.setText(tvWritedate.getText().toString());
                tTv.setText(tvWritetime.getText().toString());
                eMood.setText(tvMemo.getText().toString());
                tvN.setText(tvNum.getText().toString());


                //누른 라디오 그룹의 값을 가져오는 리스너
                rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.rg_btn1: radioStr="a"; break;
                            case R.id.rg_btn2: radioStr="b"; break;
                            case R.id.rg_btn3: radioStr="c"; break;
                            case R.id.rg_btn4: radioStr="d"; break;
                            case R.id.rg_btn5: radioStr="e"; break;
                            default: radioStr="a";
                        }
                    }
                });

                //다이알로그의 확인 버튼을 눌러서 수정
                new AlertDialog.Builder((Context)mAct)
                        .setTitle("수정")
                        .setView(v)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String imagename=radioStr;
                                String writedate = ((TextView)v.findViewById(R.id.dateTv)).getText().toString();
                                String writetime=((TextView)v.findViewById(R.id.timeTv)).getText().toString();
                                String memo = ((EditText)v.findViewById(R.id.edtMood)).getText().toString();
                                String strNumber=((TextView)v.findViewById(R.id.tvNumber)).getText().toString();
                                new NetworkUpdate(Custom_Adapter.this).execute(imagename, writedate, writetime, memo, strNumber);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        });

        //삭제하기 아이콘을 눌렀을때
        Button deleteButton = (Button) view.findViewById(R.id.btn_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String numStr=tvNum.getText().toString();

                AlertDialog.Builder ad = new AlertDialog.Builder(mAct);
                ad.setTitle("삭제하기");
                ad.setMessage("정말 삭제하시겠습니까?");

                ad.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        new NetworkDelete(Custom_Adapter.this).execute(numStr);
                        Toast.makeText(mAct,"삭제되었습니다. 새로고침을 눌러주세요.",Toast.LENGTH_SHORT).show();
                    }
                });
                ad.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(mAct,"취소하였습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                ad.show();
            }
        });
        return view;
    }
}
