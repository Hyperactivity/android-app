package models;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
public class PrivateCategory implements Serializable {
    static final long serialVersionUID = 6L;
    private int id;
    private int colorCode;
    private Category parentPrivateCategory;
    private Account account;

    public int getId() {
        return id;
    }

    public int getColorCode() {
        return colorCode;
    }

    public Category getParentPrivateCategory() {
        return parentPrivateCategory;
    }

    public Account getAccount() {
        return account;
    }
}
