package models;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */

public class CustomCategoryColor implements Serializable {
    static final long serialVersionUID = 3L;
    private int accountId;
    private int categoryId;
    private int colorCode;

    public int getAccountId() {
        return accountId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getColorCode() {
        return colorCode;
    }
}
