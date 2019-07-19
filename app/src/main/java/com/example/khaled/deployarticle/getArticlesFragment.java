package com.example.khaled.deployarticle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by khaled on 7/16/2019.
 */

public class getArticlesFragment extends android.support.v4.app.Fragment {

    private DatabaseReference CategoryRef ,ArticleRef ,UserRef;
    private FirebaseAuth mAuth;
    private EditText ArticleTitle , ArticleContent ;
    private TextView DisplayArticles;
    private Button postButton;
    private String CurrentDate ,CurrentTime , CurrentUserId ,CurrentUserName;
    private ScrollView  mScrollView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String category = getActivity().getIntent().getStringExtra(getArticleActivity.ArticleCategory);

        mAuth = FirebaseAuth.getInstance();
        CurrentUserId = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        CategoryRef = FirebaseDatabase.getInstance().getReference().child("Categories").child(category);

        setHasOptionsMenu(true);
        getUserInfo();
    }


    @Override
    public void onStart() {
        super.onStart();

        CategoryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()){
                    DisplayPosts(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_get_articles , container , false);
        ArticleTitle = (EditText) v.findViewById(R.id.article_title_Textview);
        ArticleContent =(EditText) v.findViewById(R.id.article_content_textview);
        DisplayArticles = (TextView) v.findViewById(R.id.display);

        postButton = (Button)v.findViewById(R.id.post_button);
        mScrollView = v.findViewById(R.id.scroll_view);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postToDataBase();
                ArticleTitle.setText("");
                ArticleContent.setText("");
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        return v;
    }



    private void postToDataBase() {

        String title = ArticleTitle.getText().toString();

        String content = ArticleContent.getText().toString();
        String messagekey = CategoryRef.push().getKey();

        if (TextUtils.isEmpty(title)){
            Toast.makeText(getActivity(), "please enter Artice title", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(content)){
            Toast.makeText(getActivity(), "please enter Artice content", Toast.LENGTH_SHORT).show();
        }
        else {

            Calendar DateCal = Calendar.getInstance();
            SimpleDateFormat DateFormat = new SimpleDateFormat("mmm dd,yyyy");
            CurrentDate = DateFormat.format(DateCal.getTime());

            Calendar TimeCal = Calendar.getInstance();
            SimpleDateFormat TimeFormat = new SimpleDateFormat("hh:mm");
            CurrentTime = TimeFormat.format(TimeCal.getTime());

            HashMap<String , Object> postInfMap = new HashMap<>();
            postInfMap.put("title", title);
            postInfMap.put("content", content);
            postInfMap.put("date", CurrentDate);
            postInfMap.put("time", CurrentTime);

            ArticleRef = CategoryRef.child(messagekey);
            ArticleRef.updateChildren(postInfMap);
        }
    }



    private void DisplayPosts(DataSnapshot dataSnapshot) {

        Iterator iterator = dataSnapshot.getChildren().iterator();

        while (iterator.hasNext()){
            String content = (String) ((DataSnapshot)iterator.next()).getValue();
            String date = (String) ((DataSnapshot)iterator.next()).getValue();
            String time  = (String) ((DataSnapshot)iterator.next()).getValue();
            String title = (String) ((DataSnapshot)iterator.next()).getValue();

            DisplayArticles.append(
           "              " +title +"    \n " +
                   "     published by  " + CurrentUserName + "\n" +
                    "writen on " +date + " at " + time + "\n\n"
                    + content +"\n \n \n\r" );
        }

    }



    private void getUserInfo() {
        UserRef.child(CurrentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    CurrentUserName = dataSnapshot.getValue().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
                Intent intent = loginActivity.newIntent(getActivity());
                startActivity(intent);
                break;

            default:
                return super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }


}
