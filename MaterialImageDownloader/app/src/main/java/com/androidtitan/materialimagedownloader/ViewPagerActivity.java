package com.androidtitan.materialimagedownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

public class ViewPagerActivity extends AppCompatActivity {
    private String TAG= getClass().getSimpleName();

    BitmapDownloadCollection collection = BitmapDownloadCollection.getInstance(this);

    MyPagerAdapter pagerAdapter;

    ViewPager viewPager;
    ImageView imageView;

    ArrayList<BitmapDownloader> bitmapArrayList;
    Bitmap bitmapper;
    int bitmapIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bitmapArrayList = collection.getBitmapArrayList();

        //todo: your display is getting called twice.
        // null then result
        // perhaps we need to use onCreateView() Override

        //todo: the problem with the animation might also be that
        //we are not passing anything that is being received and sent by this activity

        Log.e(TAG, "collections size: " + bitmapArrayList);
        for(BitmapDownloader bmd : bitmapArrayList) {
            Log.e(TAG, "display - " + String.valueOf(bmd.getBitmap()));
        }

        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyPagerAdapter(this, bitmapArrayList);
        viewPager.setAdapter(pagerAdapter);

        bitmapIndex = getIntent().getIntExtra(GridViewFragment.PASSABLE_INT, -1);
        viewPager.setCurrentItem(bitmapIndex);

        //decompress
        byte[] bytes = getIntent().getByteArrayExtra(GridViewFragment.PASSABLE_EXTRA);
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        /*ImageView imageView = (ImageView) findViewById(R.id.haterFrame);
        imageView.setImageBitmap(bmp);*/






    }

}
