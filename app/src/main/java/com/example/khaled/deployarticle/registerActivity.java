package com.example.khaled.deployarticle;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by khaled on 7/16/2019.
 */

public class registerActivity extends SingleFragmentActivity {


    public static Intent newIntent(Context context){
        Intent intent = new Intent(context , registerActivity.class);
        return  intent;
    }


    @Override
    public Fragment CreateFragment() {
        return new registerFragment();
    }
}
