package com.app.sqlite.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.app.sqlite.R;
import com.app.sqlite.adapter.ScanAllAdapter;
import com.app.sqlite.db.dao.ClazzD;
import com.app.sqlite.db.dao.StudentinfoD;
import com.app.sqlite.db.entity.Clazz;
import com.app.sqlite.db.entity.Studentinfo;
import com.coder.zzq.smartshow.toast.SmartToast;

import java.util.List;

public class ScanAllActivity extends AppCompatActivity {

    private Spinner spinnerClass;
    private RecyclerView recyclerView;

    private ScanAllAdapter mAdapter;

    private StudentinfoD studentinfoD;
    private ClazzD clazzD;
    private List<Clazz> clazzList;
    private String[] array;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_student);

        spinnerClass = findViewById(R.id.spinnerClass);
        recyclerView = findViewById(R.id.recyclerView);

        studentinfoD = StudentinfoD.instance();
        //初始化数据库
        studentinfoD.init(this);

        clazzD = ClazzD.instance();
        clazzD.init(this);

        clazzList = clazzD.queryAll();
        if (clazzList != null && clazzList.size() > 0) {
            //有班级数据
            array = new String[clazzList.size() + 1];
            array[0] = "所有班级";
            for (int i = 0; i < clazzList.size(); i++) {
                array[i + 1] = clazzList.get(i).getName();
            }
            ArrayAdapter adapterClazz = new ArrayAdapter<>(this, R.layout.item_spinner, array);
            spinnerClass.setAdapter(adapterClazz);
        }

        initEvent();


        List<Studentinfo> studentinfoList = studentinfoD.queryAll();
        if (studentinfoList != null && studentinfoList.size() > 0) {
            mAdapter = new ScanAllAdapter(R.layout.item_student, studentinfoList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(mAdapter);
        } else {
            SmartToast.show("暂无学生数据，去添加一个吧");
        }

    }

    private void initEvent() {
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //查询所有学生
                    List<Studentinfo> studentinfoList = studentinfoD.queryAll();
                    if (studentinfoList != null && studentinfoList.size() > 0) {
                        mAdapter = new ScanAllAdapter(R.layout.item_student, studentinfoList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ScanAllActivity.this, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(mAdapter);
                    } else {
                        SmartToast.show("暂无学生数据，去添加一个吧");
                    }
                } else {
                    //查询某个班的学生
                    List<Studentinfo> studentinfoList = studentinfoD.queryAllByClazz(array[position]);
                    if (studentinfoList != null && studentinfoList.size() > 0) {
                        mAdapter = new ScanAllAdapter(R.layout.item_student, studentinfoList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ScanAllActivity.this, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(mAdapter);
                    } else {
                        SmartToast.show("该班级暂无学生数据，去添加一个吧");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
