package com.bylawreport.flow.bylawreport.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by DN on 2016-12-23.
 */
public class SharedPrefSingleton {

    private static SharedPrefSingleton prefInstance;
    private Context context;

    private SharedPreferences myPreferences;

    private SharedPrefSingleton(){ }

    public static SharedPrefSingleton getInstance(){
        if (prefInstance == null) prefInstance = new SharedPrefSingleton();
        return prefInstance;
    }

    public void Initialize(Context ctxt){
        context = ctxt;
        myPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void writePreference(String key, String value){
        SharedPreferences.Editor e = myPreferences.edit();
        e.putString(key, value);
        e.commit();
    }

    /**
     * Return the preferences by provided input param name
     * @param prefName
     * @return
     */
    public String getPreferenceByName(String prefName){
        return myPreferences.getString(prefName, "");
    }
    
}
