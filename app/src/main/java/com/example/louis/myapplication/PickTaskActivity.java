package com.example.louis.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import java.util.HashMap;
import java.util.Map;

import Model.DownloadImageTask;
import Model.Task;
import butterknife.ButterKnife;

// todo- strectch todo need to let the user go back to the tasklist layout from the selectedtask layout

// todo add click listeners for any click anywhere to dismiss the keyboard

public class PickTaskActivity extends MenuDrawer {

    private static final String TAG = "PickTaskActivity: ";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDbRef;
    private Model.TaskListAdapter taskAdapter;
    private Model.TaskListAdapter mTaskListAdapter;
    private RelativeLayout mSelectedTaskLayout;
    private RelativeLayout mTaskListLayout;
    private ImageView mTaskImageView;
    private ListView mTasksListView;
    private TextView mTitleOfTask;
    private TextView mDescriptionOfTask;
    private ArrayList<Task> mTaskList;
    private Intent mMoveToDetail;
    private Button mAddTaskButton;
    private Button mBackButton;
    private EditText mEnterTotalEditText;
    private Task mTaskToAdd;
    private ToggleButton mToggleTotalTypeButton;

    public int getLayoutId() {
        return R.layout.activity_pick_task;
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

    public void setViews() {
        mTaskList = Model.CreateTasksList.createTaskArrayList();

        mTaskListLayout = findViewById(R.id.relativeLayout_task_list);
        mSelectedTaskLayout = findViewById(R.id.relativeLayout_selected_task);
        mTasksListView = findViewById(R.id.listView_tasks_to_choose);
        mAddTaskButton = findViewById(R.id.button_add_task);
        mEnterTotalEditText = findViewById(R.id.editText_enter_task_number);
        mTitleOfTask = findViewById(R.id.textView_task_title);
        mDescriptionOfTask = findViewById(R.id.textView_task_description);
        mBackButton = findViewById(R.id.button_back);
        mTaskImageView = findViewById(R.id.imageView_task_img);
        //mToggleTotalTypeButton = findViewById(R.id.toggleButton_totals);

        taskAdapter = new Model.TaskListAdapter(this, mTaskList);
        mTasksListView.setAdapter(taskAdapter);

        mTaskListAdapter = new Model.TaskListAdapter(this, mTaskList);
        mTasksListView.setAdapter(mTaskListAdapter);
    }

    public void setClickListeners() {
        mMoveToDetail = new Intent(this, DetailActivity.class);

        setTaskClickListener();

        mAddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEnterTotalEditText.getText().toString().length() > 0) {
                    addTask();
                    startActivity(mMoveToDetail);
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskListLayout.setVisibility(View.VISIBLE);
                mSelectedTaskLayout.setVisibility(View.GONE);
            }
        });

        mSelectedTaskLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissKeyboard(mSelectedTaskLayout);
            }
        });

//        mToggleTotalTypeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // toggleTotalType();
//            }
//        });
    }

    public void setFirebase() {
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

        mDatabase = FirebaseDatabase.getInstance();
    }

    private void addTask() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String time = String.valueOf(new Date().getTime());
        Integer goalNumber = Integer.parseInt(mEnterTotalEditText.getText().toString());
        String mUserId = mAuth.getCurrentUser().getUid();
        Map<String, Object> update = new HashMap<String, Object>();
        update.put("title", mTaskToAdd.title);
        update.put("description", mTaskToAdd.description);
        update.put("imgURL", mTaskToAdd.taskImgURL);
        update.put("time", time);
        update.put("date", date);
        update.put("goal", goalNumber);
        update.put("done", mTaskToAdd.completedNumber);
        update.put("completed", mTaskToAdd.completed);
        update.put("dayscompleted", mTaskToAdd.daysCompleted);


        mDbRef = mDatabase.getReference("users").child(mUserId).child(mTaskToAdd.title);
        mDbRef.updateChildren(update);
    }

    private void setTaskClickListener() {
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

    private void dismissKeyboard(View view) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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


