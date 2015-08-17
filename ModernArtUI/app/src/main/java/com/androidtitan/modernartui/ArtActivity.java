package com.androidtitan.modernartui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

public class ArtActivity extends AppCompatActivity {

    View topLeft;
    View topRight;
    View bottomLeftTop;
    View bottomRightBottom;

    SeekBar colorChangeSeeker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art);

        topLeft = (View) findViewById(R.id.topLeft);
        topRight = (View) findViewById(R.id.topRight);
        bottomLeftTop = (View) findViewById(R.id.bottomLeftTop);
        bottomRightBottom = (View) findViewById(R.id.bottomRightBottom);

        colorChangeSeeker = (SeekBar) findViewById(R.id.colorSeekBar);
        colorChangeSeeker.setProgress(0);

        colorChangeSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                topLeft.setBackgroundColor(interpolateColor(0x0266C8,
                        0xe66000, seekBar.getProgress() / 100f));
                topRight.setBackgroundColor(interpolateColor(0xF2B50F,
                        0x00539f, seekBar.getProgress() / 100f));
                bottomLeftTop.setBackgroundColor(interpolateColor(0xF90101,
                        0x0095dd, seekBar.getProgress() / 100f));
                bottomRightBottom.setBackgroundColor(interpolateColor(0x00933B,
                        0x002147, seekBar.getProgress() / 100f));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // used to take colors mix according to proportion
    private int interpolateColor(final int a, final int b,
                                 final float proportion) {
        final float[] hsva = new float[3];
        final float[] hsvb = new float[3];
        Color.colorToHSV(a, hsva);
        Color.colorToHSV(b, hsvb);
        for (int i = 0; i < 3; i++) {
            hsvb[i] = interpolate(hsva[i], hsvb[i], proportion);
        }
        return Color.HSVToColor(hsvb);
    }

    private float interpolate(final float a, final float b,
                              final float proportion) {
        return (a + ((b - a) * proportion));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_art, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.more_information) {

            FragmentManager fragMan = getSupportFragmentManager();
            OptionsDialogFragment optionsDialogFragment = new OptionsDialogFragment();


            optionsDialogFragment.show(fragMan, "optionsDialogFragment");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
