package com.androidtitan.materialimagedownloader;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This fragment will contain:
 *  EditText for URL input
 *  Submit Button
 *  Trash button to start over
 *  Download button to launch AsyncTasks
 *  ListView of inputs
 */
public class URLInputFragment extends ListFragment {

    //Subclasses
    public ArrayAdapter adapter;

    //View elements
    private static EditText inputEditText;
    private static TextView addUrlTextView;

    //Data objects
    public ArrayList<String> urlItems = new ArrayList<String>();

    UpdateURLsInterface interfacer;

    public URLInputFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new MyListAdapter(getActivity(), urlItems);
        getListView().setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Data initializations


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_urlinput, container, false);

        inputEditText = (EditText) v.findViewById(R.id.editText);
        addUrlTextView = (TextView) v.findViewById(R.id.urlButtonTextView);

        addUrlTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!inputEditText.getText().toString().equals("")) {
                    interfacer.updateURLItem(inputEditText.getText().toString());
                    urlItems.add(inputEditText.getText().toString());
                    adapter.notifyDataSetChanged();
                    getListView().invalidate();

                    inputEditText.setText(null);
                }
                else {
                    inputEditText.setError("Missingn URL");
                }
            }
        });

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            interfacer = (UpdateURLsInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interfacer = null;
    }


}
