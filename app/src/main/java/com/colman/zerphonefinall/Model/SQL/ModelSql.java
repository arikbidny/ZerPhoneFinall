package com.colman.zerphonefinall.Model.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;

import com.colman.zerphonefinall.MainActivity;
import com.colman.zerphonefinall.Model.Item;
import com.colman.zerphonefinall.Model.User;
import com.colman.zerphonefinall.MyAdapter;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Sagi on 7/26/2016.
 */
public class ModelSql {

    private final static int VERSION = 13;
    MyDBHelper dbHelper;

     public ModelSql() {dbHelper = new MyDBHelper(MainActivity.getAppContext());}

    public void setLastUpdate(String tableNum,String lastUpdate) {LastUpdateSql.setLastUpdate(dbHelper.getWritableDatabase(),tableNum,lastUpdate);}

    public String getLastUpdate(String table) {return  LastUpdateSql.getLastUpdate(dbHelper.getWritableDatabase(),table);}

    public void addGift(Item item){GiftSql.addGift(dbHelper.getWritableDatabase(),item);}

    public void addPlanet(Item item){PlanetSql.addPlanet(dbHelper.getWritableDatabase(), item);}

    public void addSingleFlower(Item item){SingleFlowerSql.addSingleFlower(dbHelper.getWritableDatabase(), item);}

    public void addVase(Item item){VaseSql.addvase(dbHelper.getWritableDatabase(), item);}

    public void delGift(Item item) {GiftSql.delete(dbHelper.getWritableDatabase(), item);}

    public void delPlanet(Item item) {PlanetSql.delete(dbHelper.getWritableDatabase(),item);}

    public void  delSingleFlower (Item item) {SingleFlowerSql.delete(dbHelper.getWritableDatabase(),item);}

    public void delVase (Item item) {VaseSql.delete(dbHelper.getWritableDatabase(),item);}

    public LinkedList<Item> gettAllGifts(){
        LinkedList<Item> gifts = GiftSql.getAllGifts(dbHelper.getWritableDatabase());
        return gifts;
    }

    public LinkedList<Item> gettAllPlanets(){
        LinkedList<Item> planets = PlanetSql.getAllPlanets(dbHelper.getWritableDatabase());
        return planets;
    }

    public LinkedList<Item> gettAllSingleFlowers(){
        LinkedList<Item> singleFlowers = SingleFlowerSql.getAllItems(dbHelper.getWritableDatabase());
        return singleFlowers;
    }

    public LinkedList<Item> gettAllVases(){
        LinkedList<Item> vases = VaseSql.getAllVases(dbHelper.getWritableDatabase());
        return vases;
    }






    class MyDBHelper extends SQLiteOpenHelper {

        public MyDBHelper(Context context) {
            super(context, "my_DB,db", null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            GiftSql.create(db);
            LastUpdateSql.create(db);
            PlanetSql.create(db);
            SingleFlowerSql.create(db);
            UsersSql.create(db);
            VaseSql.create(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                GiftSql.drop(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                LastUpdateSql.drop(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                PlanetSql.drop(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                SingleFlowerSql.drop(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                UsersSql.drop(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                VaseSql.drop(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
            onCreate(db);

        }
    }
}
