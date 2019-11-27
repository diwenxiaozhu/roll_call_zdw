package com.app.sqlite.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sqlite.R;
import com.app.sqlite.adapter.SignDetailAdapter;
import com.app.sqlite.db.dao.CheckinD;
import com.app.sqlite.db.dao.StudentinfoD;
import com.app.sqlite.db.dao.GradesD;
import com.app.sqlite.db.entity.Checkin;
import com.app.sqlite.db.entity.Studentinfo;
import com.app.sqlite.db.entity.Grades;
import com.coder.zzq.smartshow.dialog.EnsureDialog;
import com.coder.zzq.smartshow.toast.SmartToast;

import java.util.List;

public class SignDetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView tvName, tvId, tvClazzName, tvSex, tv1, tv2, tv3, tv4, tv5;
    private Button btnUpdate, btnDelete;
    private RecyclerView recyclerView;

    private CheckinD checkinD;
    private StudentinfoD studentinfoD;

    private GradesD gradesD;

    private String studentId;
    private SignDetailAdapter adapter;

    private int count1 = 0, count2 = 0, count3 = 0, count4 = 0;
    private Grades grades;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_detail);

        imageView = findViewById(R.id.imageView);
        tvName = findViewById(R.id.tvName);
        tvId = findViewById(R.id.tvId);
        tvClazzName = findViewById(R.id.tvClazzName);
        tvSex = findViewById(R.id.tvSex);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        recyclerView = findViewById(R.id.recyclerView);

        studentId = getIntent().getStringExtra("studentId");

        gradesD = GradesD.instance();
        gradesD.init(this);
        grades = gradesD.loadOne();

        checkinD = CheckinD.instance();
        checkinD.init(this);

        studentinfoD = StudentinfoD.instance();
        studentinfoD.init(this);

        Studentinfo studentinfo = studentinfoD.loadOne(studentId);
        if (!TextUtils.isEmpty(studentinfo.getHeadImage())) {
            imageView.setImageBitmap(BitmapFactory.decodeFile(studentinfo.getHeadImage()));
        }
        tvName.setText(studentinfo.getName());
        tvId.setText(studentinfo.getId());
        tvClazzName.setText(studentinfo.getClazz());
        tvSex.setText(studentinfo.getSex());

        List<Checkin> checkinList = checkinD.queryAllofStudnt(studentId);
        if (checkinList != null && checkinList.size() > 0) {
            //查询到数据
            adapter = new SignDetailAdapter(R.layout.item_sign_detail, checkinList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);

            for (Checkin checkin : checkinList) {
                if (checkin.getType().equals("逃课")) {
                    count1++;
                } else if (checkin.getType().equals("迟到")) {
                    count2++;
                } else if (checkin.getType().equals("请假")) {
                    count3++;
                } else if (checkin.getType().equals("早退")) {
                    count4++;
                }
            }

            int sub = grades.getTruancy() * count1 + grades.getLate() * count2 + grades.getExcuse() * count3 + grades.getEarly() * count4;
            tv5.setText(100 - sub + "");

            tv1.setText(count1 + "");
            tv2.setText(count2 + "");
            tv3.setText(count3 + "");
            tv4.setText(count4 + "");

        } else {
            SmartToast.show("暂无数据");
        }

        initEvent();

    }

    private void initEvent() {
        btnDelete.setOnClickListener(v -> {
            EnsureDialog ensureDialog = new EnsureDialog().message("确定删除该生的考勤信息？")
                    .confirmBtn("确定", (smartDialog, i, o) -> {
                        smartDialog.dismiss();
                        boolean delete = checkinD.delete(studentId);

                        if (delete) {
                            SmartToast.show("删除成功");

                            Studentinfo studentinfo = studentinfoD.loadOne(studentId);
                            if (!TextUtils.isEmpty(studentinfo.getHeadImage())) {
                                imageView.setImageBitmap(BitmapFactory.decodeFile(studentinfo.getHeadImage()));
                            }
                            tvName.setText(studentinfo.getName());
                            tvId.setText(studentinfo.getId());
                            tvClazzName.setText(studentinfo.getClazz());
                            tvSex.setText(studentinfo.getSex());

                            List<Checkin> checkinList = checkinD.queryAllofStudnt(studentId);
                            if (checkinList != null && checkinList.size() > 0) {
                                //查询到数据
                                adapter = new SignDetailAdapter(R.layout.item_sign_detail, checkinList);
                                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                                recyclerView.setAdapter(adapter);

                                for (Checkin sign : checkinList) {
                                    if (sign.getType().equals("逃课")) {
                                        count1++;
                                    } else if (sign.getType().equals("迟到")) {
                                        count2++;
                                    } else if (sign.getType().equals("请假")) {
                                        count3++;
                                    } else if (sign.getType().equals("早退")) {
                                        count4++;
                                    }
                                }

                                int sub = grades.getTruancy() * count1 + grades.getLate() * count2 + grades.getExcuse() * count3 + grades.getEarly() * count4;
                                tv5.setText(100 - sub + "");

                                tv1.setText(count1 + "");
                                tv2.setText(count2 + "");
                                tv3.setText(count3 + "");
                                tv4.setText(count4 + "");

                            } else {
                                recyclerView.setVisibility(View.GONE);
                            }


                        } else {
                            SmartToast.show("删除失败");
                        }
                    });
            ensureDialog.showInActivity(SignDetailActivity.this);
        });

        btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(this, UpdateStudentActivity.class);
            intent.putExtra("studentId", studentId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Studentinfo studentinfo = studentinfoD.loadOne(studentId);
        if (!TextUtils.isEmpty(studentinfo.getHeadImage())) {
            imageView.setImageBitmap(BitmapFactory.decodeFile(studentinfo.getHeadImage()));
        }
        tvName.setText(studentinfo.getName());
        tvId.setText(studentinfo.getId());
        tvClazzName.setText(studentinfo.getClazz());
        tvSex.setText(studentinfo.getSex());
    }
}
