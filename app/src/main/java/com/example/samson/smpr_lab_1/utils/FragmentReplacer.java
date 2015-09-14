package com.example.samson.smpr_lab_1.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class FragmentReplacer {

    private static FragmentActivity activity;
    private static int containerId;

    public static void init(final FragmentActivity _activity, int _containerId){
        activity = _activity;
        containerId = _containerId;
    }

    public static void addFragment(Fragment fragment){
        activity.getSupportFragmentManager().beginTransaction()
                .add(containerId, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    public static void replaceFragment(Fragment fragment){
        activity.getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

}
