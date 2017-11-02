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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class HomeTaskActivity extends MenuDrawer {
    private static final String TAG = "HomeTaskActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ImageView mLogo;
    private TextView mDailyOne;
    private TextView mDaysLeftOne;
    private TextView mPercentOne;
    private TextView mCountOne;
    private TextView mTaskTitle;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;

    public int getLayoutId() {
        int id = R.layout.activity_home_task;
        return id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = (ImageView) findViewById(R.id.logo);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(roundedBitmapDrawable);


        Log.d("DEBUG", "debugging");
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference("tasks");

        mDatabaseRef.child("tests").setValue("testing");
        mDatabaseRef.child("tests2").setValue("testing");
        Log.d(TAG, "activity: HomeTask");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: Task changed");


                List<String> allTasks = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String task = child.getKey();
                    allTasks.add(task);
                    Log.d(TAG, "onDataChange: " + task);
                }

                TextView taskTitle = (TextView)findViewById(R.id.task_title);
                taskTitle.setText(allTasks.get(3));



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



        FirebaseApp.initializeApp(this);

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
//        mAuth.signOut();

        mTaskTitle = (TextView) findViewById(R.id.task_title);
        mDailyOne = (TextView) findViewById(R.id.daily_one);
        mDaysLeftOne = (TextView) findViewById(R.id.days_left_one);
        mCountOne = (TextView) findViewById(R.id.count_one);
        mPercentOne = (TextView)  findViewById(R.id.percent_one);
        mLogo = (ImageView)  findViewById(R.id.logo);

        countDownStart();

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
