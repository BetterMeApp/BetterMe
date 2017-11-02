package com.example.louis.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Model.DownloadImageTask;
import Model.Task;
import butterknife.ButterKnife;

// todo- strectch todo need to let the user go back to the tasklist layout from the selectedtask layout

// todo add click listeners for any click anywhere to dismiss the keyboard

public class PickTaskActivity extends MenuDrawer {

    private static final String TAG = "PickTaskActivity: ";

    private ListView mTasksListView;
    private ArrayList<Task> mTaskList;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private RelativeLayout mSelectedTaskLayout;
    private RelativeLayout mTaskListLayout;
    private Button mAddTaskButton;
    private EditText mEnterTotalEditText;
    private Model.TaskListAdapter taskAdapter;
    private ImageView mTaskImageView;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDbRef;
    private Task mTaskToAdd;
    private Intent mMoveToHome;
    private TextView mTitleOfTask;
    private TextView mDescriptionOfTask;
    private ToggleButton mToggleTotalTypeButton;

    public int getLayoutId() {
        int id = R.layout.activity_pick_task;
        return id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        setFirebase();
        setViews();
        setClickListeners();
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

    public  void setViews(){
        mTaskList = Model.CreateTasksList.createTaskArrayList();

        mTaskListLayout = findViewById(R.id.relativeLayout_task_list);
        mSelectedTaskLayout = findViewById(R.id.relativeLayout_selected_task);
        mTasksListView = findViewById(R.id.listView_tasks_to_choose);
        mAddTaskButton = findViewById(R.id.button_add_task);
        mEnterTotalEditText = findViewById(R.id.editText_enter_task_number);
        mTitleOfTask = findViewById(R.id.textView_task_title);
        mDescriptionOfTask = findViewById(R.id.textView_task_description);
        mTaskImageView = findViewById(R.id.imageView_task_img);
        //mToggleTotalTypeButton = findViewById(R.id.toggleButton_totals);

        taskAdapter = new Model.TaskListAdapter(this, mTaskList);
        mTasksListView.setAdapter(taskAdapter);
    }

    public void setClickListeners() {
        mMoveToHome = new Intent(this, HomeTaskActivity.class);

        setTaskClickListener();

        mAddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEnterTotalEditText.getText().toString().length() > 0) {
                    addTask();
                    startActivity(mMoveToHome);
                }
            }
        });

//        mToggleTotalTypeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // toggleTotalType();
//            }
//        });
    }

    public void setFirebase(){
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

        mDatabase = FirebaseDatabase.getInstance();
    }

    private void addTask(){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String time = String.valueOf(new Date().getTime());
        Integer goalNumber = Integer.parseInt(mEnterTotalEditText.getText().toString());

        String mUserId = mAuth.getCurrentUser().getUid();
        mDbRef = mDatabase.getReference("users").child(mUserId).child(mTaskToAdd.title);
        mDbRef.child("title").setValue(mTaskToAdd.title);
        mDbRef.child("description").setValue(mTaskToAdd.description);
        mDbRef.child("imgURL").setValue(mTaskToAdd.taskImgURL);
        mDbRef.child("time").setValue(time);
        mDbRef.child("date").setValue(date);
        mDbRef.child("goal").setValue(goalNumber);
        mDbRef.child("done").setValue(mTaskToAdd.completedNumber);
        mDbRef.child("completed").setValue(mTaskToAdd.completed);
        mDbRef.child("dayscompleted").setValue(mTaskToAdd.dayscompleted);

    }

    private void setTaskClickListener(){
        mTasksListView.setClickable(true);
        mTasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                mTaskToAdd = (Task) mTasksListView.getItemAtPosition(position);
                mTaskListLayout.setVisibility(View.GONE);
                mSelectedTaskLayout.setVisibility(View.VISIBLE);
                mTitleOfTask.setText(mTaskToAdd.title);
                mDescriptionOfTask.setText(mTaskToAdd.description);
                new DownloadImageTask(mTaskToAdd.taskImgURL, mTaskImageView).execute();
            }
        });
    }

//        private void toggleTotalType(){
//        if (mToggleTotalTypeButton.isChecked()){
//            mEnterTotalEditText.setHint("push-ups per month");
//            //todo logic for switching input type if pushups per MONTH
//        } else {
//            mEnterTotalEditText.setHint("push-ups per day");
//            //todo logic for switching input type if pushups per DAY
//
//        }
//
//    }

}


