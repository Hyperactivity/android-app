package models;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
public class Note implements Serializable {
    static final long serialVersionUID = 5L;
    private int id;
    private String headLine;
    private String text;
    private Account account;
    private Category parentPrivateCategory;

    public int getId() {
        return id;
    }

    public String getHeadLine() {
        return headLine;
    }

    public String getText() {
        return text;
    }

    public Account getAccount() {
        return account;
    }

    public Category getParentPrivateCategory() {
        return parentPrivateCategory;
    }
}
