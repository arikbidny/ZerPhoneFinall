package com.colman.zerphonefinall.Model.SQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.colman.zerphonefinall.Model.Constants;
import com.colman.zerphonefinall.Model.Item;

import java.util.LinkedList;

/**
 * Created by Sagi on 7/26/2016.
 */
public class GiftSql {
    private static final String GIFT_TABLE = Constants.giftTable;
    private static final String CATEGORY = "gift";
    private static final String DETAILS = "details";
    private static final String IMAGE = "image";
    private static final String PRICE = "price";
    private static final String TITLE = "title";
    private static final String KEY = "key";
    private static final String LASTUPDATE = "date";

    public static void create(SQLiteDatabase db){
        db.execSQL("create table " +
                GIFT_TABLE      + " (" +
                CATEGORY + " TEXT," +
                DETAILS      + " TEXT," +
                IMAGE      + " TEXT," +
                PRICE      + " INTEGER," +
                TITLE      + " TEXT," +
                KEY          + " TEXT," +
                LASTUPDATE   + " TEXT" +
                ");");
    }

    public static  void addGift(SQLiteDatabase db, Item item){
        boolean flag=true;
        for (Item candidate: getAllGiftsByTimeStamp(db,item.getLastUpadte())){
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
            values.put(LASTUPDATE,item.getLastUpadte());
            values.put(KEY,item.getKey());
            db.insert(GIFT_TABLE, null, values);
        }
    }

    public static void drop(SQLiteDatabase db) {db.execSQL("drop table " + GIFT_TABLE);}

    public static void delete(SQLiteDatabase db,Item item){db.delete(GIFT_TABLE,KEY+" = ?",new String[]{item.getKey()});}

    public static LinkedList<Item> getAllGifts(SQLiteDatabase db) {
        Cursor cursor = db.query(GIFT_TABLE,null,null,null,null,null,null,null);
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
    public static LinkedList<Item> getAllGiftsByTimeStamp(SQLiteDatabase db,String date){
        Cursor cursor = db.query(GIFT_TABLE,null,LASTUPDATE+" = ?",new String[]{date},null,null,null);
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
}
