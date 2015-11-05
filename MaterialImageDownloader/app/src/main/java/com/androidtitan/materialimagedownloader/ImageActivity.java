package com.androidtitan.materialimagedownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {

    ImageView imageView;

    Bitmap bitmapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //decompress
        byte[] bytes = getIntent().getByteArrayExtra(GridViewFragment.PASSABLE_EXTRA);
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        ImageView imageView = (ImageView) findViewById(R.id.haterFrame);
        imageView.setImageBitmap(bmp);



    }

}
