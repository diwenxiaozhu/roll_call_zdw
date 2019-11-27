package com.app.sqlite.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.sqlite.R;
import com.app.sqlite.adapter.SignAdapter;
import com.app.sqlite.db.dao.ClazzD;
import com.app.sqlite.db.dao.CheckinD;
import com.app.sqlite.db.dao.StudentinfoD;
import com.app.sqlite.db.entity.Clazz;
import com.app.sqlite.db.entity.Checkin;
import com.app.sqlite.db.entity.Studentinfo;
import com.coder.zzq.smartshow.toast.SmartToast;

import java.util.ArrayList;
import java.util.List;

public class SignActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvSubmit;
    private Spinner spinnerClass;
    private RecyclerView recyclerView;

    private ClazzD clazzD;
    private List<Clazz> clazzList;
    private String[] array;

    private StudentinfoD studentinfoD;

    private CheckinD checkinD;

    private SignAdapter mAdapter;
    private int position = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        ivBack = findViewById(R.id.ivBack);
        tvSubmit = findViewById(R.id.tvSubmit);
        spinnerClass = findViewById(R.id.spinnerClass);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


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

        studentinfoD = StudentinfoD.instance();
        //初始化数据库
        studentinfoD.init(this);

        List<Studentinfo> studentinfoList = studentinfoD.queryAll();
        if (studentinfoList != null && studentinfoList.size() > 0) {
            List<Checkin> checkinList = new ArrayList<>();
            for (int i = 0; i < studentinfoList.size(); i++) {
                Checkin checkin = new Checkin(studentinfoList.get(i).getId(),
                        studentinfoList.get(i).getName(),
                        studentinfoList.get(i).getClazz(),
                        studentinfoList.get(i).getSex(),
                        studentinfoList.get(i).getHeadImage());
                checkinList.add(checkin);
            }

            mAdapter = new SignAdapter(SignActivity.this, R.layout.item_sign, checkinList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(mAdapter);
        } else {
            SmartToast.show("暂无学生数据，去添加一个吧");
        }

        checkinD = CheckinD.instance();
        checkinD.init(this);

        initEvent();
    }

    private void initEvent() {
        ivBack.setOnClickListener(v -> finish());

        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SignActivity.this.position = position;
                if (position == 0) {
                    //查询所有学生
                    List<Studentinfo> studentinfoList = studentinfoD.queryAll();
                    if (studentinfoList != null && studentinfoList.size() > 0) {
                        List<Checkin> checkinList = new ArrayList<>();
                        for (int i = 0; i < studentinfoList.size(); i++) {
                            Checkin checkin = new Checkin(studentinfoList.get(i).getId(),
                                    studentinfoList.get(i).getName(),
                                    studentinfoList.get(i).getClazz(),
                                    studentinfoList.get(i).getSex(),
                                    studentinfoList.get(i).getHeadImage());
                            checkinList.add(checkin);
                        }
                        mAdapter = new SignAdapter(SignActivity.this, R.layout.item_sign, checkinList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(SignActivity.this, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(mAdapter);
                    } else {
                        SmartToast.show("暂无学生数据，去添加一个吧");
                    }
                } else {
                    //查询某个班的学生
                    List<Studentinfo> studentinfoList = studentinfoD.queryAllByClazz(array[position]);
                    if (studentinfoList != null && studentinfoList.size() > 0) {
                        List<Checkin> checkinList = new ArrayList<>();
                        for (int i = 0; i < studentinfoList.size(); i++) {
                            Checkin checkin = new Checkin(studentinfoList.get(i).getId(),
                                    studentinfoList.get(i).getName(),
                                    studentinfoList.get(i).getClazz(),
                                    studentinfoList.get(i).getSex(),
                                    studentinfoList.get(i).getHeadImage());
                            checkinList.add(checkin);
                        }
                        mAdapter = new SignAdapter(SignActivity.this, R.layout.item_sign, checkinList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(SignActivity.this, LinearLayoutManager.VERTICAL, false));
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

        tvSubmit.setOnClickListener(v -> {
            if (mAdapter != null && mAdapter.getData().size() > 0) {
                boolean insertList = checkinD.insertList(mAdapter.getData());

                if (insertList) {
                    SmartToast.show("批量添加成功");
                } else {
                    SmartToast.show("批量添加失败");
                }


            } else {
                SmartToast.show("数据状态无变化");
            }
        });
    }
}
