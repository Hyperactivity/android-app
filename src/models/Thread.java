package models;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:14
 */
public class Thread implements Serializable {
    static final long serialVersionUID = 9L;
    private int parentCategoryId;
    private int id;
    private String headLine;
    private String text;
    private List<Reply> replies;
    private Account account;
    private Category parentCategory;

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public int getId() {
        return id;
    }

    public String getHeadLine() {
        return headLine;
    }

    public String getText() {
        return text;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public Account getAccount() {
        return account;
    }

    public Category getParentCategory() {
        return parentCategory;
    }


}
