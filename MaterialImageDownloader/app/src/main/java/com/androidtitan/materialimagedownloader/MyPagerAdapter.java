package com.androidtitan.materialimagedownloader;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by amohnacs on 11/5/15.
 */
public class MyPagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    ArrayList<BitmapDownloader> bitmapArrayList;

    public MyPagerAdapter(Context c, ArrayList<BitmapDownloader> d) {
        this.context = c;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.bitmapArrayList = d;
    }

    @Override
    public int getCount() {
        return bitmapArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.adapter_content_image, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.haterFrame);
        imageView.setImageBitmap(bitmapArrayList.get(position).getBitmap());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

}