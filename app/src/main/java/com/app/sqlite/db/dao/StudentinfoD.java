package com.app.sqlite.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.sqlite.db.DataBaseHelper;
import com.app.sqlite.db.entity.Studentinfo;

import java.util.ArrayList;
import java.util.List;

public class StudentinfoD {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "Student";

    /**
     * 创建 student 表的 sql 语句
     */
    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + "id text primary key,"
            + "name text not null,"
            + "clazz text not null,"
            + "sex text not null,"
            + "headImage text"
            + ");";
    /**
     * 数据库的实例
     */
    private SQLiteDatabase mDatabase;

    /**
     * 获取当前类的实例
     */
    public static StudentinfoD instance() {
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
     * 插入/更新一个学生
     *
     * @param studentinfo : 插入的学生
     * @return ：true 成功，false失败
     */
    public boolean insert(Studentinfo studentinfo) {
        //该sql语句的意思是： 有则更新原有的数据，没有则插入新的数据
        String insertSql = "INSERT INTO " + TABLE_NAME + " (id,name,clazz,sex,headImage) VALUES(?,?,?,?,?)";
        Object[] values = {studentinfo.getId(), studentinfo.getName(), studentinfo.getClazz(), studentinfo.getClazz(), studentinfo.getHeadImage()};
        boolean result;
        try {
            if (mDatabase != null) {
                //非空判断，执行插入语句
                mDatabase.execSQL(insertSql, values);
            }
            result = true;
        } catch (SQLException e) {
            Log.e("qwe", "Studentinfo 插入失败: " + e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 更新数据
     *
     * @param headImage
     * @return ：true 更新成功，false 失败
     */
    public boolean update(String headImage) {
        boolean result;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("headImage", headImage);
            mDatabase.update(TABLE_NAME, contentValues, "", null);
            result = true;
        } catch (SQLException e) {
            Log.e("qwe", "Studentinfo 更新头像失败: " + e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 更新数据
     *
     * @param studentinfo ：被更新的学生
     * @return ：true 更新成功，false 失败
     */
    public boolean update(Studentinfo studentinfo) {
        boolean result;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("headImage", studentinfo.getHeadImage());
            contentValues.put("clazz", studentinfo.getClazz());
            contentValues.put("name", studentinfo.getName());
            contentValues.put("sex", studentinfo.getSex());
            mDatabase.update(TABLE_NAME, contentValues, "id=?", new String[]{studentinfo.getId()});
            result = true;
        } catch (SQLException e) {
            Log.e("qwe", "Studentinfo 更新头像失败: " + e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 删除数据
     *
     * @return ： 成功 true，否则false
     */
    public boolean delete(String id) {
        String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        boolean result;
        try {
            //建议使用该语句，锻炼Sql语法
            mDatabase.execSQL(deleteSql, new Object[]{id});
            result = true;
        } catch (SQLException e) {
            Log.e("qwe", "Studentinfo 删除失败: " + e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 查询整个表的数据
     *
     * @return ：返回数据的集合
     */
    public List<Studentinfo> queryAll() {
        //游标
        Cursor cursor = null;
        List<Studentinfo> studentinfos = null;
        try {
            cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, new String[]{});
            if (cursor != null) {
                //非null判断
                studentinfos = new ArrayList<>();
                int index = 0;
                //如果cursor可以移动到下一条数据
                while (cursor.moveToNext()) {
                    Studentinfo one = new Studentinfo(
                            cursor.getString(index + 0),
                            cursor.getString(index + 1),
                            cursor.getString(index + 2),
                            cursor.getString(index + 3),
                            cursor.getString(index + 4));
                    studentinfos.add(one);
                }
            }
        } catch (Exception e) {
            Log.e("qwe", "Studentinfo 查询所有数据失败: " + e.getMessage());
        } finally {
            //关闭游标
            if (cursor != null) {
                cursor.close();
            }
        }
        return studentinfos;
    }

    /**
     * 查询某个班的学生
     *
     * @return ：返回数据的集合
     */
    public List<Studentinfo> queryAllByClazz(String clazz) {
        //游标
        Cursor cursor = null;
        List<Studentinfo> studentinfos = null;
        try {
            cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE clazz = ?", new String[]{clazz});
            if (cursor != null) {
                //非null判断
                studentinfos = new ArrayList<>();
                int index = 0;
                //如果cursor可以移动到下一条数据
                while (cursor.moveToNext()) {
                    Studentinfo one = new Studentinfo(
                            cursor.getString(index + 0),
                            cursor.getString(index + 1),
                            cursor.getString(index + 2),
                            cursor.getString(index + 3),
                            cursor.getString(index + 4));
                    studentinfos.add(one);
                }
            }
        } catch (Exception e) {
            Log.e("qwe", "Studentinfo 查询所有数据失败: " + e.getMessage());
        } finally {
            //关闭游标
            if (cursor != null) {
                cursor.close();
            }
        }
        return studentinfos;
    }

    /**
     * 查询一个学生
     *
     * @param studentId
     */
    public Studentinfo loadOne(String studentId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        Studentinfo studentinfo = null;
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(sql, new String[]{studentId});
            studentinfo = null;
            int index = 0;
            //判断cursor不是null，并且可以移动到第一条数据
            if (cursor != null && cursor.moveToFirst()) {
                //把cursor移动到第一条数据
                studentinfo = new Studentinfo(
                        cursor.getString(index + 0),
                        cursor.getString(index + 1),
                        cursor.getString(index + 2),
                        cursor.getString(index + 3),
                        cursor.getString(index + 4));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("qwe", "Studentinfo 查询失败: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return studentinfo;
    }

    /**
     * 使用静态内部类来实现单例
     */
    private static class SingletonHolder {
        private static StudentinfoD mDao = new StudentinfoD();
    }

}
