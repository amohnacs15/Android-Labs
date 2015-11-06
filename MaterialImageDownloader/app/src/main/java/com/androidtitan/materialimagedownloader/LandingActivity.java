package com.androidtitan.materialimagedownloader;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LandingActivity extends Activity implements UpdateURLsInterface {
    private String TAG = getClass().getSimpleName();
    public static String URLEXTRA = "urlArrayListExtra";

    BitmapDownloader downloader;
    BitmapDownloadCollection collection = BitmapDownloadCollection.getInstance(this);

    private URLInputFragment urlFrag = new URLInputFragment();
    private GridViewFragment gridViewFragment = new GridViewFragment();

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    String[] mDrawerInput;
    private ArrayList<ImageAsyncTask> asyncArray;
    private ArrayList<String> earthsLastHope;
    String andGrab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navList);
        initializeNavDrawer();
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LandingActivity.this, "Snatch!", Toast.LENGTH_SHORT).show();
                andGrab = mDrawerInput[position];
                urlFrag.inputEditText.setText(andGrab);

                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });

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

        bm = grayScaleFilter(bm);

        downloader = new BitmapDownloader(bm);
        collection.add2BitmapArrayList(downloader);

        GridViewFragment gridFrag = (GridViewFragment) getFragmentManager().findFragmentByTag("gridViewFragment");

        gridFrag.bitmaps.add(bm);
        gridFrag.adapter.notifyDataSetChanged();
        //gridFrag.imageGridView.invalidateViews();

    }


    public void downloadActions(ArrayList<String> controlActionsAL) {

        ArrayList<ImageAsyncTask> asyncArray = new ArrayList<ImageAsyncTask>();

        for(String urlString : controlActionsAL) {
            ImageAsyncTask imageAsyncTask = new ImageAsyncTask(this, urlString);
            asyncArray.add(imageAsyncTask);
        }

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                controlActionsAL.size(), controlActionsAL.size(),
                1, TimeUnit.MINUTES, new LinkedBlockingQueue());

        Log.e(TAG, "List size: " + controlActionsAL.size());

        for(ImageAsyncTask imageAsyncTask : asyncArray) {
            imageAsyncTask.executeOnExecutor(threadPoolExecutor);
        }
    }

    public void initializeNavDrawer() {
        mDrawerInput = new String []{
                "http://lorempixel.com/400/200", "http://lorempixel.com/g/400/200",
                "http://lorempixel.com/400/200/sports/", "http://lorempixel.com/400/200/sports/1/"};

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDrawerInput);
        mDrawerList.setAdapter(mAdapter);
    }

    @Override
    public void updateURLList(ArrayList<String> stringArrayList) {
        earthsLastHope.addAll(stringArrayList);
    }

    @Override
    public void updateURLItem(String string) {
        earthsLastHope.add(string);
    }

    /**
     * Apply a grayscale filter to the @a pathToImageFile and return a
     * Uri to the filtered image.
     */
    public static Bitmap grayScaleFilter(Bitmap originalImage) {
//        Bitmap originalImage =
//                decodeImageFromPath(context,
//                        pathToImageFile);

        // Bail out if something is wrong with the image.
        if (originalImage == null)
            return null;

        Bitmap grayScaleImage =
                originalImage.copy(originalImage.getConfig(),
                        true);

        boolean hasTransparent = grayScaleImage.hasAlpha();
        int width = grayScaleImage.getWidth();
        int height = grayScaleImage.getHeight();

        // A common pixel-by-pixel grayscale conversion algorithm
        // using values obtained from en.wikipedia.org/wiki/Grayscale.
        for (int i = 0; i < height; ++i) {
            // Break out if we've been interrupted.
            if (Thread.interrupted())
                return null;

            for (int j = 0; j < width; ++j) {
                // Check if the pixel is transparent in the original
                // by checking if the alpha is 0.
                if (hasTransparent
                        && ((grayScaleImage.getPixel(j, i)
                        & 0xff000000) >> 24) == 0)
                    continue;

                // Convert the pixel to grayscale.
                int pixel = grayScaleImage.getPixel(j, i);
                int grayScale =
                        (int) (Color.red(pixel) * .299
                                + Color.green(pixel) * .587
                                + Color.blue(pixel) * .114);
                grayScaleImage.setPixel(j, i,
                        Color.rgb(grayScale,
                                grayScale,
                                grayScale));
            }
        }

        return grayScaleImage;
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
