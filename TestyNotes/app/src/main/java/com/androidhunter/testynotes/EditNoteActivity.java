package com.androidhunter.testynotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class EditNoteActivity extends AppCompatActivity {

    private Note note;
    private EditText titleEditText;
    private EditText contentEditText;
    private Button saveNoteButton;

    private String postTitle;
    private String postContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarIndeterminateVisibility(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Intent intent = EditNoteActivity.this.getIntent();

        titleEditText = (EditText) findViewById(R.id.noteTitleEditText);
        contentEditText = (EditText) findViewById(R.id.noteCommentEditText);
        saveNoteButton = (Button) findViewById(R.id.submitButton);

        if(intent.getExtras() != null) {
            note = new Note(intent.getStringExtra("noteId"),
                    intent.getStringExtra("noteTitle"), intent.getStringExtra("noteComment"));

            titleEditText.setText(note.getTitle());
            contentEditText.setText(note.getComment());
        }

        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

    }

    public void saveNote() {

        postTitle = titleEditText.getText().toString();
        postContent = contentEditText.getText().toString();

        postTitle = postTitle.trim();
        postContent = postContent.trim();

        if(!postTitle.isEmpty()) {

            if(note == null) {
                //create new note!

                final ParseObject post = new ParseObject("Note");
                post.put("title", postTitle);
                post.put("comment", postContent);
                post.put("author", ParseUser.getCurrentUser());

                setProgressBarIndeterminateVisibility(true);

                post.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        setProgressBarIndeterminateVisibility(false);
                        if(e == null) {
                            note = new Note(post.getObjectId(), postTitle, postContent);
                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Save Failed", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });

            } else {
                //todo: update existing

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
                //query by id
                query.getInBackground(note.getId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject post, ParseException e) {
                        if(e == null) {
                            //update
                            post.put("title", postTitle);
                            post.put("comment", postContent);

                            post.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e == null) {
                                        setProgressBarIndeterminateVisibility(false);
                                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

                                    } else {

                                        Toast.makeText(getApplicationContext(), "Save Failed", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } else {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } else if(postTitle.isEmpty() && !postContent.isEmpty()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(EditNoteActivity.this);
            builder.setMessage("You cannot save a note without a title!")
                    .setTitle("Error")
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

}
