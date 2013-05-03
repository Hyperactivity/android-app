package com.hyperactivity.android_app.forum.models;

import java.util.LinkedList;
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
    private String image;

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
        if(threads == null) {
            threads = new LinkedList<Thread>();
        }

        return threads;
    }

    public String getImageName() {
        String path = "";
        String extension = ".png";

        switch (getId()) {
            case 1:
                return path + "c_contact" + extension;
            case 2:
                return path + "c_creativity" + extension;
            case 3:
                return path + "c_general" + extension;
            case 4:
                return path + "c_hobby" + extension;
            case 5:
                return path + "c_medicine" + extension;
            case 6:
                return path + "c_school" + extension;
            case 8:
                return path + "c_tips" + extension;
            default:
                return null;
        }
    }
}
