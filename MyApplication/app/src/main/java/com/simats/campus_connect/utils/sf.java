package com.simats.campus_connect.utils;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
public interface sf {

    String userId = "userId";

    String name= "name";

    String marks_12th ="marks_12th";

    String college_id = "college_id";

    String college_name= "college_name";




    static SharedPreferences getLoginSF(@NonNull FragmentActivity activity) {
        String LOGIN_SHARED_PREFS = "LOGIN_SHARED_PREFS";
        return activity.getSharedPreferences(LOGIN_SHARED_PREFS, Context.MODE_PRIVATE);
    }

    static SharedPreferences.Editor getLoginSFEditor(FragmentActivity activity) {
        return getLoginSF(activity).edit();
    }

    static void setLoginSFValue(FragmentActivity activity, String key, String value) {
        getLoginSFEditor(activity).putString(key, value).apply();
    }

    static void setLoginSFValue(FragmentActivity activity, String key, int value) {
        getLoginSFEditor(activity).putInt(key, value).apply();
    }

    static void setLoginSFValue(FragmentActivity activity, String key, Boolean value) {
        getLoginSFEditor(activity).putBoolean(key, value).apply();
    }

    static void clearLoginSF(FragmentActivity activity) {
        getLoginSFEditor(activity).clear().apply();
    }

    /* END */

}
