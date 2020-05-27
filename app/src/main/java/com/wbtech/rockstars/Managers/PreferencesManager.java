package com.wbtech.rockstars.Managers;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;

public class PreferencesManager {
    private SharedPreferences preferences;

    private static PreferencesManager INSTANCE = null;
    private static final String BOOKMARKS_PREFS = "bookMarksPref";
    private static final String PROFILE_IMAGE_PREFS = "profileImage";
    private static final String PROFILE_NAME_PREFS = "profileName";

    private PreferencesManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public static synchronized PreferencesManager getInstance(SharedPreferences preferences) {
        if (INSTANCE == null) {
            INSTANCE = new PreferencesManager(preferences);
        }
        return INSTANCE;
    }

    //Save rock star
    public void saveRockStarToBookMark(String name) {
        Set<String> set = new HashSet<>(preferences.getStringSet(BOOKMARKS_PREFS, new HashSet<String>()));

        set.add(name);
        preferences.edit().putStringSet(BOOKMARKS_PREFS, set).apply();
    }

    //remove rock star
    public void removeRockStarFromBookMark(String name) {
        Set<String> set = new HashSet<>(preferences.getStringSet(BOOKMARKS_PREFS, new HashSet<String>()));
        if (set.contains(name)) {
            set.remove(name);
            preferences.edit().putStringSet(BOOKMARKS_PREFS, set).apply();
        }
    }

    //retrieve saved rock stars
    public Set<String> getBookMark() {
        return new HashSet<>(preferences.getStringSet(BOOKMARKS_PREFS, new HashSet<String>()));

    }

    //Save profile image and name in sharedPreferences
    public void saveProfile(Bitmap realImage, String name) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        realImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        preferences.edit().putString(PROFILE_IMAGE_PREFS, encodedImage).apply();
        preferences.edit().putString(PROFILE_NAME_PREFS, name).apply();

    }

    //retrieve profile image
    public Bitmap getProfileImage() {
        String encodedImage = preferences.getString(PROFILE_IMAGE_PREFS, "");

        if (!encodedImage.equalsIgnoreCase("")) {
            byte[] b = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            return bitmap;
        }
        return null;
    }

    //retrieve profile name
    public String getProfileName() {
        return preferences.getString(PROFILE_NAME_PREFS, "");
    }
}
