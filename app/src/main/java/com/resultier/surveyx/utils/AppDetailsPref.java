package com.resultier.surveyx.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class AppDetailsPref {
    public static String USER_NAME = "user_name";
    public static String USER_MOBILE = "user_mobile";
    public static String USER_LOGIN_KEY = "user_login_key";
    public static String SURVEY_ID = "survey_id";
    public static String SURVEY_NUMBER = "survey_number";
    public static String SURVEY_STATUS = "survey_status";
    public static String SURVEY_DAY_ELAPSED = "survey_days_elapsed";
    public static String PRODUCT_ID = "product_id";
    public static String PRODUCT_CODE = "product_code";
    
    public static String BUTTON1 = "button1";
    public static String BUTTON2 = "button2";
    public static String BUTTON3 = "button3";
    public static String BUTTON4 = "button4";
    
    
    private static AppDetailsPref appDetailsPref;
    private String APP_DETAILS = "APP_DETAILS";
    
    public static AppDetailsPref getInstance () {
        if (appDetailsPref == null)
            appDetailsPref = new AppDetailsPref ();
        return appDetailsPref;
    }

    private SharedPreferences getPref (Context context) {
        return context.getSharedPreferences (APP_DETAILS, Context.MODE_PRIVATE);
    }

    public String getStringPref (Context context, String key) {
        return getPref (context).getString (key, "");
    }

    public int getIntPref (Context context, String key) {
        return getPref (context).getInt (key, 0);
    }

    public boolean getBooleanPref (Context context, String key) {
        return getPref (context).getBoolean (key, false);
    }

    public void putBooleanPref (Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getPref (context).edit ();
        editor.putBoolean (key, value);
        editor.apply ();
    }

    public void putStringPref (Context context, String key, String value) {
        SharedPreferences.Editor editor = getPref (context).edit ();
        editor.putString (key, value);
        editor.apply ();
    }

    public void putIntPref (Context context, String key, int value) {
        SharedPreferences.Editor editor = getPref (context).edit ();
        editor.putInt (key, value);
        editor.apply ();
    }
}
