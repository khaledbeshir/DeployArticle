package com.example.khaled.deployarticle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by khaled on 7/16/2019.
 */

public class registerFragment extends Fragment {

    private EditText emailEditText , passwordEditText ,usernameEditText;
    private Button registerButton;
    private FirebaseAuth mAuth;
    private DatabaseReference rootRefrence;
    private ProgressDialog mProgressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        rootRefrence = FirebaseDatabase.getInstance().getReference();
        mProgressDialog = new ProgressDialog(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_register , container , false);
        emailEditText = (EditText) v.findViewById(R.id.register_email);
        passwordEditText = (EditText) v.findViewById(R.id.register_password);
        usernameEditText = (EditText) v.findViewById(R.id.register_username);
        registerButton = (Button)v.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllowUserToRegister();
            }
        });
        return v;
    }


    private void AllowUserToRegister (){

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        final String userName = usernameEditText.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(), "please enter your email ...", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(getActivity(), "please enter your name ...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(getActivity(), "Please Enter your password ...", Toast.LENGTH_SHORT).show();
        }
        else {
            mProgressDialog.setTitle("regist");
            mProgressDialog.setMessage("please wait until create your account");
            mProgressDialog.setCanceledOnTouchOutside(true);
            mProgressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        String CurrentUserId = mAuth.getCurrentUser().getUid();
                        rootRefrence.child("Users").child(CurrentUserId).setValue(userName);
                        Toast.makeText(getActivity(), "registered sucessfully", Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                        Intent intent = ArticlesCategoriesActivity.newIntent(getActivity());
                        startActivity(intent);
                    }
                    else{

                        String message = task.getException().toString();
                        Toast.makeText(getActivity(), "Error : "+message , Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                    }
                }
            });
        }
        }

}
