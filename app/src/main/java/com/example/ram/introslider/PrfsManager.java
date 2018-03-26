package com.example.ram.introslider;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ram on 26/3/18.
 */

public class PrfsManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context mCtx;
    private static PrfsManager minstance;
    private static final String PREF_NAME = "LAUNCH";
    private static final String IS_FIRST_TIME_LAUNCH = "IS_FIRST_LAUNCH";

    private PrfsManager(Context context) {
        mCtx = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static PrfsManager getInstance(Context context) {
        if (minstance == null) {
            minstance = new PrfsManager(context);
        }
        return minstance;
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
