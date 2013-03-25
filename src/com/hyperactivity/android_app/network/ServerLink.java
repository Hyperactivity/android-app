package com.hyperactivity.android_app.network;

import android.graphics.Bitmap;
import com.hyperactivity.android_app.core.Account;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.core.Profile;
import com.hyperactivity.android_app.core.Utils;
import com.hyperactivity.android_app.forum.Category;

import java.util.Date;
import java.util.UUID;

/*
//TODO: enable the fb stuff
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
*/

public class ServerLink {
    Engine engine;

    public ServerLink(Engine engine) {
        this.engine = engine;
    }

    public void login(final NetworkCallback activityCallBack) {

        /*
        TODO: fix fb stuff
        Response facebookUser = getFacebookUserInfo();
        String email = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.EMAIL);
        String userId = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.ID);
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.EMAIL, email);

        sendRequest(Constants.Methods.LOGIN, userId, params, activityCallBack, true);
        */
    }

    //TODO: old login
    public Account login(String username, String password) {
        // TODO: make server auth request.
        // TODO: this method should also do take a callback
        boolean auth = true;

        if (auth) {
            // Populate an account with dummy data for now
            Account account = new Account(UUID.randomUUID().toString());
            account.setUsername(username);
            account.setProfile(getProfile(account.getId()));
            account.setLoaded(true);

            return account;
        } else {
            return null;
        }
    }

    //TODO: new register
    public void register(String username, final NetworkCallback activityCallBack) {

        /*
        TODO: fix fb stuff
        Response facebookUser = getFacebookUserInfo();
        String email = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.EMAIL);
        String userId = (String) facebookUser.getGraphObject().getProperty(Constants.Transfer.ID);
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Transfer.EMAIL, email);
        params.put(Constants.Transfer.USERNAME, username);

        sendRequest(Constants.Methods.REGISTER, userId, params, activityCallBack, true);
        */
    }

    //TODO: old registe
    public Account createAccount(String username, String password, Date birthdate) {
        //TODO: make request to server
        //TODO: this method should also take a callback
        boolean success = true;

        if (success) {
            Account account = new Account(UUID.randomUUID().toString());
            account.setUsername(username);
            account.setLoaded(true);
            account.setProfile(uploadProfile(new Profile(account.getId())));
            return account;
        } else {
            return null;
        }
    }

    //---------------------------------------------------------------
    //TODO: all methods below should be using callback

    public Profile getProfile(String id) {
        // TODO: make server auth request.
        boolean exist = true;

        if (exist) {
            // Populate an profile with dummy data for now
            Profile profile = new Profile(UUID.randomUUID().toString());
            profile.setAvatar(Bitmap.createBitmap(32, 32, Bitmap.Config.ARGB_8888));
            profile.setBirthdate(new Date());
            profile.setDescription("Im a dummy user :)");
            profile.setLoaded(true);

            return profile;
        } else {
            return null;
        }
    }

    public Profile uploadProfile(Profile profile) {
        if (profile.isLoaded()) {
            // Make sure profile and currently logged in user have the same id.
            if (Utils.sameId(engine.getAccount(), profile)) {
                // TODO: make the server request
                boolean success = true;

                if (success) {
                    return profile;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean changePassword(String password) {
        if (engine.getAccount().isLoaded()) {
            // TODO: make the password change remote
            boolean success = true;

            if (success) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Category getCategory(String id) {
        //TODO: make request to server
        boolean success = true;

        if (success) {
            //Load dummy data for now
            Category category;

            if (engine.isPrivateMode()) {
                //Generate dummy data category for private category
                category = new Category(UUID.randomUUID().toString(), "How to happy");
                category.createThread(engine.getAccount(), "Eat bananas", "It makes me more happy because I like banans.");
                category.createThread(engine.getAccount(), "Oranges work too", "Just realized");
                category.createThread(engine.getAccount(), "I think I like fruit", "Maybe thats why I like banans and oranges");

                //TODO: test with subcategories later.

                category.setLoaded(true);
            } else {
                //generate dummy data for public category
                category = new Category(UUID.randomUUID().toString(), "The subject of cats");

                //TODO: Create threads by different authors.

                category.setLoaded(true);
            }

            return category;
        } else {
            return null;
        }
    }
}
