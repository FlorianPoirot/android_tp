package io.gresse.hugo.tp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by raziel-11 on 04/12/2017.
 */

public class UserStorage {

    public static String getName(Context context){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString("USER_NAME", null);
    }

    public static String getEmail(Context context){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString("USER_EMAIL", null);
    }

    public static void saveUserInfo(Context context, String name, String email) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("USER_NAME", name);
        editor.putString("USER_EMAIL", email);
        editor.apply();
    }


}
