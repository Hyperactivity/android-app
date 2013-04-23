package models;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
public class Account implements Serializable {
    static final long serialVersionUID = 1L;
    private int id;
    private String profileDescription;
    private Date birthDate;
    private int limitPerDay;
    private boolean useDefaultColors;
    private int facebookId;
    private String username;
    private boolean showBirthDate;

    public int getId() {
        return id;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public int getLimitPerDay() {
        return limitPerDay;
    }

    public boolean isUseDefaultColors() {
        return useDefaultColors;
    }

    public int getFacebookId() {
        return facebookId;
    }

    public String getUsername() {
        return username;
    }

    public boolean isShowBirthDate() {
        return showBirthDate;
    }
}
