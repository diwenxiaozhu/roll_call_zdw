package com.app.sqlite.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.sqlite.R;
import com.app.sqlite.db.dao.ClazzD;
import com.app.sqlite.db.dao.StudentinfoD;
import com.app.sqlite.db.entity.Clazz;
import com.app.sqlite.db.entity.Studentinfo;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.List;

public class UpdateStudentActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText etName;
    private TextView tvId;
    private Spinner spinnerSex, spinnerClass;
    private Button btnSave;

    private StudentinfoD studentinfoD;
    private ClazzD clazzD;
    private List<Clazz> clazzList;
    private String[] stringArray;
    private String[] array;

    private int sexPosition = 0;
    private int clazzPosition = 0;
    private String imagePath = "";

    private List<LocalMedia> selectList;
    private String studentId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        imageView = findViewById(R.id.imageView);
        etName = findViewById(R.id.etName);
        tvId = findViewById(R.id.tvId);
        spinnerSex = findViewById(R.id.spinnerSex);
        spinnerClass = findViewById(R.id.spinnerClass);
        btnSave = findViewById(R.id.btnSave);

        studentId = getIntent().getStringExtra("studentId");

        tvId.setText(studentId);

        studentinfoD = StudentinfoD.instance();
        //初始化数据库
        studentinfoD.init(this);

        Studentinfo studentinfo = studentinfoD.loadOne(studentId);

        clazzD = ClazzD.instance();
        clazzD.init(this);

        clazzList = clazzD.queryAll();
        if (clazzList != null && clazzList.size() > 0) {
            //有班级数据
            array = new String[clazzList.size()];

            for (int i = 0; i < clazzList.size(); i++) {
                array[i] = clazzList.get(i).getName();
                if (studentinfo.getClazz().equals(clazzList.get(i).getName())) {
                    clazzPosition = i;
                }
            }

            ArrayAdapter adapterClazz = new ArrayAdapter<>(this, R.layout.item_spinner, array);
            spinnerClass.setAdapter(adapterClazz);

        } else {
            SmartToast.show("还没有班级，先去添加一个吧");
        }

        stringArray = getResources().getStringArray(R.array.sex);
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.item_spinner, stringArray);
        spinnerSex.setAdapter(adapter);


        if (studentinfo != null) {
            spinnerClass.setSelection(clazzPosition, true);
            if (studentinfo.getSex().equals("男")) {
                spinnerSex.setSelection(0, true);
            } else {
                spinnerSex.setSelection(1, true);
            }
            imagePath = studentinfo.getHeadImage();

            if (!TextUtils.isEmpty(studentinfo.getHeadImage())) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(studentinfo.getHeadImage()));
            }

        }

        initEvent();

    }

    private void initEvent() {
        spinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("qwe", position + "");
                sexPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("qwe", position + "");
                clazzPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imageView.setOnClickListener(v -> AndPermission.with(UpdateStudentActivity.this)
                .runtime()
                .permission(Permission.Group.STORAGE, Permission.Group.CAMERA)
                .onGranted(permissions -> {
                    selectImage();
                })
                .onDenied(permissions -> {
                    SmartToast.show("请勾选权限");
                })
                .start());

        btnSave.setOnClickListener(v -> {

            if (clazzList != null && clazzList.size() > 0) {
            } else {
                SmartToast.show("还没有班级，先去添加一个吧");
            }

            if (TextUtils.isEmpty(tvId.getText().toString().trim())) {
                SmartToast.show("学号不能为空");
                return;
            }

            if (TextUtils.isEmpty(etName.getText().toString().trim())) {
                SmartToast.show("姓名不能为空");
                return;
            }

            Studentinfo studentinfo;
            if (!TextUtils.isEmpty(imagePath)) {
                studentinfo = new Studentinfo(tvId.getText().toString().trim(),
                        etName.getText().toString().trim(),
                        array[clazzPosition],
                        stringArray[sexPosition],
                        imagePath);
            } else {
                studentinfo = new Studentinfo(tvId.getText().toString().trim(),
                        etName.getText().toString().trim(),
                        array[clazzPosition],
                        stringArray[sexPosition]);
            }

            boolean insert = studentinfoD.update(studentinfo);
            if (insert) {
                SmartToast.show("更新成功");
            } else {
                SmartToast.show("更新失败");
            }
        });
    }

    private void selectImage() {
        File file = new File(Environment.getExternalStorageDirectory() + "");
        if (!file.exists()) {
            file.mkdirs();
        }
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(0)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .glideOverride(220, 220)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                .compressSavePath(file.getPath())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(70)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .cropWH(300, 300)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    Log.d("qwe", "选择照片结果回调:" + selectList.size());
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (selectList.size() == 1) {
                        //选择了照片
                        imagePath = selectList.get(0).getCompressPath();
                        imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
