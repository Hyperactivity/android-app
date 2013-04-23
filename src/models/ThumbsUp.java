package models;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
public class ThumbsUp implements Serializable {
    static final long serialVersionUID = 10L;
    private int replyId;
    private int accountId;
    private Reply reply;
    private Account account;

    public int getReplyId() {
        return replyId;
    }

    public int getAccountId() {
        return accountId;
    }

    public Reply getReply() {
        return reply;
    }

    public Account getAccount() {
        return account;
    }
}
