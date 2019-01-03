package com.example.talong.lab7;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends BaseAdapter {
    Context context;
    private int resource;

    List<Restaurant> restaurant;


    public RestaurantAdapter( Context context, int resource,  ArrayList<Restaurant> objects) {

        this.context=context;
        this.resource=resource;
        this.restaurant=objects;
    }


    @Override
    public int getCount() {
        return this.restaurant.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurant.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View row=LayoutInflater.from(context).inflate(R.layout.custom_lisview,parent,false);
        TextView name=row.findViewById(R.id.nameTitle);
        TextView address=row.findViewById(R.id.address);
        ImageView icon=row.findViewById(R.id.icon);
        Restaurant r=restaurant.get(position);
        name.setText(r.getName());
        address.setText(r.getAddress());
        String type=r.getType();
        if (type.equals("Take out")){
            icon.setImageResource(R.drawable.love);

        }
        else if (type.equals("Sit down")){
            icon.setImageResource(R.drawable.like);
        }
        else
        {
            icon.setImageResource(R.drawable.smile);

        }
        return row;
    }




}
