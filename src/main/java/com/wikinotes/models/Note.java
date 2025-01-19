package com.wikinotes.models;

public class Note {

    private String title;
    private String content;
    private String path;
    private String lastModified;

    public Note() {
    }

    public Note(String title, String content, String path, String lastModified) {
        this.title = title;
        this.content = content;
        this.path = path;
        this.lastModified = lastModified;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
}
