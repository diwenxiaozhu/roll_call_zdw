package com.app.sqlite.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.sqlite.db.DataBaseHelper;
import com.app.sqlite.db.entity.Checkin;

import java.util.ArrayList;
import java.util.List;

public class CheckinD {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "Sign";

    /**
     * 创建 sign 表的 sql 语句
     */
    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + "id integer primary key autoincrement,"
            + "studentId text not null,"
            + "name text not null,"
            + "clazz text not null,"
            + "sex text not null,"
            + "time text not null,"
            + "event text not null,"
            + "headImage text"
            + ");";
    /**
     * 数据库的实例
     */
    private SQLiteDatabase mDatabase;

    /**
     * 获取当前类的实例
     */
    public static CheckinD instance() {
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
     * 插入/更新一条考勤记录
     *
     * @param checkin : 插入考勤记录
     * @return ：true 成功，false失败
     */
    public boolean insert(Checkin checkin) {
        //该sql语句的意思是： 有则更新原有的数据，没有则插入新的数据
        String insertSql = "INSERT OR REPLACE INTO " + TABLE_NAME + " (studentId,name,clazz,sex,time,event,headImage) VALUES(?,?,?,?,?,?,?)";
        Object[] values = {checkin.getStudentId(), checkin.getName(), checkin.getClazz(), checkin.getClazz(), checkin.getTime(), checkin.getType(), checkin.getHeadImage()};
        boolean result;
        try {
            if (mDatabase != null) {
                //非空判断，执行插入语句
                mDatabase.execSQL(insertSql, values);
            }
            result = true;
        } catch (SQLException e) {
            Log.e("qwe", "Checkin 插入失败: " + e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 插入多条数据
     *
     * @param checkinList
     * @return
     */
    public boolean insertList(List<Checkin> checkinList) {
        boolean result;
        mDatabase.beginTransaction();
        try {
            for (Checkin checkin : checkinList) {
                Object[] args = {checkin.getStudentId(), checkin.getName(), checkin.getClazz(),
                        checkin.getSex(), checkin.getTime(), checkin.getType(), checkin.getHeadImage()};
                mDatabase.execSQL("INSERT INTO " + TABLE_NAME + " (studentId,name,clazz,sex,time,event,headImage) VALUES (?,?,?,?,?,?,?);", args);
            }
            //设置事物操作成功
            mDatabase.setTransactionSuccessful();
            result = true;
        } catch (SQLException e) {
            Log.e("qwe", "Checkin 批量插入失败: " + e.getMessage());
            result = false;
        } finally {
            if (mDatabase != null) {
                //结束事物
                mDatabase.endTransaction();
            }
        }
        return result;
    }

    /**
     * 删除数据一个学生的考勤记录
     *
     * @return ： 成功 true，否则false
     */
    public boolean delete(String studentId) {
        String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE studentId = ?";
        //db 提供的删除的方法
        boolean result;
        try {
            //建议使用该语句，锻炼Sql语法
            mDatabase.execSQL(deleteSql, new Object[]{studentId});
            result = true;
        } catch (SQLException e) {
            Log.e("qwe", "Checkin 删除失败: " + e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 查询某个学生的所有考勤记录
     *
     * @return ：返回数据的集合
     */
    public List<Checkin> queryAllofStudnt(String studentId) {
        //游标
        Cursor cursor = null;
        List<Checkin> checkinList = null;
        try {
            cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE studentId = ?", new String[]{studentId});
            if (cursor != null) {
                //非null判断
                checkinList = new ArrayList<>();
                int index = 0;
                //如果cursor可以移动到下一条数据
                while (cursor.moveToNext()) {
                    Checkin one = new Checkin(
                            cursor.getInt(index + 0),
                            cursor.getString(index + 1),
                            cursor.getString(index + 2),
                            cursor.getString(index + 3),
                            cursor.getString(index + 4),
                            cursor.getString(index + 7),
                            cursor.getString(index + 5),
                            cursor.getString(index + 6));
                    checkinList.add(one);
                }
            }
        } catch (Exception e) {
            Log.e("qwe", "Checkin 查询所有数据失败: " + e.getMessage());
        } finally {
            //关闭游标
            if (cursor != null) {
                cursor.close();
            }
        }
        return checkinList;
    }

    /**
     * 查询某个学生的所有考勤记录
     *
     * @return ：返回数据的集合
     */
    public List<Checkin> queryAll() {
        //游标
        Cursor cursor = null;
        List<Checkin> checkinList = null;
        try {
            cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, new String[]{});
            if (cursor != null) {
                //非null判断
                checkinList = new ArrayList<>();
                int index = 0;
                //如果cursor可以移动到下一条数据
                while (cursor.moveToNext()) {
                    Checkin one = new Checkin(
                            cursor.getInt(index + 0),
                            cursor.getString(index + 1),
                            cursor.getString(index + 2),
                            cursor.getString(index + 3),
                            cursor.getString(index + 4),
                            cursor.getString(index + 7),
                            cursor.getString(index + 5),
                            cursor.getString(index + 6));
                    checkinList.add(one);
                }
            }
        } catch (Exception e) {
            Log.e("qwe", "Checkin 查询所有数据失败: " + e.getMessage());
        } finally {
            //关闭游标
            if (cursor != null) {
                cursor.close();
            }
        }
        return checkinList;
    }

    /**
     * 查询某班学生的所有考勤记录
     *
     * @return ：返回数据的集合
     */
    public List<Checkin> queryAllByClazzName(String clazzName) {
        //游标
        Cursor cursor = null;
        List<Checkin> checkinList = null;
        try {
            cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE clazz = ?", new String[]{clazzName});
            if (cursor != null) {
                //非null判断
                checkinList = new ArrayList<>();
                int index = 0;
                //如果cursor可以移动到下一条数据
                while (cursor.moveToNext()) {
                    Checkin one = new Checkin(
                            cursor.getInt(index + 0),
                            cursor.getString(index + 1),
                            cursor.getString(index + 2),
                            cursor.getString(index + 3),
                            cursor.getString(index + 4),
                            cursor.getString(index + 7),
                            cursor.getString(index + 5),
                            cursor.getString(index + 6));
                    checkinList.add(one);
                }
            }
        } catch (Exception e) {
            Log.e("qwe", "Checkin 查询所有数据失败: " + e.getMessage());
        } finally {
            //关闭游标
            if (cursor != null) {
                cursor.close();
            }
        }
        return checkinList;
    }

    /**
     * 使用静态内部类来实现单例
     */
    private static class SingletonHolder {
        private static CheckinD mDao = new CheckinD();
    }

}
