package com.app.sqlite.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.sqlite.db.DataBaseHelper;
import com.app.sqlite.db.entity.Grades;


public class GradesD {
    /**
     * 表名
     */
    public static final String TABLE_NAME = "SubScore";

    /**
     * 创建 subscore 表的 sql 语句
     */
    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + "id integer primary key,"
            + "truancy integer not null,"
            + "early integer not null,"
            + "late integer not null,"
            + "excuse integer not null"
            + ");";
    /**
     * 数据库的实例
     */
    private SQLiteDatabase mDatabase;

    /**
     * 获取当前类的实例
     */
    public static GradesD instance() {
        return SingletonHolder.mDao;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, "sqlite_demo", null, 1);
        //打开数据库或者创建数据库
        mDatabase = dataBaseHelper.getReadableDatabase();
    }

    /**
     * 插入/更新数据
     *
     * @param grades : 插入的学生
     * @return ：true 成功，false失败
     */
    public boolean insert(Grades grades) {
        //该sql语句的意思是： 有则更新原有的数据，没有则插入新的数据
        String insertSql = "REPLACE INTO " + TABLE_NAME + " (id,truancy,early,late,excuse) VALUES(?,?,?,?,?)";
        Object[] values = {grades.getId(), grades.getTruancy(), grades.getEarly(), grades.getLate(), grades.getExcuse()};
        boolean result;
        try {
            if (mDatabase != null) {
                //非空判断，执行插入语句
                mDatabase.execSQL(insertSql, values);
            }
            result = true;
        } catch (SQLException e) {
            Log.e("qwe", "Grades 插入失败: " + e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 更新数据
     *
     * @param grades
     * @return ：true 更新成功，false 失败
     */
    public boolean update(Grades grades) {
        boolean result;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("truancy", grades.getTruancy());
            contentValues.put("early", grades.getEarly());
            contentValues.put("late", grades.getLate());
            contentValues.put("excuse", grades.getExcuse());
            mDatabase.update(TABLE_NAME, contentValues, "", null);
            result = true;
        } catch (SQLException e) {
            Log.e("qwe", "Grades 更新失败: " + e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 查询
     */
    public Grades loadOne() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        Grades grades = null;
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(sql, new String[]{});
            grades = null;
            int index = 0;
            //判断cursor不是null，并且可以移动到第一条数据
            if (cursor != null && cursor.moveToFirst()) {
                //把cursor移动到第一条数据
                grades = new Grades(
                        cursor.getInt(index),
                        cursor.getInt(index + 1),
                        cursor.getInt(index + 2),
                        cursor.getInt(index + 3),
                        cursor.getInt(index + 4));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("qwe", "Grades 查询失败: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return grades;
    }

    /**
     * 使用静态内部类来实现单例
     */
    private static class SingletonHolder {
        private static GradesD mDao = new GradesD();
    }
}
