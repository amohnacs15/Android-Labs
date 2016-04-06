package com.androidhunter.testynotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    ArrayList<Note> notes;

    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);

        ParseUser currentUser = ParseUser.getCurrentUser();

        if(currentUser == null) {
            loadLoginView();
        }

        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*Parse.enableLocalDatastore(ListActivity.this);
        Parse.initialize(ListActivity.this);*/

        notes = new ArrayList<Note>();
        adapter = new NoteAdapter(ListActivity.this, notes);
        ListView listView = (ListView) findViewById(R.id.myListView);
        listView.setAdapter(adapter);

        refreshPosts();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = notes.get(position);

                Intent intent = new Intent(ListActivity.this, EditNoteActivity.class);
                intent.putExtra("noteId", note.getId());
                intent.putExtra("noteTitle", note.getTitle());
                intent.putExtra("noteComment", note.getComment());
                startActivity(intent);
            }
        });
    }

    /**
     * This method will fetch data from parse and assign it to our list of notes
     */
    private void refreshPosts() {
        setProgressBarIndeterminateVisibility(true);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
        query.whereEqualTo("author", ParseUser.getCurrentUser());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> noteList, ParseException e) {
                if (e == null) { //there is no error and the query was successful
                    setProgressBarIndeterminateVisibility(false);
                    //update
                    notes.clear();
                    for (ParseObject note : noteList) {
                        Note tempNote = new Note(note.getObjectId(), note.getString("title"),
                                note.getString("comment"));
                        notes.add(tempNote);
                    }
                    //((NoteAdapter) adapter).notifyDataSetChanged();
                    adapter.notifyDataSetChanged();

                } else { //something went wrong
                    e.printStackTrace();

                }
            }
        });
    }

    public void loadLoginView() {
        Intent intent = new Intent(ListActivity.this, LoginActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.action_refresh: {
                refreshPosts();
                break;
            }

            case R.id.action_new: {
                Intent intent = new Intent(this, EditNoteActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.action_logout: {
                ParseUser.logOut();
                loadLoginView();

                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
