package com.example.samson.smpr_lab_1;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.samson.smpr_lab_1.fragments.MainFragment;
import com.example.samson.smpr_lab_1.utils.FragmentReplacer;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentReplacer.init(this, R.id.container);
        FragmentReplacer.addFragment(new MainFragment());
    }
}
