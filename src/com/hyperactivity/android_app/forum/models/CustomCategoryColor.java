package com.hyperactivity.android_app.forum.models;

import com.hyperactivity.android_app.Utils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */

public class CustomCategoryColor{
    private int colorCode;
    private Account account;
    private Category category;

    public Account getAccount() {
        return account;
    }

    public Category getCategory() {
        return category;
    }

    public int getColorCode() {
        return colorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CustomCategoryColor)) return false;

        CustomCategoryColor c = (CustomCategoryColor) o;

        if (this.colorCode != c.colorCode) return false;

        if (!Utils.objectsEqual(this.getAccount(), c.getAccount())) return false;

        if (!Utils.objectsEqual(this.getCategory(), c.getCategory())) return false;

        return true;
    }
}
