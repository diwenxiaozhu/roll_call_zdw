package com.app.sqlite.adapter;

import android.support.annotation.Nullable;

import com.app.sqlite.R;
import com.app.sqlite.db.entity.Clazz;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ClazzAdapter extends BaseQuickAdapter<Clazz, BaseViewHolder> {

    public ClazzAdapter(int layoutResId, @Nullable List<Clazz> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Clazz item) {
        helper.setText(R.id.tvClazzName, item.getName());
    }
}
