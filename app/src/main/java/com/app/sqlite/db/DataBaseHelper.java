package com.app.sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.sqlite.db.dao.ClazzD;
import com.app.sqlite.db.dao.CheckinD;
import com.app.sqlite.db.dao.StudentinfoD;
import com.app.sqlite.db.dao.GradesD;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context c, String n, SQLiteDatabase.CursorFactory fac, int ver) {
        super(c, n, fac, ver);
    }

    //数据库创建
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClazzD.CREATE_TABLE);
        db.execSQL(GradesD.CREATE_TABLE);
        db.execSQL(StudentinfoD.CREATE_TABLE);
        db.execSQL(CheckinD.CREATE_TABLE);
    }

    //数据库更新
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
