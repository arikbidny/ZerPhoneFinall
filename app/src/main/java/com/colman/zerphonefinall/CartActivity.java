package com.colman.zerphonefinall;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.colman.zerphonefinall.Model.Item;
import com.colman.zerphonefinall.Model.Model;

import java.util.LinkedList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    ListView listView;
    List<Item> data = new LinkedList<Item>();
    CustomAdapter adapter;
    static int total = 0;
    int num;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        total=0;
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        total=0;
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = Model.getInstance(0).getCart();

        for (int i=0; i<data.size(); i++){
            try {
                num = Integer.parseInt(data.get(i).getPrice().toString());
                total+= num;
            } catch (NumberFormatException nfe){
            }
        }

        final TextView totalPrice = (TextView) findViewById(R.id.cartTotal);
        totalPrice.setText(String.valueOf(total)+"$");

        data = Model.getInstance(0).getCart();

        listView = (ListView) findViewById(R.id.cartList);

        adapter = new CustomAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = data.get(position);
                Model.getInstance(position).removeFromCart(position);
                Toast.makeText(getApplicationContext(), "Removed from cart", Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
                try {
                    num = Integer.parseInt(item.getPrice().toString());
                    total -= num;
                    totalPrice.setText(String.valueOf(total));
                } catch (NumberFormatException nfe) {
                }

            }
        });
    }



    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.cart_single_row,null);
            }

            final ImageView image = (ImageView) convertView.findViewById(R.id.imageView);
            TextView title = (TextView) convertView.findViewById(R.id.cartTitle);
            TextView price = (TextView) convertView.findViewById(R.id.priceCart);
            TextView details = (TextView) convertView.findViewById(R.id.detialsCart);

            Item item = data.get(position);
            title.setText(item.getTitle());
            price.setText(item.getPrice());
            details.setText(item.getdetails());



            if (item.getImage() !=null) {
                final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.rowImageProgressBar);
                progressBar.setVisibility(View.VISIBLE);
                Model.getInstance(position).loadImage(item.getImage(), new Model.LoadImageListener() {
                    @Override
                    public void onResult(Bitmap imageBitmap) {
                        if (imageBitmap!=null) {
                            image.setImageBitmap(imageBitmap);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
            return convertView;
        }
    }

}
