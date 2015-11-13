package com.androidtitan.materialimagedownloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by amohnacs on 11/1/15.
 */
public class MyGridAdapter extends BaseAdapter{
    private String TAG = getClass().getSimpleName();

    private Context context;
    public ArrayList<Bitmap> sweet;


    public MyGridAdapter(Context cont, ArrayList<Bitmap> bitter) {
        this.context = cont;
        this.sweet = bitter;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView gridViewItem;

        if(convertView == null) {

            gridViewItem = new ImageView(context);
            gridViewItem.setScaleType(ImageView.ScaleType.CENTER);
            gridViewItem.setPadding(8, 8, 8, 8);
            //Bitmap tinybitmap = sweet.get(position);
            Log.e(TAG, "adapter item " + String.valueOf(sweet.get(position)));

            gridViewItem.setImageBitmap(sweet.get(position));
        }
        else {
            gridViewItem = (ImageView) convertView;
        }

        return gridViewItem;
    }

    @Override
    public int getCount() {
        return sweet.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
