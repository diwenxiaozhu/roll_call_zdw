package com.app.sqlite.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.sqlite.db.DataBaseHelper;
import com.app.sqlite.db.entity.Clazz;

import java.util.ArrayList;
import java.util.List;

public class ClazzD {
    public static final String TABLE_NAME = "Clazz";   //表名
    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("    //创建clazz表
            + "name text primary key"
            + ");";
    private SQLiteDatabase mDatabase;    //数据库实例

    public static ClazzD instance() {
        return SingletonHolder.mDao;
    }   //当前类实例
    public void init(Context context) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, "sqlite_demo", null, 1);
        //打开数据库或者创建数据库
        mDatabase = dataBaseHelper.getReadableDatabase();
    }

    public boolean insert(Clazz clazz) {
        //该sql语句的意思是： 有则更新原有的数据，没有则插入新的数据
        String insertSql = "INSERT INTO " + TABLE_NAME + " (name) VALUES(?)";
        Object[] values = {clazz.getName()};
        boolean result;
        try {
            if (mDatabase != null) {
                //非空判断，执行插入语句
                mDatabase.execSQL(insertSql, values);
            }
            result = true;
        } catch (SQLException e) {
            Log.e("qwe", "Clazz 插入失败: " + e.getMessage());
            result = false;
        }
        return result;
    }

    public boolean delete(String name) {
        String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE name = ?";
        boolean result;
        try {
            mDatabase.execSQL(deleteSql, new Object[]{name});
            result = true;
        } catch (SQLException e) {
            Log.e("qwe", "Clazz 删除失败: " + e.getMessage());
            result = false;
        }
        return result;
    }

    public List<Clazz> queryAll() {
        //游标
        Cursor cursor = null;
        List<Clazz> clazzList = null;
        try {
            cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, new String[]{});
            if (cursor != null) {
                //非null判断
                clazzList = new ArrayList<>();
                int index = 0;
                //如果cursor可以移动到下一条数据
                while (cursor.moveToNext()) {
                    Clazz one = new Clazz(cursor.getString(index));
                    clazzList.add(one);
                }
            }
        } catch (Exception e) {
            Log.e("qwe", "Clazz 查询所有数据失败: " + e.getMessage());
        } finally {
            //关闭游标
            if (cursor != null) {
                cursor.close();
            }
        }
        return clazzList;
    }

    private static class SingletonHolder {
        private static ClazzD mDao = new ClazzD();
    }
}
