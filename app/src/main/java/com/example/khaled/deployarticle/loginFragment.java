package com.example.khaled.deployarticle;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class loginFragment extends Fragment {

    private EditText emailEditText , passwordEditText ;
    private Button loginButton;
    private TextView registerTextView;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private ProgressDialog mprogressDialog;
    private String currentUserId ;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        userRef  = FirebaseDatabase.getInstance().getReference().child("Users");
        mprogressDialog = new ProgressDialog(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login , container , false);
        emailEditText = (EditText) v.findViewById(R.id.login_email);
        passwordEditText = (EditText) v.findViewById(R.id.login_password);
        loginButton = (Button) v.findViewById(R.id.login_button);
        registerTextView = (TextView) v.findViewById(R.id.need_new_account);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllowUserToLogin();
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = registerActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
        return v;
    }

    private void AllowUserToLogin(){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(), "please enter your email ...", Toast.LENGTH_SHORT).show();

        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(getActivity(), "Please Enter your password ...", Toast.LENGTH_SHORT).show();
        }
        else {

            mprogressDialog.setTitle("Login");
            mprogressDialog.setMessage("please until confirm your login");
            mprogressDialog.setCanceledOnTouchOutside(true);
            mprogressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){

                        Toast.makeText(getActivity(), "logedd in successfully", Toast.LENGTH_SHORT).show();
                        mprogressDialog.dismiss();

                        Intent intent = ArticlesCategoriesActivity.newIntent(getActivity());
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getActivity(), "cannot logg in " + task.getException(), Toast.LENGTH_SHORT).show();
                        mprogressDialog.dismiss();
                    }
                }
            });

        }
        }
}
