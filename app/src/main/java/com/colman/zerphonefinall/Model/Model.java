package com.colman.zerphonefinall.Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.colman.zerphonefinall.MainActivity;
import com.colman.zerphonefinall.Model.SQL.ModelSql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sagi on 5/28/2016.
 */
public class Model {
    private final static Model instance = new Model();
    Context context;
    private List<Item> cart = new LinkedList<Item>();
    static int position;
    static ModelFIreBase modelFIreBase;
    ModelCloudinary modelCloudinary;
    static ModelSql modelSql;

    private Model(){
        context = MainActivity.getAppContext();
        modelFIreBase = new ModelFIreBase(context,position);
        modelCloudinary = new ModelCloudinary();
        modelSql = new ModelSql();
    }

    public static void setPosition(int i){
        position = i;
    }

    public static Model getInstance(int i){
        setPosition(i);
        return instance;
    }

    public interface getItemListener {
        public void onResult(List<Item> items);
        public void onCancel();
    }

    public void getAllItemsASynch(final int position, final getItemListener listener) {
//        modelFIreBase.getAllItemsAsync(listener, position);
        String cat="";
        switch (position) {
            case 0:
            {
                cat= "single flower";
                modelFIreBase.getLastUpdateDate(Constants.SingleFlowerTable, new ModelFIreBase.UpdateDateCompletionListener() {
                    @Override
                    public void onResult(final String updateDate) {
                        String sqlUpdated = modelSql.getLastUpdate(Constants.SingleFlowerTable);
                        long temp;
                        if (sqlUpdated!=null) {temp = Long.parseLong(sqlUpdated);}
                        else temp = 0;
                        final long sql=temp;
                        if (sql >= Long.parseLong(updateDate)){
                            LinkedList<Item> list = modelSql.gettAllSingleFlowers();
                            listener.onResult(list);
                        }
                        else {
                            modelFIreBase.getAllItemsAsync(new getItemListener() {
                                @Override
                                public void onResult(List<Item> items) {
                                    for (Item item: items){
                                        if(Long.parseLong(item.getLastUpadte()) >= sql){
                                        //Write updates to sql
                                            modelSql.delSingleFlower(item);
                                            modelSql.addSingleFlower(item);
                                        }

                                    }
                                    //Update sql last update date for the table
                                    //After updates were written -> call to the listener
                                    modelSql.setLastUpdate(Constants.SingleFlowerTable,updateDate);
                                    listener.onResult(items);
                                }

                                @Override
                                public void onCancel() {
                                    LinkedList<Item> list = modelSql.gettAllSingleFlowers();
                                    listener.onResult(list);
                                }
                            }, position);

                        }
                    }
                    @Override
                    public void onError(String error) {
                        LinkedList<Item> list = modelSql.gettAllSingleFlowers();
                        listener.onResult(list);
                    }
                });
                break;
            }
            case 1:
            {
                cat = "vase";
                modelFIreBase.getLastUpdateDate(Constants.VaseTable, new ModelFIreBase.UpdateDateCompletionListener() {
                    @Override
                    public void onResult(final String updateDate) {
                        String sqlUpdated = modelSql.getLastUpdate(Constants.VaseTable);
                        long temp;
                        if (sqlUpdated!=null) {temp = Long.parseLong(sqlUpdated);}
                        else temp = 0;
                        final long sql=temp;
                        if (sql >= Long.parseLong(updateDate)){
                            LinkedList<Item> list = modelSql.gettAllVases();
                            listener.onResult(list);
                        }
                        else {
                            modelFIreBase.getAllItemsAsync(new getItemListener() {
                                @Override
                                public void onResult(List<Item> items) {
                                    for (Item item: items){
                                        if(Long.parseLong(item.getLastUpadte()) >= sql){
                                            //Write updates to sql
                                            modelSql.delVase(item);
                                            modelSql.addVase(item);
                                        }

                                    }
                                    //Update sql last update date for the table
                                    //After updates were written -> call to the listener
                                    modelSql.setLastUpdate(Constants.VaseTable,updateDate);
                                    listener.onResult(items);
                                }

                                @Override
                                public void onCancel() {
                                    LinkedList<Item> list = modelSql.gettAllVases();
                                    listener.onResult(list);
                                }
                            }, position);
                        }
                    }
                    @Override
                    public void onError(String error) {
                        LinkedList<Item> list = modelSql.gettAllVases();
                        listener.onResult(list);
                    }
                });
                break;
            }
            case 2:
            {
                cat = "gift";
                modelFIreBase.getLastUpdateDate(Constants.giftTable, new ModelFIreBase.UpdateDateCompletionListener() {
                    @Override
                    public void onResult(final String updateDate) {
                        String sqlUpdated = modelSql.getLastUpdate(Constants.giftTable);
                        long temp;
                        if (sqlUpdated!=null) {temp = Long.parseLong(sqlUpdated);}
                        else temp = 0;
                        final long sql=temp;
                        if (sql >= Long.parseLong(updateDate)){
                            LinkedList<Item> list = modelSql.gettAllGifts();
                            listener.onResult(list);
                        }
                        else {
                            modelFIreBase.getAllItemsAsync(new getItemListener() {
                                @Override
                                public void onResult(List<Item> items) {
                                    for (Item item: items){
                                        if(Long.parseLong(item.getLastUpadte()) >= sql){
                                            //Write updates to sql
                                            modelSql.delGift(item);
                                            modelSql.addGift(item);
                                        }

                                    }
                                    //Update sql last update date for the table
                                    //After updates were written -> call to the listener
                                    modelSql.setLastUpdate(Constants.giftTable,updateDate);
                                    listener.onResult(items);
                                }

                                @Override
                                public void onCancel() {
                                    LinkedList<Item> list = modelSql.gettAllGifts();
                                    listener.onResult(list);
                                }
                            }, position);
                        }
                    }
                    @Override
                    public void onError(String error) {
                        LinkedList<Item> list = modelSql.gettAllGifts();
                        listener.onResult(list);
                    }
                });
                break;
            }
            case 3:
            {
                cat = "planet";
                modelFIreBase.getLastUpdateDate(Constants.PlanetTable, new ModelFIreBase.UpdateDateCompletionListener() {
                    @Override
                    public void onResult(final String updateDate) {
                        String sqlUpdated = modelSql.getLastUpdate(Constants.PlanetTable);
                        long temp;
                        if (sqlUpdated!=null) {temp = Long.parseLong(sqlUpdated);}
                        else temp = 0;
                        final long sql=temp;
                        if (sql == Long.parseLong(updateDate)){
                            LinkedList<Item> list = modelSql.gettAllPlanets();
                            listener.onResult(list);
                        }
                        else {
                            modelFIreBase.getAllItemsAsync(new getItemListener() {
                                @Override
                                public void onResult(List<Item> items) {
                                    for (Item item: items){
                                        if(Long.parseLong(item.getLastUpadte()) >= sql){
                                            //Write updates to sql
                                            modelSql.delPlanet(item);
                                            modelSql.addPlanet(item);
                                        }

                                    }
                                    //Update sql last update date for the table
                                    //After updates were written -> call to the listener
                                    modelSql.setLastUpdate(Constants.PlanetTable,updateDate);
                                    listener.onResult(items);
                                }

                                @Override
                                public void onCancel() {
                                    LinkedList<Item> list = modelSql.gettAllPlanets();
                                    listener.onResult(list);
                                }
                            }, position);
                        }
                    }
                    @Override
                    public void onError(String error) {
                        LinkedList<Item> list = modelSql.gettAllPlanets();
                        listener.onResult(list);
                    }
                });
                break;
            }
        }

    }


    public void add(Item item){
        modelFIreBase.add(item);
        String cat = item.getCategory().toLowerCase();
    }

    public void saveImage (final Bitmap imageBitmap,final String imageName){
        saveImageToFile(imageBitmap, imageName);
        Thread d = new Thread(new Runnable() {
            @Override
            public void run() {
                modelCloudinary.saveImage(imageBitmap,imageName);
            }
        });
        d.start();
    }

    public interface LoadImageListener{
        public void onResult(Bitmap imageBitmap);
    }

    public void loadImage(final String imageName, final LoadImageListener listener) {
        AsyncTask<String,String,Bitmap> task = new AsyncTask<String, String, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bmp = laodImageFromFile(imageName);
                if (bmp == null){
                    bmp = modelCloudinary.loadImage(imageName);
                    if (bmp!=null) saveImageToFile(bmp, imageName);
                }
                return bmp;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                listener.onResult(result);
            }
        };
        task.execute();
    }

    private Bitmap laodImageFromFile(String imageName) {
        String str = null;
        Bitmap bitmap = null;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(dir,imageName);

            //File dir = context.getExternalFilesDir(null);
            InputStream inputStream = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(inputStream);
            Log.d("tag","got image from cache: " + imageName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void saveImageToFile(Bitmap imageBitmap, String imageFileName){
        FileOutputStream fos;
        OutputStream out = null;
        try {
            //File dir = context.getExternalFilesDir(null);
            File dir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File imageFile = new File(dir,imageFileName);
            imageFile.createNewFile();

            out = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

            //add the picture to the gallery so we dont need to manage the cache size
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(imageFile);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
            Log.d("tag","add image to cache: " + imageFileName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Item item,String title,String imageName, String price,String details, String cat,String date){
        modelFIreBase.update(item, title, imageName, price, details, cat, date);
    }

    public static void remove(Item item){
        modelFIreBase.remove(item);
        switch (item.getCategory().toLowerCase()) {
            case "single flower":
            {
                modelSql.delSingleFlower(item);
                modelFIreBase.getLastUpdateDate(Constants.SingleFlowerTable, new ModelFIreBase.UpdateDateCompletionListener() {
                    @Override
                    public void onResult(String updateDate) {
                        modelSql.setLastUpdate(Constants.SingleFlowerTable,updateDate);
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
            case "vase":
            {
                modelSql.delVase(item);
                modelFIreBase.getLastUpdateDate(Constants.VaseTable, new ModelFIreBase.UpdateDateCompletionListener() {
                    @Override
                    public void onResult(String updateDate) {
                        modelSql.setLastUpdate(Constants.VaseTable,updateDate);
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
            case "gift":
            {
                modelSql.delGift(item);
                modelFIreBase.getLastUpdateDate(Constants.giftTable, new ModelFIreBase.UpdateDateCompletionListener() {
                    @Override
                    public void onResult(String updateDate) {
                        modelSql.setLastUpdate(Constants.giftTable,updateDate);
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
            case "planet":
            {
                modelSql.delPlanet(item);
                modelFIreBase.getLastUpdateDate(Constants.PlanetTable, new ModelFIreBase.UpdateDateCompletionListener() {
                    @Override
                    public void onResult(String updateDate) {
                        modelSql.setLastUpdate(Constants.PlanetTable,updateDate);
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        }
    }

    public void logout(){modelFIreBase.logout();}

    public void addToCart(Item item) {
        cart.add(item);
    }

    public List<Item> getCart(){
        return cart;
    }

    public void removeFromCart(int pos) {
        cart.remove(pos);
    }

    public void removeAllCart(){
        for (int i=0; i<=cart.size() ; i++){
            cart.remove(i);
        }
    }

}
