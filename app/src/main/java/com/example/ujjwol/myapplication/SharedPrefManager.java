package com.example.ujjwol.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ujjwol on 2/8/2018.
 */

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME="mysharedpref12";
    private static final String KEY_USER_NAME="username";
    private static final String KEY_USER_EMAIL="email";
    private static final String KEY_USER_SN="sn";
    private static final String KEY_USER_ROLLNO="rollno";

    private SharedPrefManager(Context ctx)
    {
        mCtx=ctx;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if (mInstance==null){
            mInstance= new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int sn,String name,String rollno,String email){
        SharedPreferences sharedPreferences= mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(KEY_USER_SN,sn);
        editor.putString(KEY_USER_NAME,name);
        editor.putString(KEY_USER_ROLLNO,rollno);
        editor.putString(KEY_USER_EMAIL,email);
        editor.apply();
        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_ROLLNO,null)!=null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getUsername(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_NAME,"Name");

    }
    public String getUserEmail(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL,"Email");

    }
    public String getUserRollno(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ROLLNO,"RollNo");

    }
}
