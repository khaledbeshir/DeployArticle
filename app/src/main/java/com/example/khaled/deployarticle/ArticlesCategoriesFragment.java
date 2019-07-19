package com.example.khaled.deployarticle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ArticlesCategoriesFragment extends Fragment {

    private ArrayList<String> categoriesArray;
    private RecyclerView categoriesRecycleView;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mAuth;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoriesArray = new ArrayList<String>();
        categoriesArray.add("sport");
        categoriesArray.add("political");
        categoriesArray.add("technology");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        setHasOptionsMenu(true);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mFirebaseUser == null){
            Intent intent = loginActivity.newIntent(getActivity());
            startActivity(intent);
        }
        else{


        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_articles_categories  , container , false);
        categoriesRecycleView = v.findViewById(R.id.aricles_categories_recycleview);
        categoriesRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoriesRecycleView.setAdapter(new categoriesList());
        return v;
    }


    private class categoriesList extends RecyclerView.Adapter<CategoryHolder> {

        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v =getLayoutInflater().inflate(R.layout.articles_category_item , parent ,false);
            return new CategoryHolder(v);
        }

        @Override
        public void onBindViewHolder(final CategoryHolder holder, final int position) {
            holder.CategoryItem.setText(categoriesArray.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String category = categoriesArray.get(position);
                    Intent intent = getArticleActivity.newIntent(view.getContext() , category);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return categoriesArray.size();
        }
    }



    private static class CategoryHolder extends RecyclerView.ViewHolder
    {

        Button CategoryItem ;
        public CategoryHolder(View itemView) {
            super(itemView);
            CategoryItem = itemView.findViewById(R.id.category_item);
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.articles_categories , menu );

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout :
                mAuth.signOut();
                Intent intent = loginActivity.newIntent(getActivity());
                startActivity(intent);
                break;

            default:
                return super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }


}
