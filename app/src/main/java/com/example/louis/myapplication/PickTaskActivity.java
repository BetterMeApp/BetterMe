package com.example.louis.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import java.util.Collections;
import Model.DownloadImageTask;
import Model.Task;
import Model.TaskListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// todo- strectch todo need to let the user go back to the tasklist layout from the selectedtask layout

public class PickTaskActivity extends MenuDrawer {

    private static final String TAG = "PickTaskActivity: ";

    private ListView mTasksListView;
    private ArrayList<Bitmap> mBmps;
    private ArrayList<Task> mTaskList;
    private Bitmap taskBmp;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ListView mTaskListView;
    private RelativeLayout mSelectedTaskLayout;
    private RelativeLayout mTaskListLayout;
    private Button mAddTaskButton;
    private ToggleButton mToggleTotalTypeButton;
    private EditText mEnterTotalEditText;
    private Model.TaskListAdapter mTaskListAdapter;
    private ImageView mTaskImage;

    public int getLayoutId() {
        int id = R.layout.activity_pick_task;
        return id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        mTaskList = Model.CreateTasksList.createTaskArrayList();
        Log.d(TAG, "onCreate: created list " + mTaskList.toString());
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


    }

    public  void setViews(){
        mTaskListLayout = findViewById(R.id.relativeLayout_task_list);
        mSelectedTaskLayout = findViewById(R.id.relativeLayout_selected_task);
        mTasksListView = findViewById(R.id.listView_tasks_to_choose);
        mTaskListLayout.setVisibility(View.VISIBLE);
        mAddTaskButton = findViewById(R.id.button_add_task);
        mToggleTotalTypeButton = findViewById(R.id.toggleButton_totals);
        mEnterTotalEditText = findViewById(R.id.editText_enter_task_number);
        mTaskImage = findViewById(R.id.imageView_task_img);
        mTaskListAdapter = new Model.TaskListAdapter(this, mTaskList);
        mTasksListView.setAdapter(mTaskListAdapter);
    }

    public void setClickListeners() {
        setTaskClickListener();
        addTask();
        mToggleTotalTypeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleTotalType();
                }
            });
    }

    public void toggleTotalType(){
        if (mToggleTotalTypeButton.isChecked()){
            mEnterTotalEditText.setHint("push-ups per month");
            //todo logic for switching input type if pushups per MONTH
        } else {
            mEnterTotalEditText.setHint("push-ups per day");
            //todo logic for switching input type if pushups per DAY

        }

    }

    private void addTask(){

        //todo logic for submitting their pushup activity to firebase under the current user with the goal total in pushups per day
        // grab user
        // put task info under user
        //
    }

    private void setTaskClickListener(){
        mTasksListView.setClickable(true);
        mTasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Object task = (Task) mTasksListView.getItemAtPosition(position);
                Log.d(TAG, "onItemClick: " + task.toString());
                mSelectedTaskLayout.setVisibility(View.VISIBLE);
                mTaskListLayout.setVisibility(View.GONE);
                // TODO: need to fill in the proper task info into selected task layout
            }
        });
    }

    // this is where createtasklist method was
}


