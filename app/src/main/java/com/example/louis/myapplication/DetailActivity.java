package com.example.louis.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DetailActivity extends MenuDrawer {
    private static final String TAG = "DetailActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Task mSelectedTask;
    private TextView mTaskTitle;
    private TextView mDescription;
    private TextView mDateStarted;
    private TextView mTimeStarted;
    private TextView mGoal;
    private TextView mTaskTally;
    private TextView mMinuteNumber;
    private TextView mCheckedBoxes;
    private ImageView mLogo;
    private ImageView mImagePhoto;
    private ImageView mTaskImage;

    public int getLayoutId() {
        int id = R.layout.activity_detail;
        return id;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mSelectedTask = Task.selectedTask;

        ImageView imageView = (ImageView) findViewById(R.id.image_photo);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(roundedBitmapDrawable);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    // user is logged in
                } else {
                    finish();
                }
            }
        };

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

    private void configureLayout() {
        mTaskTitle = (TextView) findViewById(R.id.task_title);
        mDescription = (TextView) findViewById(R.id.description);
        mDateStarted = (TextView) findViewById(R.id.date_started);
        mTimeStarted = (TextView) findViewById(R.id.time_started);
        mGoal = (TextView) findViewById(R.id.goal);
        mTaskTally = (TextView) findViewById(R.id.task_tally);
        mMinuteNumber = (TextView) findViewById(R.id.minute_number);
        mCheckedBoxes = (TextView) findViewById(R.id.checked_boxes);
        mLogo = (ImageView) findViewById(R.id.logo);
        mImagePhoto = (ImageView) findViewById(R.id.image_photo);
        mTaskImage = (ImageView) findViewById(R.id.task_image);
    }
}
