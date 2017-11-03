package com.example.louis.myapplication;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.louis.myapplication.R.id.description;

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
    private ImageView mLogoBackground;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;

    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ImageView imageView = (ImageView) findViewById(R.id.logo);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
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

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference("tasks");

        mDatabaseRef.child("tests").setValue("testing");
        mDatabaseRef.child("tests2").setValue("testing");
        Log.d(TAG, "activity: HomeTask");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: Task changed");

                List<String> allDescription = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String task = child.getKey();
                    allDescription.add(task);
                    Log.d(TAG, "onDataChange: " + description);
                }

                TextView taskTitle = (TextView)findViewById(description);
                taskTitle.setText(allDescription.get(0));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Error - " + databaseError.getMessage());

            }
        });

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
        mLogoBackground = (ImageView) findViewById(R.id.logo_background);

    }
}
