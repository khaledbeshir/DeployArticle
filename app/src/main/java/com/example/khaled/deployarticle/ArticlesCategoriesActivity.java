package com.example.khaled.deployarticle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by khaled on 7/16/2019.
 */

public class ArticlesCategoriesActivity extends SingleFragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Articles Categories");
    }

    public static Intent newIntent (Context context){
        Intent intent = new Intent(context , ArticlesCategoriesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }


    @Override
    public Fragment CreateFragment() {
        return new ArticlesCategoriesFragment();
    }
}
