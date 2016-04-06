package com.androidhunter.testynotes;

/**
 * Created by amohnacs on 1/27/16.
 */
public class Note {

    private String id;
    private String title;
    private String comment;

    public Note(String cId, String cTitle, String cComment) {
        this.id = cId;
        this.title = cTitle;
        this.comment = cComment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }
}
