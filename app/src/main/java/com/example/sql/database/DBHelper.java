package com.example.sql.database;


import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.sql.model.Detail;
import com.example.sql.model.User;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper instance;

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "CipherDB.db";

    public static final String TABLE_USER = "USERS";
    public static final String COLUMN_USER_ID = "USER_ID";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_USER_PHONE = "PHONE";

    public static final String TABLE_DETAIL = "DETAILS";
    public static final String COLUMN_DETAIL_USER_ID = "USER_ID";
    public static final String COLUMN_DETAIL_ID = "DETAIL_ID";
    public static final String COLUMN_DETAIL_DAY = "DAY";
    public static final String COLUMN_DETAIL_NUMBER = "NUMBER";
    public static final String COLUMN_DETAIL_CONTENT = "CONTENT";

    //xử lí lại thành đăng nhập mã pin!
    //todo
    public static final String DB_PASS = "!@#ABC";

    public static final String SQL_CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_NAME + " TEXT NOT NULL, "
            + COLUMN_USER_PHONE + " INTEGER NOT NULL " + ");";

    public static final String SQL_CREATE_TABLE_DETAIL = "CREATE TABLE " + TABLE_DETAIL + "("
            + COLUMN_DETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DETAIL_USER_ID + "INTEGER NOT NULL, "
            + COLUMN_DETAIL_DAY + " TEXT NOT NULL, "
            + COLUMN_DETAIL_NUMBER + " INTEGER NOT NULL, "
            + COLUMN_DETAIL_CONTENT + " TEXT NOT NULL " + ");";

    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void insertNewUser(String userName, String userPhone) {
        SQLiteDatabase database = instance.getWritableDatabase(DB_PASS);

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, userName);
        values.put(COLUMN_USER_PHONE, userPhone);
        database.insert(TABLE_USER, null, values);
        database.close();
    }

    public void deleteUser(String userID) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        ContentValues values = new ContentValues();
        values.put(COLUMN_DETAIL_USER_ID, userID);
        values.put(COLUMN_USER_ID, userID);
        db.delete(TABLE_DETAIL, COLUMN_DETAIL_USER_ID + "='" + userID + "'", new String[]{});
        db.delete(TABLE_USER, COLUMN_USER_ID + "='" + userID + "'", new String[]{});

        db.close();
    }

    public void insertNewDetail(String userId, String detailDay,String detailNumber,String detailContent) {
        SQLiteDatabase database = instance.getWritableDatabase(DB_PASS);

        ContentValues values = new ContentValues();
        values.put(COLUMN_DETAIL_USER_ID, userId);
        values.put(COLUMN_DETAIL_DAY, detailDay);
        values.put(COLUMN_DETAIL_NUMBER, detailNumber);
        values.put(COLUMN_DETAIL_CONTENT, detailContent);
        database.insert(TABLE_DETAIL, null, values);
        database.close();

    }

    public void updateDetail(String detailID, String newDay, String newNumber, String newContent) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        ContentValues values = new ContentValues();
        values.put(COLUMN_DETAIL_DAY, newDay);
        values.put(COLUMN_DETAIL_NUMBER, newNumber);
        values.put(COLUMN_DETAIL_CONTENT, newContent);
        db.update(TABLE_DETAIL, values, COLUMN_DETAIL_ID + "='" + detailID + "'", null);
        db.close();
    }

    public void deleteDetails(String detailID) {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, detailID);
        db.delete(TABLE_DETAIL, COLUMN_DETAIL_ID + "='" + detailID + "'", new String[]{});

        db.close();
    }

    public List<User> getAllUser() {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM '%s';", TABLE_USER), null);
        List<User> users = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String userId= cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
                String userName= cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));
                String userPhone= cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE));
                users.add(new User(userId,userName,userPhone));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return users;
    }

    public List<Detail> getAllDetail() {
        SQLiteDatabase db = instance.getWritableDatabase(DB_PASS);

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM '%s';", TABLE_DETAIL), null);
        List<Detail> details = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String userId= cursor.getString(cursor.getColumnIndex(COLUMN_DETAIL_USER_ID));
                String detailId= cursor.getString(cursor.getColumnIndex(COLUMN_DETAIL_ID));
                String detailDay= cursor.getString(cursor.getColumnIndex(COLUMN_DETAIL_DAY));
                String detailNumber= cursor.getString(cursor.getColumnIndex(COLUMN_DETAIL_NUMBER));
                String detailContent= cursor.getString(cursor.getColumnIndex(COLUMN_DETAIL_CONTENT));
                details.add(new Detail(userId,detailId,detailDay,detailNumber,detailContent));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        Log.e("aaa",""+details.size());

        return details;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_DETAIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_DETAIL);

        onCreate(db);
    }


}
