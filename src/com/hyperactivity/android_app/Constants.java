package com.hyperactivity.android_app;

/**
 * Created with IntelliJ IDEA.
 * User: lucas
 * Date: 3/25/13
 * Time: 2:17 PM
 */
public class Constants {
    public class Log {
        public static final String TAG = "android-app"; //To be used as tag with all android log calls.
    }

    public class Server {
        public static final String IP = "TODO";
        public static final String PORT = "TODO";
        public static final int TIMEOUT = 10000;
    }

    public class Methods {
        public static final String LOGIN = "login";
        public static final String REGISTER = "register";
        public static final String GET_PROFILE = "getProfile";
    }

    public class Transfer {
        public static final String VALUE = "value";
        public static final String FAILED = "failed";

        public static final String ID = "id";
        public static final String RESULT = "result";
        public static final String EMAIL = "email";
        public static final String USERNAME = "username";
        public static final String FACEBOOK_ID = "facebookID";
        public static final String USER_ID = "userID";
        public static final String SUCCESS = "success";
    }
}
