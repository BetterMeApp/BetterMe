package com.example.louis.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Model.Task;
import Model.TaskListAdapter;

public class ProfileActivity extends MenuDrawer {
    private static final String TAG = "ProfileActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;

    private ListView mTasksCompleted;
    private ArrayList<Task> mTaskArrayList;
    private TaskListAdapter mTaskListAdapter;

    public int getLayoutId() {
        return R.layout.activity_profile;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is logged in
                } else {
                    finish();
                }
            }
        };

        checkTaskCompletion();
        setViews();
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

    private void setViews() {
        mTasksCompleted = findViewById(R.id.profile_tasks_completed);

        mTaskListAdapter = new TaskListAdapter(this, mTaskArrayList);
        mTasksCompleted.setAdapter(mTaskListAdapter);
    }

    private void checkTaskCompletion() {
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference("users").child(mAuth.getCurrentUser().getUid());
        mTaskArrayList = new ArrayList<>();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: " + data.getValue().toString());

                    if (data.child("completed").getValue().toString().equals("false")) {
                        continue;
                    }

                    try {
                        Log.d(TAG, "onDataChange: " + data.child("title"));

                        String title = data.child("title").getValue().toString();
                        String description = data.child("description").getValue().toString();
                        String taskImgURL = data.child("imgURL").getValue().toString();
                        Long startTime = Long.valueOf(data.child("time").getValue().toString());
                        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(data.child("date").getValue().toString());
                        Integer goalNumber = Integer.valueOf(data.child("goal").getValue().toString());
                        Integer completedNumber = Integer.valueOf(data.child("done").getValue().toString());
                        Boolean completed = (Boolean) data.child("completed").getValue();
                        Integer daysCompleted = Integer.valueOf(data.child("dayscompleted").getValue().toString());

                        Task newCompletedTask = new Task(title,
                                description,
                                taskImgURL,
                                startTime,
                                startDate,
                                goalNumber,
                                completedNumber,
                                completed,
                                daysCompleted);

                        mTaskArrayList.add(newCompletedTask);
                        Log.d(TAG, "onDataChange: Arraylist being built? " + mTaskArrayList.toString());
                    } catch (Exception e) {
                        Log.d(TAG, "onDataChange: ArrayList failed" + e.getMessage());
                        e.printStackTrace();
                    }
                }
                mTaskListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Error - " + databaseError.getMessage());
            }
        });
    }
}