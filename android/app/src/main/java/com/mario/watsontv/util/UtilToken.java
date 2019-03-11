package com.mario.watsontv.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.mario.watsontv.R;
import com.mario.watsontv.responses.LoginResponse;
import com.mario.watsontv.responses.UserResponse;

public class UtilToken {
    public static void setToken(Context mContext, String token) {
        SharedPreferences sharedPreferences =
                mContext.getSharedPreferences(
                        mContext.getString(R.string.sharedpreferences_filename),
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(mContext.getString(R.string.jwt_key), token);
        editor.commit();
    }

    public static String getId(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mContext.getString(R.string.sharedpreferences_filename),
                Context.MODE_PRIVATE
        );

        String id = sharedPreferences
                .getString(mContext.getString(R.string.userId), null);

        return id;
    }

    public static void setId(Context mContext, String id) {
        SharedPreferences sharedPreferences =
                mContext.getSharedPreferences(
                        mContext.getString(R.string.sharedpreferences_filename),
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(mContext.getString(R.string.userId), id);
        editor.commit();
    }

    public static void setUserLoggedData(Context mContext, UserResponse loggedUser) {
        SharedPreferences sharedPreferences =
                mContext.getSharedPreferences(
                        mContext.getString(R.string.sharedpreferences_filename),
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (loggedUser != null) {
            editor.putString(mContext.getString(R.string.userName), loggedUser.getName());
            editor.putString(mContext.getString(R.string.userEmail), loggedUser.getEmail());
        } else {
            editor.putString(mContext.getString(R.string.userName), "");
            editor.putString(mContext.getString(R.string.userEmail), "");
        }
        editor.commit();
    }

    public static String getEmail(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mContext.getString(R.string.sharedpreferences_filename),
                Context.MODE_PRIVATE
        );

        String userEmail = sharedPreferences
                .getString(mContext.getString(R.string.userEmail), null);

        return userEmail;
    }

    public static String getName(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mContext.getString(R.string.sharedpreferences_filename),
                Context.MODE_PRIVATE
        );

        String userName = sharedPreferences
                .getString(mContext.getString(R.string.userName), null);

        return userName;
    }

    public static String getToken(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mContext.getString(R.string.sharedpreferences_filename),
                Context.MODE_PRIVATE
        );

        String jwt = sharedPreferences
                .getString(mContext.getString(R.string.jwt_key), null);

        return jwt;
    }
}
