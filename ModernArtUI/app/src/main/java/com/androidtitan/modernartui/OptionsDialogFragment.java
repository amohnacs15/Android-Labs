package com.androidtitan.modernartui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;


public class OptionsDialogFragment extends DialogFragment {
    private static final String TAG= "optionsDialogFragment";
    private static final String URL ="http://www.moma.org";

    TextView dismiss;
    TextView internalIntent;

    public OptionsDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_options_dialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        dismiss = (TextView) v.findViewById(R.id.dismiss);
        internalIntent = (TextView) v.findViewById(R.id.proceedToMoma);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });

        internalIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Entered startImplicitActivation()");

                String TITLE_TEXT = "Choose an app...";

                Intent baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));

                startActivity(baseIntent);

            }
        });

        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
