package com.colman.zerphonefinall.Model.SQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.colman.zerphonefinall.Model.User;

import java.util.LinkedList;

/**
 * Created by Sagi on 7/26/2016.
 */
public class UsersSql {
    private static final String USERS_TABLE = "users";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String LASTUPDATE = "";


    public static void create(SQLiteDatabase db){
        db.execSQL("create table " +
                USERS_TABLE      + " (" +
                EMAIL + " TEXT," +
                PASSWORD      + " TEXT," +
                LASTUPDATE   + " TEXT" +
                ");");
    }

    public static  void addUser(SQLiteDatabase db, User user){
        boolean flag=true;
        for (User candidate: getAllUsersByTimeStamp(db,user.getlastUpdate())){
            if (candidate.equals(user));
            flag=false;
        }
        if (flag) {
            ContentValues values = new ContentValues();
            values.put(EMAIL,user.getEmail());
            values.put(PASSWORD,user.getPassword());
            values.put(LASTUPDATE,user.getlastUpdate());
            db.insert(USERS_TABLE,null,values);
        }
    }

    public static void drop(SQLiteDatabase db) {db.execSQL("drop table " + USERS_TABLE);}

    public static LinkedList<User> getAllUsers(SQLiteDatabase db) {
        Cursor cursor = db.query(USERS_TABLE,null,null,null,null,null,EMAIL," DESC");
        LinkedList<User> list = new LinkedList<User>();
        if (cursor.moveToFirst()) {
            int emailIDX = cursor.getColumnIndex(EMAIL);
            int passwordIDX = cursor.getColumnIndex(PASSWORD);
            int lastUpdateIDX = cursor.getColumnIndex(LASTUPDATE);
            do {
                String email = cursor.getString(emailIDX);
                String password = cursor.getString(passwordIDX);
                String lastUpdate = cursor.getString(lastUpdateIDX);
                list.add(new User(email,password,lastUpdate));
            }while (cursor.moveToNext());
        }
        return list;
    }

    @Nullable
    public static LinkedList<User> getAllUsersByTimeStamp(SQLiteDatabase db,String date){
        Cursor cursor = db.query(USERS_TABLE,null,LASTUPDATE+" = ?",new String[]{date},null,null,null);
        LinkedList<User> list = new LinkedList<User>();
        if (cursor.moveToFirst()) {
            int emailIDX = cursor.getColumnIndex(EMAIL);
            int passwordIDX = cursor.getColumnIndex(PASSWORD);
            int lastUpdateIDX = cursor.getColumnIndex(LASTUPDATE);
            do {
                String email = cursor.getString(emailIDX);
                String password = cursor.getString(passwordIDX);
                String lastUpdate = cursor.getString(lastUpdateIDX);
                list.add(new User(email,password,lastUpdate));
            }while (cursor.moveToNext());
        }
        return list;
    }
}
