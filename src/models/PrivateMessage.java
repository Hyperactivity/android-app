package models;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
public class PrivateMessage implements Serializable {
    static final long serialVersionUID = 7L;
    private int senderAccountId;
    private int recieverAccountId;
    private String text;

    public int getSenderAccountId() {
        return senderAccountId;
    }

    public int getRecieverAccountId() {
        return recieverAccountId;
    }

    public String getText() {
        return text;
    }
}
