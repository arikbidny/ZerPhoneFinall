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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Sagi on 5/28/2016.
 */
public class Model {
    private final static Model instance = new Model();
    Context context;
    static int position;
    static ModelFIreBase modelFIreBase;
    ModelCloudinary modelCloudinary;

    private Model(){
        context = MainActivity.getAppContext();
        modelFIreBase = new ModelFIreBase(context,position);
        modelCloudinary = new ModelCloudinary();
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

    public void getAllItemsASynch(int position, getItemListener listener) {
        modelFIreBase.getAllItemsAsync(listener, position);
    }


    public void add(Item item){
        modelFIreBase.add(item);
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

    public void update(Item item,String title,String imageName, String price,String details, String cat){
        modelFIreBase.update(item, title, imageName, price, details, cat);
    }

    public static void remove(Item item){
        modelFIreBase.remove(item);
    }
}
