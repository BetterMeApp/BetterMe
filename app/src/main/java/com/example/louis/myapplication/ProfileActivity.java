package com.example.louis.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

import Model.DownloadImageTask;

public class ProfileActivity extends MenuDrawer {
    private static final String TAG = "ProfileActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mStorageRef;

    private TextView mUsername;
    private ListView mTasksCompleted;
    //TODO: add adapter to show completed tasks

    public int getLayoutId() {
        int id = R.layout.activity_profile;
        return id;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        mStorageRef = FirebaseStorage.getInstance().getReference();

        configureLayout();
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
        mUsername = findViewById(R.id.profile_username);
        mTasksCompleted = findViewById(R.id.profile_tasks_completed);

        FirebaseUser user = mAuth.getCurrentUser();
        mUsername.setText(user.getDisplayName());
    }
}
