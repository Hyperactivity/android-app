package com.hyperactivity.android_app.forum.models;

import com.hyperactivity.android_app.Utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import com.hyperactivity.android_app.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
public class Account implements SimpleCallback{


    private String profileDescription;
    private int id;
    private Date birthDate;
    private int limitPerDay;
    private boolean useDefaultColors;
    private int facebookId;
    private String username;
    private boolean showBirthDate;
    private boolean admin;
    private Bitmap profilePicture;

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

    public boolean isAdmin() {
        return admin;
    }

    public Bitmap getProfilePicture(){
//        if(profilePicture == null){
//            new GetProfilePictureTask(this).execute();
//        }
        return profilePicture;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Account)) {
            return false;
        }

        Account a = (Account) o;

        if (!Utils.objectsEqual(this.getProfileDescription(), a.getProfileDescription())) return false;

        if (this.getId() != a.getId()) return false;

        if (!Utils.objectsEqual(this.getBirthDate(), a.getBirthDate())) return false;

        if (this.getLimitPerDay() != a.getLimitPerDay()) return false;

        if (this.isUseDefaultColors() != a.isUseDefaultColors()) return false;

        if (this.getFacebookId() != a.getFacebookId()) return false;

        if (!Utils.objectsEqual(this.getUsername(), a.getUsername())) return false;

        if (this.isShowBirthDate() != a.isShowBirthDate()) return false;

        if (this.isAdmin() != a.isAdmin()) return false;

        return true;
    }

    @Override
    public void onNetworkTaskComplete(Bitmap result) {
        profilePicture = result;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }

    private class GetProfilePictureTask extends AsyncTask<Void, Void, Bitmap> {
        SimpleCallback cb;
        private GetProfilePictureTask(SimpleCallback cb) {
            this.cb = cb;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            cb.onNetworkTaskComplete(result);
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            String imageURL = "http://graph.facebook.com/"+getFacebookId()+"/picture?type=square";
            try {
                profilePicture = BitmapFactory.decodeStream((InputStream) new URL(imageURL).getContent());
            } catch (IOException e) {
                Log.d(Constants.Log.TAG, "Loading Picture FAILED");
                e.printStackTrace();
            }
            return profilePicture;
        }
    };
}


