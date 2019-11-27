package com.app.sqlite.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.app.sqlite.R;

public class MainActivity extends AppCompatActivity {

    private Button btnSign, btnScanAll, btnAdd, btnOutput, btnSub, btnClazz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSign = findViewById(R.id.btnSign);
        btnScanAll = findViewById(R.id.btnScanAll);
        btnAdd = findViewById(R.id.btnAdd);
        btnOutput = findViewById(R.id.btnOutput);
        btnSub = findViewById(R.id.btnSub);
        btnClazz = findViewById(R.id.btnClazz);

        btnSign.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SignActivity.class)));
        btnScanAll.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ScanAllActivity.class)));
        btnAdd.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddStudentActivity.class)));
        btnOutput.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, OutPutActivity.class)));
        btnSub.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SubActivity.class)));
        btnClazz.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ClazzActivity.class)));

    }
}
