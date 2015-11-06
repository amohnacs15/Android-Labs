package com.androidtitan.materialimagedownloader;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class GridViewFragment extends Fragment {
    private String TAG = getClass().getSimpleName();
    public static String PASSABLE_EXTRA= "passableBitMapExtra";
    public static String PASSABLE_INT = "passableCountInteger";

    public GridView imageGridView;
    public MyGridAdapter adapter;

    public ArrayList<String> urlDownloaders;
    public ArrayList<Bitmap> bitmaps;

    public GridViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Drawable myDrawable = getResources().getDrawable(R.drawable.unnamed);
//        Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();

        bitmaps = new ArrayList<Bitmap>();

        adapter = new MyGridAdapter(getActivity(), bitmaps);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_grid_view, container, false);

        imageGridView = (GridView) v.findViewById(R.id.imageGridView);
        imageGridView.setAdapter(adapter);

        imageGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //compress bitmap
                //todo we need to find a way to pass them all or reference them all from that activity
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmaps.get(position).compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();

                Intent intent = new Intent(getActivity(), ViewPagerActivity.class);
                intent.putExtra(PASSABLE_EXTRA, bytes);
                intent.putExtra(PASSABLE_INT, position);

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    getActivity(), new Pair<View, String>(view, getString(R.string.image_transition)));

                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                //ActivityCompat.startActivity(

            }
        });

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

}
