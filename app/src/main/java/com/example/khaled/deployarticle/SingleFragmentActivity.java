package com.example.khaled.deployarticle;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mohamed Amr on 6/22/2018.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public abstract Fragment CreateFragment();

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager
                .findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = CreateFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container,fragment).commit();
        }
    }
}
