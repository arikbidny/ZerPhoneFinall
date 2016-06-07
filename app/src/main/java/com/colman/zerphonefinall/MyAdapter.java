package com.colman.zerphonefinall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.colman.zerphonefinall.Model.Item;
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
public class MyAdapter extends BaseAdapter {



    private List<Item> items = new ArrayList<Item>();
    private LayoutInflater inflater;

    public final String data_path = "https://arikbidny-zer-phone.firebaseio.com/";
    private Firebase myFireBaseData;

    public MyAdapter(Context context){
        inflater = LayoutInflater.from(context);
    }
//
//    public MyAdapter (Context context,int i) {
//        Firebase.setAndroidContext(context);
//        if (i==0) {
//            myFireBaseData = new Firebase(data_path+"/single flower");
//            //myFireBaseData.addChildEventListener(new itemChiledEventListner());
//        }
//        else if (i==1) {
//            myFireBaseData = new Firebase(data_path+"/vase");
//            //myFireBaseData.addChildEventListener(new itemChiledEventListner());
//        }
//        else if (i==2) {
//            myFireBaseData = new Firebase(data_path+"/gift");
//            //myFireBaseData.addChildEventListener(new itemChiledEventListner());
//        }
//        else if (i==3) {
//            myFireBaseData = new Firebase(data_path+"/planet");
//
//        }
//        myFireBaseData.addChildEventListener(new itemChiledEventListner());
//        inflater = LayoutInflater.from(context);
//    }
//
//    public void getAllItems(int positon,final LinkedList<Item> list){
//        Firebase itemref;
//        if (positon==0) {
//            itemref = new Firebase(data_path+"/single flower");
//        }
//        else if (positon==1) {
//            itemref = new Firebase(data_path+"/vase");
//        }
//        else if (positon==2) {
//            itemref =new Firebase(data_path+"/gift");
//        }
//        else  {
//            itemref =new Firebase(data_path+"/planet");
//        }
//        for (int i=0; i< items.size() ; i++ ) {
//            list.add(items.get(i));
//        }
//
//        itemref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                /*for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
//                    Item item = itemSnapshot.getValue(Item.class);
//                    list.add(item);
//                }*/
//                for (int i=0; i< items.size() ; i++ ) {
//                    list.add(items.get(i));
//                }
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getImage();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ImageView picture;
        TextView name;
        TextView price;

        if (v == null) {
            v = inflater.inflate(R.layout.gridview_item,parent,false);
            v.setTag(R.id.grid_image, v.findViewById(R.id.grid_image));
            v.setTag(R.id.grid_title, v.findViewById(R.id.grid_title));
            v.setTag(R.id.grid_price,v.findViewById(R.id.grid_price));
        }

        picture = (ImageView) v.getTag(R.id.grid_image);
        name = (TextView) v.getTag(R.id.grid_title);
        price = (TextView) v.getTag(R.id.grid_price);

        Item item = (Item) getItem(position);

        picture.setImageResource(item.getImage());
        name.setText(item.getTitle());
        price.setText(item.getPrice());

        return v;
    }

    public void add(Item item) {
        myFireBaseData = new Firebase(data_path+item.getCategory().toLowerCase());
        myFireBaseData.push().setValue(item);
        notifyDataSetChanged();
    }

    public void update(Item item,String title, String price,String details, String cat) {
        item.setTitle(title);
        item.setPrice(price);
        item.setDetails(details);
        String temp = item.getCategory().replace("%20", " ");
        String temp1 = cat.toLowerCase().replace("%20", " ");
        if (!temp.equals(temp1)) {
            item.setCategory(cat);
            remove(item);
            add(item);
        }

        myFireBaseData.child(item.getKey()).setValue(item);
    }

    public void remove(Item item){
        myFireBaseData.child(item.getKey()).removeValue();
    }

    public void setData(List<Item> data) {
        this.items = data;
        notifyDataSetChanged();
    }


//    public class itemChiledEventListner implements ChildEventListener {
//
//
//        @Override
//        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//            String[] split = myFireBaseData.toString().split("/");
//            String cat = split[3];
//            Item item = dataSnapshot.getValue(Item.class);
//            items.add(new Item(item.getTitle(), R.drawable.lailot_levanim, item.getPrice(), item.getdetails(), cat ,dataSnapshot.getKey()));
//            notifyDataSetChanged();
//
//        }
//
//        @Override
//        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public void onChildRemoved(DataSnapshot dataSnapshot) {
//            String key = dataSnapshot.getKey();
//            for (Item item : items){
//                if (key.equals(item.getKey())){
//                    items.remove(item);
//                    notifyDataSetChanged();
//                    break;
//                }
//            }
//        }
//
//        @Override
//        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//        }
//
//        @Override
//        public void onCancelled(FirebaseError firebaseError) {
//
//        }
//    }

}
