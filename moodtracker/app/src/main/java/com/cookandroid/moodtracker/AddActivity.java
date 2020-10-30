package com.cookandroid.moodtracker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.moodtracker.Network.NetworkInsert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class AddActivity extends AppCompatActivity {
    private String finDate;
    private String finTime;
    private String finMood;
    private RadioGroup radioGroup;
    private EditText edtMood;
    private Button reset, insert;

    Custom_Adapter adapter;
    Calendar myCalendar=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add);
        updateLabel(); //시간, 날짜 정하기

        radioGroup=(RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);
        edtMood=(EditText) findViewById(R.id.edtMood);

        //취소버튼
        reset=(Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //확인버튼 -> moodInsert() 함수 호출
        insert=(Button) findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                moodInsert();
            }
        });

        //날짜 변경은 myDatePicker 객체를 생성
        final TextView dateTv = (TextView) findViewById(R.id.dateTv);
        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //시간 변경은 TimePickerDialog 이용
        final TextView timeTv = (TextView) findViewById(R.id.timeTv);
        timeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "PM";
                        }
                        // EditText에 출력할 형식 지정
                        timeTv.setText(state + " " + selectedHour + "시 " + selectedMinute + "분");
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    } //onCreate 끝

    //라디오그룹 클릭 리스너
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            //라디오 버튼 id 값에 따라 finMood는 a,b,c,d,e 값으로 변경
            switch (checkedId){
                case R.id.rg_btn1: finMood="a"; break;
                case R.id.rg_btn2: finMood="b"; break;
                case R.id.rg_btn3: finMood="c"; break;
                case R.id.rg_btn4: finMood="d"; break;
                case R.id.rg_btn5: finMood="e"; break;
                default: Toast.makeText(AddActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        }
    };

    //날짜 정하기
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    //시간, 날짜 정하기
    private void updateLabel() {
        //날짜 표시
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        TextView dateTv = (TextView) findViewById(R.id.dateTv);
        dateTv.setText(sdf.format(myCalendar.getTime()));

        finDate=sdf.format(myCalendar.getTime()); //최종 날짜

        //시간 표시
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        String state = "AM";
        if(hour>12){
            hour-=12;
            state = "PM";
        }
        // EditText에 출력할 형식 지정
        TextView timeTv=(TextView) findViewById(R.id.timeTv);
        timeTv.setText(state + " " + hour + "시 " + minute + "분");

        finTime=(state + " " + hour + "시 " + minute + "분"); //최종 시간
    }

    //값 저장 함수
    private void moodInsert(){
        //jsp에서 받는 값과 이름이 일치해야함
        String imagename=finMood;
        String writedate=finDate;
        String writetime=finTime;
        String memo=edtMood.getText().toString();
        //Insert
        new NetworkInsert(adapter).execute(imagename, writedate, writetime, memo);
    }
}