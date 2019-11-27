package com.app.sqlite.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.app.sqlite.R;
import com.app.sqlite.activity.SignDetailActivity;
import com.app.sqlite.db.entity.Checkin;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignAdapter extends BaseQuickAdapter<Checkin, BaseViewHolder> {

    private Context context;
    private List<Checkin> data;

    public SignAdapter(Context context, int layoutResId, @Nullable List<Checkin> data) {
        super(layoutResId, data);
        this.context = context;
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public List<Checkin> getData() {
        return data;
    }

    @Override
    protected void convert(BaseViewHolder helper, Checkin item) {
        if (!TextUtils.isEmpty(item.getHeadImage())) {
            ((ImageView) helper.getView(R.id.imageView)).setImageBitmap(BitmapFactory.decodeFile(item.getHeadImage()));
        }
        helper.setText(R.id.tvName, item.getName());
        helper.setText(R.id.tvId, item.getStudentId());
        helper.setText(R.id.tvClazzName, item.getClazz());
        helper.getView(R.id.content).setOnClickListener(v -> {
            Intent intent = new Intent(context, SignDetailActivity.class);
            intent.putExtra("studentId", item.getStudentId());
            context.startActivity(intent);
        });

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ((RadioGroup) helper.getView(R.id.radioGroup)).setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb1:
                    //出勤
                    item.setType("出勤");
                    item.setTime(format.format(new Date()));
                    addToList(item);
                    break;
                case R.id.rb2:
                    //逃课
                    item.setType("逃课");
                    item.setTime(format.format(new Date()));
                    addToList(item);
                    break;
                case R.id.rb3:
                    //请假
                    item.setType("请假");
                    item.setTime(format.format(new Date()));
                    addToList(item);
                    break;
                case R.id.rb4:
                    //早退
                    item.setType("早退");
                    item.setTime(format.format(new Date()));
                    addToList(item);
                    break;
                case R.id.rb5:
                    //迟到
                    item.setType("迟到");
                    item.setTime(format.format(new Date()));
                    addToList(item);
                    break;
                default:
                    break;
            }
        });
    }

    private void addToList(Checkin item) {
        boolean haseAdd = false;
        if (data.size() == 0) {
            data.add(item);
        } else {
            for (Checkin checkin : data) {
                if (checkin.getStudentId().equals(item.getStudentId())) {
                    //是同一个学生，并且已经添加，同步数据
                    checkin.setType(item.getType());
                    checkin.setTime(item.getTime());
                    haseAdd = true;
                    break;
                }
            }
            if (!haseAdd) {
                data.add(item);
            }
        }
    }

    private int getPosition(Checkin item) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).equals(item)) {
                return i;
            }
        }
        return 0;
    }
}
