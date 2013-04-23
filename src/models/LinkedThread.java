package models;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
public class LinkedThread implements Serializable {
    static final long serialVersionUID = 4L;
    private int id;
    private String headLine;
    private Category parentPrivateCategory;
    private Account account;

    public int getId() {
        return id;
    }

    public String getHeadLine() {
        return headLine;
    }

    public Category getParentPrivateCategory() {
        return parentPrivateCategory;
    }

    public Account getAccount() {
        return account;
    }
}
