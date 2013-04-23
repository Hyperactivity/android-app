package models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:14
 */
public class Reply implements Serializable {
    static final long serialVersionUID = 8L;
    private int id;
    private Timestamp time;
    private String text;
    private Thread parentThread;
    private Account account;
    private List<ThumbsUp> thumbsUp;

    public int getId() {
        return id;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public Thread getParentThread() {
        return parentThread;
    }

    public Account getAccount() {
        return account;
    }

    public List<ThumbsUp> getThumbsUp() {
        return thumbsUp;
    }
}
