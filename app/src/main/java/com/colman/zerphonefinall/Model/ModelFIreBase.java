package com.colman.zerphonefinall.Model;

import android.content.Context;
import android.util.Log;

import com.colman.zerphonefinall.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sagi on 5/28/2016.
 */
public class ModelFIreBase {
    Firebase myFireBaseData;
    public final String data_path = Constants.FIREBASE_URL;
    private static  final String TAG = "MyActivity";

    public interface UpdateDateCompletionListener
    {
        public void onResult(String updateDate);
        public void onError(String error);
    }

    private List<Item> items = new ArrayList<Item>();

    ModelFIreBase(Context context,int i) {
        Firebase.setAndroidContext(context);
        if (i==0) {
            myFireBaseData = new Firebase(data_path+"/single flower");
            //myFireBaseData.addChildEventListener(new itemChiledEventListner());
        }
        else if (i==1) {
            myFireBaseData = new Firebase(data_path+"/vase");
            //myFireBaseData.addChildEventListener(new itemChiledEventListner());
        }
        else if (i==2) {
            myFireBaseData = new Firebase(data_path+"/gift");
            //myFireBaseData.addChildEventListener(new itemChiledEventListner());
        }
        else if (i==3) {
            myFireBaseData = new Firebase(data_path+"/planet");

        }
        myFireBaseData.addChildEventListener(new itemChiledEventListner());

    }


    public void getAllItemsAsync(final Model.getItemListener listener, int positon){
        Firebase itemref;
        if (positon==0) {
            itemref = new Firebase(data_path+"/single flower");
        }
        else if (positon==1) {
            itemref = new Firebase(data_path+"/vase");
        }
        else if (positon==2) {
            itemref =new Firebase(data_path+"/gift");
        }
        else  {
            itemref =new Firebase(data_path+"/planet");
        }

        itemref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Item> list = new LinkedList<Item>();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);
                    item.setKey(itemSnapshot.getKey());
                    list.add(item);
                }
                listener.onResult(list);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onCancel();
            }
        });
    }

    public void getLastUpdateDate(String tableName,final UpdateDateCompletionListener updateDateCompletionListener){
        Firebase ref = new Firebase(data_path + "/" + Constants.LastUpdateTable);
        Log.v(TAG,"table name=" + tableName);
        ref.child(tableName).child("date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String date = dataSnapshot.getValue(String.class);
                if (date == null || date.equals("")) date = "1";
                updateDateCompletionListener.onResult(date);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                updateDateCompletionListener.onError("");
            }
        });
    }


    public void add(Item item) {
        myFireBaseData = new Firebase(data_path+item.getCategory().toLowerCase());
        myFireBaseData.push().setValue(item);
        Firebase ref = new Firebase(data_path + "/" + Constants.LastUpdateTable);
        String tableName = checkCat(item);
        lastUpdate lastUpdate = new lastUpdate(item.getLastUpadte(),tableName);
        ref.child(tableName).setValue(lastUpdate);
    }

    public void update(Item item,String title,String image, String price,String details, String cat,String date) {
        Firebase ref = new Firebase(data_path + "/" + Constants.LastUpdateTable);
        String tableName = checkCat(item);
        lastUpdate lastUpdate = new lastUpdate(String.valueOf(System.currentTimeMillis()),tableName);
        ref.child(tableName).setValue(lastUpdate);

        item.setTitle(title);
        item.setPrice(price);
        item.setDetails(details);
        item.setImage(image);
        item.setLastUpadte(date);
        String temp = item.getCategory();
        if (!item.getCategory().toLowerCase().equals(cat.toLowerCase())) {
            remove(item);
            item.setCategory(cat);
            add(item);
            return;
        }

        //myFireBaseData.child(item.getKey()).setValue(item);
        Firebase url = new Firebase(data_path + "/" + item.getCategory().toLowerCase());
        url.child(item.getKey()).setValue(item);


    }

    public void remove(Item item){
        Firebase firebase = new Firebase(data_path+item.getCategory().toLowerCase());
        String key = item.getKey();
        firebase.child(item.getKey()).removeValue();
        Firebase ref = new Firebase(data_path + "/" + Constants.LastUpdateTable);
        String tableName = checkCat(item);
        lastUpdate lastUpdate = new lastUpdate(String.valueOf(System.currentTimeMillis()),tableName);
        ref.child(tableName).setValue(lastUpdate);
    }

    public void logout(){
        myFireBaseData.unauth();
    }

    public String checkCat(Item item){
        switch (item.getCategory().toLowerCase()){
            case "single flower":
            {
                return Constants.SingleFlowerTable;
            }
            case "vase":
            {
                return Constants.VaseTable;
            }
            case "gift":
            {
                return Constants.giftTable;
            }
            default:
            {
                return Constants.PlanetTable;
            }
        }
    }


    public class itemChiledEventListner implements ChildEventListener {


        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            String[] split = myFireBaseData.toString().split("/");
            String cat = split[3];
            Item item = dataSnapshot.getValue(Item.class);
            items.add(new Item(item.getTitle(), item.getImage(), item.getPrice(), item.getdetails(), cat ,dataSnapshot.getKey()));
            //notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            //notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
            for (Item item : items){
                if (key.equals(item.getKey())){
                    items.remove(item);
                    //notifyDataSetChanged();
                    break;
                }
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }
}
