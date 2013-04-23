package models;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-22
 * Time: 11:05
 */
public class Category implements Serializable {
    static final long serialVersionUID = 2L;
    private int id;
    private String headLine;
    private int colorCode;
    private Category parentCategory;
    private List<Category> childCategories;
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

    public Category getParentCategory() {
        return parentCategory;
    }

    public List<Category> getChildCategories() {
        return childCategories;
    }

    public List<Thread> getThreads() {
        return threads;
    }
}
