package com.hyperactivity.android_app.forum.models;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-22
 * Time: 11:05
 */
public class Category {
    private int id;
    private String headLine;
    private int colorCode;
    private Category parentCategory;
    private List<Thread> threads;

    public int getId() {
        return id;
    }

    public String getHeadLine() {
        return headLine;
    }

    public int getColorCode() {
        return colorCode;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

    public List<Thread> getThreads() {
        return threads;
    }
}