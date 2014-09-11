package com.lsimberg.smartzone;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by leo on 4/22/14.
 */
public class Persistency {
    public static final String UUID = "e2c56db5-dffb-48d2-b060-d0f5a71096aa";

    public static void saveBool(Context ctx, String key, boolean value){
        SharedPreferences prefs = ctx.getSharedPreferences(
                ctx.getPackageName(), Context.MODE_PRIVATE);
        prefs.edit().putBoolean(key, value).apply();
    }

    public static boolean loadBool(Context ctx, String key){
        SharedPreferences prefs = ctx.getSharedPreferences(
                ctx.getPackageName(), Context.MODE_PRIVATE);
        return prefs.getBoolean(key, true);
    }


    public static void saveInt(Context ctx, String key, int value){
        SharedPreferences prefs = ctx.getSharedPreferences(
                ctx.getPackageName(), Context.MODE_PRIVATE);
        prefs.edit().putInt(key, value).apply();
    }

    public static int loadInt(Context ctx, String key){
        SharedPreferences prefs = ctx.getSharedPreferences(
                ctx.getPackageName(), Context.MODE_PRIVATE);
        return prefs.getInt(key, -1);
    }

}
