package com.example.louis.myapplication;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import Model.Task;

public class ProfileActivity extends MenuDrawer {
    private static final String TAG = "ProfileActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mStorageRef;
    private FirebaseDatabase mRef;

    private TextView mUsername;
    private ListView mTasksCompleted;
    private LinearLayout mTaskListLayout;
    private ArrayList<Task> mTaskList;
    private Model.TaskListAdapter mTaskListAdapter;

    public int getLayoutId() {
        int id = R.layout.activity_profile;
        return id;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskList = Model.CreateTasksList.createTaskArrayList();

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

    private void configureLayout(){
        mUsername = findViewById(R.id.profile_username);
        mTasksCompleted = findViewById(R.id.profile_tasks_completed);

        FirebaseUser user = mAuth.getCurrentUser();
        mUsername.setText(user.getDisplayName());
    }

    private void setViews() {
        mTasksCompleted = findViewById(R.id.profile_tasks_completed);
        mTaskListLayout = findViewById(R.id.profile_task_layout);

        mTaskListAdapter = new Model.TaskListAdapter(this, mTaskList);
        mTasksCompleted.setAdapter(mTaskListAdapter);
    }

    private void checkCompletedTask() {

    }

    private void checkDatabaseChanges() {

    }
}
