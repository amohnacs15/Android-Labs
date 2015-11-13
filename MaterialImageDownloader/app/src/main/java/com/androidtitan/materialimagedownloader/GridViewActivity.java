package com.androidtitan.materialimagedownloader;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GridViewActivity extends Activity implements AsyncDelegate{
    private String TAG = getClass().getSimpleName();
    public static String PASSABLE_EXTRA= "passableBitMapExtra";
    public static String PASSABLE_INT = "passableCountInteger";

    private BitmapDownloadCollection collection = BitmapDownloadCollection.getInstance(this);

    private FrameLayout rootLayout;
    private int cx;
    private int cy;

    public GridView imageGridView;
    public MyGridAdapter adapter;

    private ArrayList<String> urlDownloaders;
    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);

        rootLayout = (FrameLayout) findViewById(R.id.root_layout);

        urlDownloaders = getIntent().getStringArrayListExtra(LandingActivity.URLEXTRA);
        cx = getIntent().getIntExtra(LandingActivity.BTN_X_EXTRA, 0);
        cy = getIntent().getIntExtra(LandingActivity.BTN_Y_EXTRA, 0);

        adapter = new MyGridAdapter(this, bitmaps);

        imageGridView = (GridView) findViewById(R.id.imageGridView);
        imageGridView.setAdapter(adapter);

        downloadActions(urlDownloaders);

        if(savedInstanceState == null) {
            //Circular Reveal Setup
            rootLayout.setVisibility(View.VISIBLE);

            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();

            if(viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        //todo
                        circularRevealActivity();
                    }
                });
            }
        }


        imageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //compress bitmap
                //todo we need to find a way to pass them all or reference them all from that activity
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmaps.get(position).compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();

                Intent intent = new Intent(GridViewActivity.this, ViewPagerActivity.class);
                //intent.putExtra(PASSABLE_EXTRA, bytes);
                intent.putExtra(PASSABLE_INT, position);

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        GridViewActivity.this, new Pair<View, String>(view, getString(R.string.image_transition)));

                ActivityCompat.startActivity(GridViewActivity.this, intent, options.toBundle());

            }
        });
    }

    private void circularRevealActivity() {


        if(cx == 0 && cy == 0) {

            cx = rootLayout.getWidth() / 2;
            cy = rootLayout.getHeight() / 2;
        }

        float finalRadius = Math.max(rootLayout.getWidth(), rootLayout.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, 0, finalRadius);
        circularReveal.setDuration(1000);
        if(counter == 0) {
        } else {
            rootLayout.setBackgroundResource(R.color.colorAccent);
        }

        counter ++;
        // make the view visible and start the animation
        rootLayout.setVisibility(View.VISIBLE);

        circularReveal.start();

    }

    public void downloadActions(ArrayList<String> stringArray) {

        ArrayList<ImageAsyncTask> asyncArray = new ArrayList<ImageAsyncTask>();

        for(String urlString : stringArray) {
            ImageAsyncTask imageAsyncTask = new ImageAsyncTask(GridViewActivity.this, urlString);
            asyncArray.add(imageAsyncTask);
        }

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                stringArray.size(), stringArray.size(),
                1, TimeUnit.MINUTES, new LinkedBlockingQueue());

        Log.e(TAG, "List size: " + stringArray.size());

        for(ImageAsyncTask imageAsyncTask : asyncArray) {
            imageAsyncTask.executeOnExecutor(threadPoolExecutor);
        }
    }

    @Override
    public void asyncComplete(Bitmap bitmap) {
        collection.add2BitmapArrayList(new BitmapDownloader(bitmap));
        //bitmaps.add(bitmap);
        adapter.notifyDataSetChanged();
    }
}
