package com.example.louis.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Model.Task;
import Model.TaskListAdapter;

public class HomeTaskActivity extends MenuDrawer {
    private static final String TAG = "HomeTaskActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ImageView mLogo;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private ArrayList<Model.Task> mTaskArrayList;
    private ListView mTaskListView;
    private TaskListAdapter mAdapter;

    public int getLayoutId() {
        int id = R.layout.activity_home_task;
        return id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Context ctx = this;

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //user is signed in
                } else {
                    //user not signed in
                    Intent loginIntent = new Intent(ctx, LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        };
        mLogo = (ImageView) findViewById(R.id.logo);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        mLogo.setImageDrawable(roundedBitmapDrawable);


        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference("users").child(mAuth.getCurrentUser().getUid());

        mTaskArrayList = new ArrayList<>();
        addDatabaseListener();
        configureListView();
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

    private void addDatabaseListener(){
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot task : dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: completedNumber check->: " + task.getValue().toString());
                    //=====check to see if task is already complete=================
                if(task.child("completed").getValue().toString().equals("true")){
                    continue;
                }
//                    //=========load arraylist with Tasks======================
//
//                    //identify each value (this could be refactored, but clarifies what value each is being assigned to)
                try {
                    String title = task.child("title").getValue().toString();
                    String description = task.child("description").getValue().toString();
                    String taskImgURL = task.child("imgURL").getValue().toString();
                    Long startTime = Long.valueOf(task.child("time").getValue().toString());
                    Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(task.child("date").getValue().toString());
                    Integer goalNumber = Integer.valueOf(task.child("goal").getValue().toString());
                    Integer completedNumber = Integer.valueOf(task.child("done").getValue().toString());
                    Boolean completed = (Boolean) task.child("completed").getValue();
                    Integer daysCompleted = Integer.valueOf(task.child("dayscompleted").getValue().toString());

                    //create new task
                    Task newTask = new Model.Task(title,
                            description,
                            taskImgURL,
                            startTime,
                            startDate,
                            goalNumber,
                            completedNumber,
                            completed,
                            daysCompleted);

                    //add task to arraylist
                    mTaskArrayList.add(newTask);
                } catch(Exception e){
                    Log.d(TAG, "onDataChange: Date format parsing failed" + e.getMessage());
                    e.printStackTrace();
                }

                mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void configureListView(){
        final Context context = this;
        mTaskListView = findViewById(R.id.current_task_listView);
        mAdapter = new TaskListAdapter(this, mTaskArrayList){
            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
//                return super.getView(i, view, viewGroup);
                //use recycler view?
                view = LayoutInflater.from(context).inflate(R.layout.home_task_list_item, null);
                //find views to adjust
                final TextView title = view.findViewById(R.id.task_title);
                TextView percent = view.findViewById(R.id.percent_one);
                TextView count = view.findViewById(R.id.count_one);

                //fill-in views
                title.setText(getItem(i).title);

                //percent should be completedNumber/goalNumber
//                Log.d(TAG, "getView: percentage calc->: " + getItem(i).completedNumber / getItem(i).goalNumber);
                Double comp = Double.valueOf(getItem(i).completedNumber.toString());
                Double goal = Double.valueOf(getItem(i).goalNumber.toString());
                Double percentage = 100 * (comp / goal);
                percent.setText(String.valueOf(percentage));

                //days left should be 30 - daysCompleted
                //daysCompleted should (somewhere) be incremented each day (or by TodayDate - StartDate)
                //if daysCompleted is a utilized property for each task, the following code could be refactored
                Date today = new Date();
                Date startDay = getItem(i).startDate;
                startDay.setTime(getItem(i).startTime);
                Long diff = today.getTime() - startDay.getTime();
                Long days = diff / (24 * 60 * 60 * 1000);
                Long daysLeft = 30 - days;
                count.setText(String.valueOf(daysLeft));

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Task.mCurrentTask = title.getText().toString();
                        Intent detailIntent = new Intent(context, DetailActivity.class);
                        startActivity(detailIntent);
                    }
                });
                return view;
            }
        };
        mTaskListView.setAdapter(mAdapter);
    }

}
