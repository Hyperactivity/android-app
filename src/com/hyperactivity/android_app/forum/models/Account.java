package com.hyperactivity.android_app.forum.models;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
public class Account implements Externalizable {
    static final long serialVersionUID = 1L;
    private String profileDescription;
    private int id;
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

    /**
     * The object implements the writeExternal method to save its contents
     * by calling the methods of DataOutput for its primitive values or
     * calling the writeObject method of ObjectOutput for objects, strings,
     * and arrays.
     *
     * @param out the stream to write the object to
     * @throws java.io.IOException Includes any I/O exceptions that may occur
     * @serialData Overriding methods should use this tag to describe
     * the data layout of this Externalizable object.
     * List the sequence of element types and, if possible,
     * relate the element to a public/protected field and/or
     * method of this Externalizable class.
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * The object implements the readExternal method to restore its
     * contents by calling the methods of DataInput for primitive
     * types and readObject for objects, strings and arrays.  The
     * readExternal method must read the values in the same sequence
     * and with the same types as were written by writeExternal.
     *
     * @param in the stream to read data from in order to restore the object
     * @throws java.io.IOException    if I/O errors occur
     * @throws ClassNotFoundException If the class for an object being
     *                                restored cannot be found.
     */
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = in.readInt();
        profileDescription = in.readUTF();
        birthDate = (Date) in.readObject();
        limitPerDay = in.readInt();
        useDefaultColors = in.readBoolean();
        facebookId = in.readInt();
        username = in.readUTF();
        showBirthDate = in.readBoolean();
    }
}
