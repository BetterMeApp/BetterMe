package com.example.louis.myapplication;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Task;

public class DetailActivity extends MenuDrawer {
    private static final String TAG = "DetailActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private Task mCurrentTask;

    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        String userId = mAuth.getCurrentUser().getUid();
        mDatabaseRef = mDatabase.getReference("users").child(userId).child(Task.mCurrentTask);
//        Query queryDatabase = mDatabaseRef.child("users").child("user");
        Log.d(TAG, "onCreate: " + userId);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Boolean isCompleted = dataSnapshot.child("completed").getValue(Boolean.class);
                String time = dataSnapshot.child("time").getValue(String.class);
                String date = dataSnapshot.child("date").getValue(String.class);
                String title = dataSnapshot.child("title").getValue(String.class);
                String description = dataSnapshot.child("description").getValue(String.class);
                Integer goal = Integer.valueOf(dataSnapshot.child("goal").getValue().toString());
                String goalString = goal.toString();
                Integer tally = Integer.valueOf(dataSnapshot.child("done").getValue().toString());
                String tallyString = tally.toString();
                TextView taskTitle = (TextView)findViewById(R.id.task_title);
                taskTitle.setText(title);

                TextView taskDescription = (TextView)findViewById(R.id.description);
                taskDescription.setText(description);

                TextView taskTime = (TextView)findViewById(R.id.time_started);
                taskTime.setText(time);

                TextView taskDate = (TextView)findViewById(R.id.date_started);
                taskDate.setText(date);

                TextView taskGoal = (TextView)findViewById(R.id.goal);
                taskGoal.setText(goalString);

                TextView taskTally = (TextView)findViewById(R.id.task_tally);
                taskTally.setText(tallyString);
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
}
