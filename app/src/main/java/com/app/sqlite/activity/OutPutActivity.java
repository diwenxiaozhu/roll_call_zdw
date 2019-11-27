package com.app.sqlite.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.app.sqlite.ExcelUtil;
import com.app.sqlite.R;
import com.app.sqlite.db.dao.ClazzD;
import com.app.sqlite.db.dao.CheckinD;
import com.app.sqlite.db.entity.Clazz;
import com.app.sqlite.db.entity.Checkin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OutPutActivity extends AppCompatActivity {

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Spinner spinnerClass;
    private ClazzD clazzD;
    private List<Clazz> clazzList;
    private String[] array;
//    private String filePath = Environment.getExternalStorageDirectory() + "";
    private CheckinD checkinD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_put);
        spinnerClass = findViewById(R.id.spinnerClass);

        checkinD = CheckinD.instance();
        checkinD.init(this);

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

    }

    private void initEvent() {
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    String[] title = {"序号", "学号", "姓名", "时间", "事件"};
                    String sheetName = "Stu_info";
                    List<Checkin> checkinList = checkinD.queryAll();
                    File file = new File(Environment.getExternalStorageDirectory() + "");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    String excelFileName = "/demo" + format.format(new Date()) + ".xls";
                    ExcelUtil.initExcel(Environment.getExternalStorageDirectory() + excelFileName, sheetName, title);
                    ExcelUtil.writeObjListToExcel(checkinList,
                            Environment.getExternalStorageDirectory() + excelFileName,
                            OutPutActivity.this);
                }else {
                    String[] title = {"序号", "学号", "姓名", "时间", "事件"};
                    String sheetName = "sqliteDemo";
                    List<Checkin> checkinList = checkinD.queryAllByClazzName(array[position]);
                    File file = new File(Environment.getExternalStorageDirectory() + "");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    String excelFileName = "/demo" + format.format(new Date()) + ".xls";
                    ExcelUtil.initExcel(Environment.getExternalStorageDirectory() + excelFileName, sheetName, title);
                    ExcelUtil.writeObjListToExcel(checkinList,
                            Environment.getExternalStorageDirectory() + excelFileName,
                            OutPutActivity.this);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
