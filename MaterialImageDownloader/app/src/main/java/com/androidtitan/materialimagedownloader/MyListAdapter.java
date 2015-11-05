package com.androidtitan.materialimagedownloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by amohnacs on 10/31/15.
 */


public class MyListAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> urlStringList;

    public MyListAdapter(Context context, ArrayList<String> objects) {
        super(context, -1, objects);

        this.context = context;
        this.urlStringList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_row_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.listItemText);

        textView.setText(urlStringList.get(position));

        return rowView;
    }
}


