package com.colman.zerphonefinall.Model.SQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.colman.zerphonefinall.Model.Constants;
import com.colman.zerphonefinall.Model.Item;
import com.colman.zerphonefinall.Model.User;

import java.util.LinkedList;

/**
 * Created by Sagi on 7/26/2016.
 */
public class SingleFlowerSql {
    private static final String SINGLEFLOWER_TABLE = Constants.SingleFlowerTable;
    private static final String CATEGORY = "single flower";
    private static final String DETAILS = "details";
    private static final String IMAGE = "image";
    private static final String PRICE = "price";
    private static final String TITLE = "title";
    private static final String KEY = "key";
    private static final String LASTUPDATE = "date";

    public static void create(SQLiteDatabase db){
        db.execSQL("create table " +
                SINGLEFLOWER_TABLE      + " (" +
                CATEGORY + " TEXT," +
                DETAILS      + " TEXT," +
                IMAGE      + " TEXT," +
                PRICE      + " INTEGER," +
                TITLE      + " TEXT," +
                KEY          + " TEXT," +
                LASTUPDATE   + " TEXT" +
                ");");
    }

    public static  void addSingleFlower(SQLiteDatabase db, Item item){
        boolean flag=true;
        for (Item candidate: getAllItemsByTimeStamp(db,item.getLastUpadte())){
            if (candidate.equals(item));
            flag=false;
        }
        if (flag) {
            ContentValues values = new ContentValues();
            values.put(CATEGORY,item.getCategory());
            values.put(DETAILS,item.getdetails());
            values.put(IMAGE,item.getImage());
            values.put(PRICE,item.getPrice());
            values.put(TITLE,item.getTitle());
            values.put(KEY,item.getKey());
            values.put(LASTUPDATE,item.getLastUpadte());
            db.insert(SINGLEFLOWER_TABLE,null,values);
        }
    }

    public static void drop(SQLiteDatabase db) {db.execSQL("drop table " + SINGLEFLOWER_TABLE);}

    public static  void delete(SQLiteDatabase db,Item item){db.delete(SINGLEFLOWER_TABLE, KEY + " = ?", new String[]{item.getKey()});}

    public static LinkedList<Item> getAllItems(SQLiteDatabase db) {
        Cursor cursor = db.query(SINGLEFLOWER_TABLE,null,null,null,null,null,null,null);
        LinkedList<Item> list = new LinkedList<Item>();
        if (cursor.moveToFirst()) {
            int categoryIDX = cursor.getColumnIndex(CATEGORY);
            int detailsIDX = cursor.getColumnIndex(CATEGORY);
            int imageIDX = cursor.getColumnIndex(IMAGE);
            int priceIDX = cursor.getColumnIndex(PRICE);
            int titleIDX = cursor.getColumnIndex(TITLE);
            int keyIDX = cursor.getColumnIndex(KEY);
            int lastUpdateIDX = cursor.getColumnIndex(LASTUPDATE);
            do {
                String category = cursor.getString(categoryIDX);
                String details = cursor.getString(detailsIDX);
                String image = cursor.getString(imageIDX);
                String price = cursor.getString(priceIDX);
                String title = cursor.getString(titleIDX);
                String key = cursor.getString(keyIDX);
                String lastUpdate = cursor.getString(lastUpdateIDX);
                list.add(new Item(title,image,price,details,category,key,lastUpdate));
            }while (cursor.moveToNext());
        }
        return list;
    }

    @Nullable
    public static LinkedList<Item> getAllItemsByTimeStamp(SQLiteDatabase db,String date){
        Cursor cursor = db.query(SINGLEFLOWER_TABLE,null,LASTUPDATE+" = ?",new String[]{date},null,null,null);
        LinkedList<Item> list = new LinkedList<Item>();
        if (cursor.moveToFirst()) {
            int categoryIDX = cursor.getColumnIndex(CATEGORY);
            int detailsIDX = cursor.getColumnIndex(CATEGORY);
            int imageIDX = cursor.getColumnIndex(IMAGE);
            int priceIDX = cursor.getColumnIndex(PRICE);
            int titleIDX = cursor.getColumnIndex(TITLE);
            int keyIdx = cursor.getColumnIndex(KEY);
            int lastUpdateIDX = cursor.getColumnIndex(LASTUPDATE);
            do {
                String category = cursor.getString(categoryIDX);
                String details = cursor.getString(detailsIDX);
                String image = cursor.getString(imageIDX);
                String price = cursor.getString(priceIDX);
                String title = cursor.getString(titleIDX);
                String key = cursor.getString(keyIdx);
                String lastUpdate = cursor.getString(lastUpdateIDX);
                list.add(new Item(title,image,price,details,category,key,lastUpdate));
            }while (cursor.moveToNext());
        }
        return list;
    }
}
