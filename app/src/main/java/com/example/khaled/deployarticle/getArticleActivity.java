package com.example.khaled.deployarticle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by khaled on 7/16/2019.
 */

public class getArticleActivity extends SingleFragmentActivity {

    public static final String ArticleCategory = "category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String category = getIntent().getStringExtra(ArticleCategory).toString();
        getSupportActionBar().setTitle(category +" Articles");
    }

    public static Intent newIntent(Context context , String category){
        Intent intent = new Intent(context , getArticleActivity.class);
        intent.putExtra(ArticleCategory , category);
        return intent;
    }


    @Override
    public Fragment CreateFragment() {
        return new getArticlesFragment();
    }



}
