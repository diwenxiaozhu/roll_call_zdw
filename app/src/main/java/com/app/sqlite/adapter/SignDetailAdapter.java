package com.app.sqlite.adapter;

import android.support.annotation.Nullable;

import com.app.sqlite.R;
import com.app.sqlite.db.entity.Checkin;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class SignDetailAdapter extends BaseQuickAdapter<Checkin, BaseViewHolder> {

    public SignDetailAdapter(int layoutResId, @Nullable List<Checkin> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Checkin item) {
        helper.setText(R.id.tvTime, item.getTime());
        helper.setText(R.id.tvEvent, item.getType());
    }
}
