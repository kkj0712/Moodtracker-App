package com.cookandroid.moodtracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cookandroid.moodtracker.Network.NetworkGet;
import com.cookandroid.moodtracker.Network.NetworkInsert;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.ProgressDialog.show;

public class MainActivity extends AppCompatActivity {
    private Button refreshBtn, addBtn, btnSearch;
    private ListView listView;
    Custom_Adapter adapter;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnSearch=(Button) findViewById(R.id.btnSearch);
        edtSearch=(EditText) findViewById(R.id.edtSearch);

        //검색버튼 클릭
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strSearch=edtSearch.getText().toString();
                //검색불러오기
                new NetworkGet((Custom_Adapter) listView.getAdapter()).execute(strSearch);
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        adapter = new Custom_Adapter(MainActivity.this,R.layout.adapter_userinfo, new ArrayList<UserInfo>());
        listView.setAdapter(adapter);

        //새로고침버튼 클릭
        refreshBtn = (Button) findViewById(R.id.btnRefresh);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NetworkGet((Custom_Adapter) listView.getAdapter()).execute(""); //전체불러오기
                Toast.makeText(MainActivity.this, "새로고침", Toast.LENGTH_SHORT).show();
            }
        });

        //추가버튼 클릭
        addBtn = (Button) findViewById(R.id.btn_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });
        //어플 키자마자 전체목록 불러오기
        new NetworkGet((Custom_Adapter) listView.getAdapter()).execute("");
    }
}