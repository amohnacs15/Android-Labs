package com.androidtitan.materialimagedownloader;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
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

public class LandingActivity extends Activity implements UpdateURLsInterface {
    private String TAG = getClass().getSimpleName();
    public static String URLEXTRA = "urlArrayListExtra";
    public static String BTN_X_EXTRA = "buttonXExtra";
    public static String BTN_Y_EXTRA = "buttonYExtra";

    GridViewActivity gridActivity = new GridViewActivity();

    private URLInputFragment urlFrag = new URLInputFragment();

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
        transaction.replace(R.id.container, urlFrag);
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


                    Intent intent = new Intent(LandingActivity.this, GridViewActivity.class);
                    intent.putExtra(URLEXTRA, earthsLastHope);
                    intent.putExtra(BTN_X_EXTRA, (int) downloadFab.getX());
                    intent.putExtra(BTN_Y_EXTRA, (int) downloadFab.getY());
                    startActivity(intent);


                    earthsLastHope.clear();
                    urlFrag.urlItems.clear();
                    urlFrag.adapter.notifyDataSetChanged();

                    urlFrag.getListView().invalidate();

                } else {
                    //todo: cool material design animation
                }
            }
        });

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




}
