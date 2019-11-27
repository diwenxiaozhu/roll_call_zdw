package com.app.sqlite.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.app.sqlite.R;
import com.app.sqlite.adapter.ClazzAdapter;
import com.app.sqlite.db.dao.ClazzD;
import com.app.sqlite.db.entity.Clazz;
import com.coder.zzq.smartshow.toast.SmartToast;

import java.util.List;

public class ClazzActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText etClassName;
    private Button btnAdd, btnDelete;

    private ClazzAdapter mAdapter;
    private ClazzD mClazzD;

    private List<Clazz> clazzList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clazz);

        recyclerView = findViewById(R.id.recyclerView);
        etClassName = findViewById(R.id.etClassName);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);

        mClazzD = ClazzD.instance();
        //初始化数据库
        mClazzD.init(this);

        clazzList = mClazzD.queryAll();

        if (clazzList != null && clazzList.size() > 0) {
            //从数据库查询到数据
            mAdapter = new ClazzAdapter(R.layout.item_clazz, clazzList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(mAdapter);
        }

        initEvent();

    }

    private void initEvent() {
        btnAdd.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etClassName.getText().toString().trim())) {
                SmartToast.show("请输入班级名称");
                return;
            }
            boolean insert = mClazzD.insert(new Clazz(etClassName.getText().toString().trim()));
            if (insert) {
                SmartToast.show("添加成功");
                clazzList = mClazzD.queryAll();
                mAdapter = new ClazzAdapter(R.layout.item_clazz, clazzList);
                recyclerView.setLayoutManager(new LinearLayoutManager(ClazzActivity.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(mAdapter);

            }else {
                SmartToast.show("添加失败，该班级已存在");
            }
        });

        btnDelete.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etClassName.getText().toString().trim())) {
                SmartToast.show("请输入班级名称");
                return;
            }
            boolean delete = mClazzD.delete(etClassName.getText().toString().trim());
            if (delete) {
                SmartToast.show("删除成功");
                clazzList = mClazzD.queryAll();
                mAdapter = new ClazzAdapter(R.layout.item_clazz, clazzList);
                recyclerView.setLayoutManager(new LinearLayoutManager(ClazzActivity.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(mAdapter);
            }else {
                SmartToast.show("删除失败，该班级不存在");
            }
        });
    }
}
