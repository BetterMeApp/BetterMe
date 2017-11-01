package com.example.louis.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeTaskActivity extends AppCompatActivity {
    private static final String TAG = "HomeTaskActivity";
    private TextView mTaskTitle, mTaskTwo, mTaskThree, mTaskFour, mTaskFive, mDailyOne, mDailyTwo, mDailyThree, mDailyFour, mDailyFive, mDaysLeftOne, mDaysLeftTwo, mDaysLeftThree, mDaysLeftFour, mDaysLeftFive;
    private TextView mPercentOne;
    private TextView mPercentTwo;
    private TextView mPercentThree;
    private TextView mPercentFour;
    private TextView mPercentFive;
    private TextView mCountOne;
    private TextView mCountTwo;
    private TextView mCountThree;
    private TextView mCountFour;
    private TextView mCountFive;
    private Handler mHandler;
    private Runnable mRunnable;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DEBUG", "debugging");
        setContentView(R.layout.activity_home_task);
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference("tasks");

        mDatabaseRef.child("tests").setValue("testing");

//        final DatabaseReference taskRef = mDatabase.getReference("tasks");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: Task changed");


                List<String> allTasks = new ArrayList<>();
                for (DataSnapshot snapshsot : dataSnapshot.getChildren()) {
                    String task = snapshsot.getValue(String.class);
                    allTasks.add(task);
                    Log.d(TAG, "onDataChange: " + task);
                }

                TextView taskTitle = (TextView)findViewById(R.id.task_title);
                taskTitle.setText(allTasks.get(0));



//                DataSnapshot pushups = dataSnapshot.child("pushups");
//                String description = pushups.child("description").getValue(String.class);

//                TextView taskTitle = (TextView)findViewById(R.?id>    .task_title);
//                String task = dataSnapshot.getValue(String.class);
//                taskTitle.setText(task);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Error - " + databaseError.getMessage());
            }
        });

        mTaskTitle = (TextView) findViewById(R.id.task_title);
        mTaskTwo = (TextView) findViewById(R.id.task_two);
        mTaskThree = (TextView) findViewById(R.id.task_three);
        mTaskFour = (TextView) findViewById(R.id.task_four);
        mTaskFive = (TextView) findViewById(R.id.task_five);
        mDailyOne = (TextView) findViewById(R.id.daily_one);
        mDailyTwo = (TextView) findViewById(R.id.daily_two);
        mDailyThree = (TextView) findViewById(R.id.daily_three);
        mDailyFour = (TextView) findViewById(R.id.daily_four);
        mDailyFive = (TextView) findViewById(R.id.daily_five);
        mDaysLeftOne = (TextView) findViewById(R.id.days_left_one);
        mDaysLeftTwo = (TextView) findViewById(R.id.days_left_two);
        mDaysLeftThree = (TextView) findViewById(R.id.days_left_three);
        mDaysLeftFour = (TextView) findViewById(R.id.days_left_four);
        mDaysLeftFive = (TextView) findViewById(R.id.days_left_five);
        mPercentOne = (TextView) findViewById(R.id.percent_one);
        mPercentTwo = (TextView) findViewById(R.id.percent_two);
        mPercentThree = (TextView) findViewById(R.id.percent_three);
        mPercentFour = (TextView) findViewById(R.id.percent_four);
        mPercentFive = (TextView) findViewById(R.id.percent_five);
        mCountOne = (TextView) findViewById(R.id.count_one);
        mCountTwo = (TextView) findViewById(R.id.count_two);
        mCountThree = (TextView) findViewById(R.id.count_three);
        mCountFour = (TextView) findViewById(R.id.count_four);
        mCountFive = (TextView) findViewById(R.id.count_five);

        countDownStart();

    }

    public void countDownStart() {

                try {
                    Calendar createdTask = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
                    Date futureDate = dateFormat.parse("2017-11-17");
                    Date currentDate = new Date();
                    createdTask.add(Calendar.DAY_OF_MONTH, 30);
//                    String futureDate = dateFormat.format(createdTask.getTime());
                    Long diff = futureDate.getTime() - currentDate.getTime();
                    Long days = diff / (24 * 60 * 60 * 1000);
                    diff -= days * (24 * 60 * 60 * 1000);
                    mCountOne.setText(String.valueOf(days));
                    Log.d(TAG, "countDownStart: Days" + days);
                    int totalDays = 30;
                    float Percentage;
                    Percentage = (float) ((days * 100)/totalDays);
                    mPercentOne.setText(String.valueOf(Percentage));
                    Log.d(TAG, "countDownStart: Percentage" + Percentage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

    }
//    public void viewDetailedTask(final Task taskDetail) {
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                try {
//                    Task task = new Task(taskDetail.toString());
//                } catch (Exception e) {
//
//                    return null;
//                }
//            }execute();
//        }
//
//    }






//    private void writeUserData(mTaskOne, dailyPercentage, daysLeft) {
//        firebase.database().ref('users/' + userId).set({
//
//        })

}
