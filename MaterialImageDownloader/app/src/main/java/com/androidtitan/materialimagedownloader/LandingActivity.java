package com.androidtitan.materialimagedownloader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.FragmentTransaction;
import android.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LandingActivity extends Activity implements UpdateURLsInterface {
    private String TAG = getClass().getSimpleName();
    public static String URLEXTRA = "urlArrayListExtra";

    private URLInputFragment urlFrag = new URLInputFragment();
    private GridViewFragment gridViewFragment = new GridViewFragment();

    private ArrayList<ImageAsyncTask> asyncArray;
    private ArrayList<String> earthsLastHope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);

        final FloatingActionButton deleteFab = (FloatingActionButton) findViewById(R.id.deleteFab);
        final FloatingActionButton downloadFab = (FloatingActionButton) findViewById(R.id.downloadFab);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.container, urlFrag);
        transaction.addToBackStack(null);
        transaction.commit();

        asyncArray = new ArrayList<ImageAsyncTask>();
        earthsLastHope = new ArrayList<String>();

        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(urlFrag != null && urlFrag.isVisible()) {

                    Snackbar.make(view, "Deleted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    earthsLastHope.clear();
                    urlFrag.urlItems.clear();
                    urlFrag.adapter.notifyDataSetChanged();

                    urlFrag.getListView().invalidate();
                }

            }
        });

        downloadFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (urlFrag != null && urlFrag.isVisible() && urlFrag.urlItems.size() > 0) {

                    for (String item : urlFrag.urlItems) {
                        Log.e(TAG, item);
                    }

                    /*Bundle gridBundle = new Bundle();
                    gridBundle.putStringArrayList(URLEXTRA, urlFrag.urlItems);
                    gridViewFragment.setArguments(gridBundle);*/

                    downloadActions(earthsLastHope);

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, gridViewFragment, "gridViewFragment");
                    transaction.addToBackStack(null);
                    transaction.commit();

                } else {

                    //todo: cool material design animation
                }

            }
        });

    }

    public void updateFragmentGridView(Bitmap bm) {

        GridViewFragment gridFrag = (GridViewFragment) getFragmentManager().findFragmentByTag("gridViewFragment");

        gridFrag.bitmaps.add(bm);
        gridFrag.adapter.notifyDataSetChanged();
        gridFrag.imageGridView.invalidateViews();

    }


    public void downloadActions(ArrayList<String> destiny) {

        ArrayList<ImageAsyncTask> asyncArray = new ArrayList<ImageAsyncTask>();

        for(String urlString : destiny) {
            ImageAsyncTask imageAsyncTask = new ImageAsyncTask(this, urlString);
            asyncArray.add(imageAsyncTask);
        }

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                destiny.size(), destiny.size(),
                1, TimeUnit.MINUTES, new LinkedBlockingQueue());

        Log.e(TAG, "List size: " + destiny.size());

        for(ImageAsyncTask imageAsyncTask : asyncArray) {

            imageAsyncTask.executeOnExecutor(threadPoolExecutor);
        }
    }

    @Override
    public void updateURLList(ArrayList<String> stringArrayList) {
        earthsLastHope.addAll(stringArrayList);
    }

    @Override
    public void updateURLItem(String string) {
        earthsLastHope.add(string);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }*/
}
