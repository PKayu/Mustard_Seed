package com.example.mustardseed;

import androidx.annotation.NonNull;

public class Scripture {

    private String _standWork;
    private String _book;
    private int _chapter;
    private int _verse;

    public Scripture(String standWork, String book, int chapter, int verse){
        this._standWork = standWork;
        this._book = book;
        this._chapter = chapter;
        this._verse = verse;
    }

    public String getStandWork() {
        return _standWork;
    }

    public void setStandWork(String standWork) {
        this._standWork = standWork;
    }

    public String getBook() {
        return _book;
    }

    public void setBook(String book) {
        this._book = book;
    }

    public int getChapter() {
        return _chapter;
    }

    public void setChapter(int chapter) {
        this._chapter = chapter;
    }

    public int getVerse() {
        return _verse;
    }

    public void setVerse(int verse) {
        this._verse = verse;
    }

    @NonNull
    @Override
    public String toString() {
        return _book + " " + _chapter + ":" + _verse;
    }
}
