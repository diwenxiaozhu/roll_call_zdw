package com.app.sqlite.adapter;

import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.app.sqlite.R;
import com.app.sqlite.db.entity.Studentinfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ScanAllAdapter extends BaseQuickAdapter<Studentinfo, BaseViewHolder> {

    public ScanAllAdapter(int layoutResId, @Nullable List<Studentinfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Studentinfo item) {
        if (!TextUtils.isEmpty(item.getHeadImage())) {
            ((ImageView) helper.getView(R.id.imageView)).setImageBitmap(BitmapFactory.decodeFile(item.getHeadImage()));
        }
        helper.setText(R.id.tvName, item.getName());
        helper.setText(R.id.tvId, item.getId());
        helper.setText(R.id.tvClazzName, item.getClazz());
    }
}
