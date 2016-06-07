package com.colman.zerphonefinall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.colman.zerphonefinall.Model.Store;

import java.util.List;

public class HotelAdapter extends ArrayAdapter<Store> {

    private List<Store> stores;

    public HotelAdapter(Context context, int resource, List<Store> objects) {
        super(context, resource, objects);
        stores = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.list_item_location, parent, false);
        }

        Store location = stores.get(position);

        TextView cityText = (TextView) convertView.findViewById(R.id.cityText);
        cityText.setText(location.getCity());

        TextView neighborhoodText = (TextView) convertView.findViewById(R.id.neighborhoodText);
        neighborhoodText.setText(location.getNeighborhood());

        int imageResource = getContext().getResources().getIdentifier(
                location.getImage(), "drawable", getContext().getPackageName());

        ImageView iv = (ImageView) convertView.findViewById(R.id.imageView);
        iv.setImageResource(imageResource);

        return convertView;
    }
}
