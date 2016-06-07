package com.colman.zerphonefinall.Model;

import android.content.Context;

import com.colman.zerphonefinall.MainActivity;

import java.util.List;

/**
 * Created by Sagi on 5/28/2016.
 */
public class Model {
    private final static Model instance = new Model();
    Context context;
    static int position;
    static ModelFIreBase modelFIreBase;

    private Model(){
        context = MainActivity.getAppContext();
        modelFIreBase = new ModelFIreBase(context,position);
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

    public void update(Item item,String title, String price,String details, String cat){
        modelFIreBase.update(item, title, price, details, cat);
    }

    public static void remove(Item item){
        modelFIreBase.remove(item);
    }
}
