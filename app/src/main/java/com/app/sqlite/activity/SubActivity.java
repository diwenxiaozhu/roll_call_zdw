package com.app.sqlite.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.app.sqlite.R;
import com.app.sqlite.db.dao.GradesD;
import com.app.sqlite.db.entity.Grades;
import com.coder.zzq.smartshow.toast.SmartToast;

public class SubActivity extends AppCompatActivity {

    int valueTruancy = 1, valueEarly = 1, valueLate = 1, valueExcuse = 1;

    private Spinner spinnerTruancy, spinnerEarly, spinnerLate, spinnerExcuse;
    private Button btnSave;
    private GradesD mScoreDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        spinnerTruancy = findViewById(R.id.spinnerTruancy);
        spinnerEarly = findViewById(R.id.spinnerEarly);
        spinnerLate = findViewById(R.id.spinnerLate);
        spinnerExcuse = findViewById(R.id.spinnerExcuse);
        btnSave = findViewById(R.id.btnSave);

        String[] stringArray = getResources().getStringArray(R.array.sub_score);
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.item_spinner, stringArray);
        spinnerTruancy.setAdapter(adapter);
        spinnerEarly.setAdapter(adapter);
        spinnerLate.setAdapter(adapter);
        spinnerExcuse.setAdapter(adapter);

        mScoreDao = GradesD.instance();
        //初始化数据库
        mScoreDao.init(this);

        Grades grades = mScoreDao.loadOne();
        if (grades != null) {
            //查询到数据

            Log.d("qwe", grades.toString());

            spinnerTruancy.setSelection(grades.getTruancy() - 1, true);
            spinnerEarly.setSelection(grades.getEarly() - 1, true);
            spinnerLate.setSelection(grades.getLate() - 1, true);
            spinnerExcuse.setSelection(grades.getExcuse() - 1, true);

            valueTruancy = grades.getTruancy();
            valueEarly = grades.getEarly();
            valueLate = grades.getLate();
            valueExcuse = grades.getExcuse();
        }

        initEvent();
    }

    private void initEvent() {
        spinnerTruancy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueTruancy = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerEarly.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueEarly = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerLate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueLate = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerExcuse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueExcuse = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(v -> {
            Grades grades = new Grades(1, valueTruancy, valueEarly, valueLate, valueExcuse);
            Log.d("qwe", grades.toString());

            boolean insert = mScoreDao.insert(grades);
            if (insert) {
                SmartToast.show("修改成功");
            } else {
                SmartToast.show("修改失败");
            }
        });
    }
}
