package com.colman.zerphonefinall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.colman.zerphonefinall.Model.DataProvider;
import com.colman.zerphonefinall.Model.Store;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    List<Store> stores = DataProvider.storeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ListView listView = (ListView) findViewById(android.R.id.list);
        ArrayAdapter<Store> adapter =
                new HotelAdapter(this, R.layout.list_item_location, stores);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main2Activity.this, DetailActivity.class);

                Store location = stores.get(position);
                intent.putExtra("city", location.getCity());
                startActivity(intent);
            }

        });
    }
}
