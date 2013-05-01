package com.hyperactivity.android_app.forum.models;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-22
 * Time: 11:05
 */
public class Category{
    private int id;
    private String headLine;
    private int colorCode;
    private Category parentCategory;

    public int getId() {
        return id;
    }

    public String getHeadLine() {
        return headLine;
    }

    public int getColorCode() {
        return colorCode;
    }
}
