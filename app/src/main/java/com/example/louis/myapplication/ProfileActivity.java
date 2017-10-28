package com.example.louis.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;

import Model.DownloadImageTask;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ImageView mImgProfile;
    private TextView mUsername;
    private Button mUpdateImg;
    private ListView mTasksCompleted;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //user is logged in
                } else {
                    finish();
                }
            }
        };

        configureLayout();
        attachListeners();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    private void configureLayout(){
        mImgProfile = (ImageView) findViewById(R.id.profile_img);
        mUsername = (TextView) findViewById(R.id.profile_username);
        mUpdateImg = (Button) findViewById(R.id.profile_update_btn);
        mTasksCompleted = (ListView) findViewById(R.id.profile_tasks_completed);

        FirebaseUser user = mAuth.getCurrentUser();

        try {
            mUsername.setText(user.getDisplayName());
            if(user.getPhotoUrl() != null){
                new DownloadImageTask(mImgProfile).execute(user.getPhotoUrl().toString());
            }
        } catch(Exception e){
            Log.d(TAG, "configureLayout: Error: " + e.getMessage());
        }

    }

    private void attachListeners(){

        mUpdateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goto pick profile img activity?
            }
        });

    }
}
