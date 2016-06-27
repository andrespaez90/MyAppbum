package com.dev.innso.myappbum.utils;

import android.content.SharedPreferences;

import com.dev.innso.myappbum.utils.tags.SharedPrefFiles;
import com.dev.innso.myappbum.utils.tags.SharedPrefKeys;
import com.dev.innso.myappbum.app.Appbum;
import com.jpardogo.listbuddies.lib.views.ListBuddiesLayout;

public class SharePreferences {

    public static void saveDataApplication(SharedPrefKeys preKey, String value){
        SharedPreferences sharedPreference = getCustomizePref();
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(preKey.toString(), value);
        editor.commit();
    }

    public static void saveCustomization(SharedPrefKeys prefKey, int progress) {
        SharedPreferences customize_pref = getCustomizePref();
        SharedPreferences.Editor editor = customize_pref.edit();
        editor.putInt(prefKey.toString(), progress);
        editor.commit();
    }

    public static int getValue(SharedPrefKeys prefKey) {
        SharedPreferences customize_pref = getCustomizePref();
        int defaultValue = getDefaultValue(prefKey);
        return customize_pref.getInt(prefKey.toString(), defaultValue);
    }

    public static String getApplicationValue(SharedPrefKeys prefKey){
        SharedPreferences customize_pref = getCustomizePref();
        int defaultValue = getDefaultValue(prefKey);
        return customize_pref.getString(prefKey.toString(), "");
    }

    private static SharedPreferences getCustomizePref() {
        return Appbum.getAppContext().getSharedPreferences(SharedPrefFiles.CUSTOMIZE_SETTINGS.toString(), 0);
    }

    private static String getAplicationDefaultValue(SharedPrefKeys prefKey){
        String defaultValue = "";
        switch (prefKey){
            case ACCESS_TOKEN:
                //default Value
                break;
            case FACEBOOK_USERID:
                //default Value
                break;
            case EMAIL_USER:
                //default Value
                break;
        }
        return defaultValue;
    }

    private static int getDefaultValue(SharedPrefKeys prefKey) {
        int defaultValue = 0;
        switch (prefKey) {
            case GAP_PROGRESS:
                defaultValue = Appbum.getAppContext().getResources().getDimensionPixelSize(com.jpardogo.listbuddies.lib.R.dimen.default_margin_between_lists);
                break;
            case SPEED_PROGRESS:
                defaultValue = ListBuddiesLayout.DEFAULT_SPEED;
                break;
            case DIV_HEIGHT_PROGRESS:
                defaultValue = Appbum.getAppContext().getResources().getDimensionPixelSize(com.jpardogo.listbuddies.lib.R.dimen.default_margin_between_lists);
                break;
        }
        return defaultValue;
    }

    public static void resetUser() {
        saveDataApplication(SharedPrefKeys.USER_ID,"");
        saveDataApplication(SharedPrefKeys.FACEBOOK_USERID,"");
        saveDataApplication(SharedPrefKeys.NAME_USER,"");
        saveDataApplication(SharedPrefKeys.PROFILE_USER,"");
        saveDataApplication(SharedPrefKeys.COVER_USER,"");
    }

    public static void resetBuddies() {
        for (SharedPrefKeys key : SharedPrefKeys.values()) {
            saveCustomization(key, getDefaultValue(key));
        }
    }
}