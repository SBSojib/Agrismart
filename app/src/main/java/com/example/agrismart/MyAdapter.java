package com.example.agrismart;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



import java.util.List;



public class MyAdapter extends ArrayAdapter<Crop>{

    Activity activity;
    int resource;
    List<Crop> list;





    public MyAdapter(Activity activity, int resource, List<Crop> list) {
        super(activity, resource,list);
        this.activity = activity;
        this.resource = resource;
        this.list = list;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View view = layoutInflater.inflate(resource,null);


        TextView name = (TextView) view.findViewById(R.id.getName);



        name.setText(list.get(position).getName());



        return view;
    }
}

