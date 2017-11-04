package com.example.louis.myapplication;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import Model.DownloadImageTask;
import Model.Task;

public class DetailActivity extends MenuDrawer {
    private static final String TAG = "DetailActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private TextView mTaskTitle;
    private TextView mDateStarted;
    private TextView mGoal;
    private TextView mTaskTally;
    private TextView mTallyLabel;
    private TextView mAddSign;
    private ImageView mImagePhoto;
    private Integer mNumberPicked;
    private Button mIncrement;
    private Button mDecrement;
    private Button mAddToGoal;
    private RelativeLayout mInfoArea;
    private RelativeLayout mNumberPickerLayout;

    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureLayout();
        configureFirebase();
        configureNumberPicker();
        setClickListeners();
    }

    private void configureLayout() {
        mTaskTitle = findViewById(R.id.task_title);
        mDateStarted = findViewById(R.id.date_started);
        mGoal = findViewById(R.id.goal);
        mTaskTally = findViewById(R.id.task_tally);
        mIncrement = findViewById(R.id.button_increment);
        mDecrement = findViewById(R.id.button_decrement);
        mAddToGoal = findViewById(R.id.button_add_towards_goal);
        mTallyLabel = findViewById(R.id.textView_daily_tally_label);
        mImagePhoto = findViewById(R.id.imageView_task_img);
        mInfoArea = findViewById(R.id.relativeLayout_info_area);
        mNumberPickerLayout = findViewById(R.id.relativeLayout_number_picker);
        mAddSign = findViewById(R.id.textView_add_sign);

    }

    private void configureFirebase() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
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
                TextView taskTitle = (TextView) findViewById(R.id.task_title);
                taskTitle.setText(title);
                String imgUrl = dataSnapshot.child("imgURL").getValue(String.class);
                new DownloadImageTask(imgUrl, mImagePhoto).execute();
//                TextView taskDescription = (TextView) findViewById(R.id.description);
//                taskDescription.setText(description);
//
//                TextView taskTime = (TextView) findViewById(R.id.time_started);
//                taskTime.setText(time);
                TextView taskDate = findViewById(R.id.date_started);
                taskDate.setText(buildDateNoTime(date));

                TextView taskGoal = findViewById(R.id.goal);
                taskGoal.setText(goalString);

                TextView taskTally = findViewById(R.id.task_tally);
                taskTally.setText(tallyString);

//                //     String task = dataSnapshot.getValue(String.class);
//                String selectedTask = "Push-ups";
//                Log.d(TAG, "onDataChange: Task changed");
//                DataSnapshot realTimeTaskInfo = dataSnapshot.child(selectedTask);
//                Iterable<DataSnapshot> taskChildren = realTimeTaskInfo.getChildren();
//
//                String date = realTimeTaskInfo.child("date").getValue(String.class);
//                String title = realTimeTaskInfo.child("title").getValue(String.class);
//                Integer goal = Integer.valueOf(realTimeTaskInfo.child("goal").getValue().toString());
//                mTaskTitle.setText(title);
//                mDateStarted.setText(date);
//                mGoal.setText(goal.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Error - " + databaseError.getMessage());

            }
        });

    }

    private String buildDateNoTime(String date) {
        int i = 0;
        String dateNoTime = "";
        while (i < 11) {
            dateNoTime += date.charAt(i);
            i++;
        }
        return dateNoTime;
    }

    public void setClickListeners() {
        mIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCompleted();
            }
        });

        mDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtractToCompleted();
            }
        });
        mAddToGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer total = Integer.valueOf(mTaskTally.getText().toString());
                mDatabaseRef.child("done").setValue(total);
                finish();
            }
        });
        mInfoArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNumberPickerLayout.isShown()){
                    mNumberPickerLayout.setVisibility(View.INVISIBLE);
                    mAddSign.setVisibility(View.VISIBLE);
                } else {
                    mNumberPickerLayout.setVisibility(View.VISIBLE);
                    mAddSign.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private void configureNumberPicker(){
        final TextView numberPickedTextView = findViewById(R.id.textView_number_picked);
        NumberPicker numberPicker = findViewById(R.id.number_picker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(25);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal){
            //Display the newly selected number from picker
            String valString = Integer.toString(newVal);
            numberPickedTextView.setText(valString);
            mNumberPicked = newVal;
            mIncrement.setVisibility(View.VISIBLE);
            int tally = Integer.valueOf(mTaskTally.getText().toString());
            int goalChecker1 = Integer.valueOf(mGoal.getText().toString());
            if (newVal <= tally) {
                mDecrement.setVisibility(View.VISIBLE);
            } else {
                mDecrement.setVisibility(View.INVISIBLE);
            }
            if (goalChecker1 <= tally) {
                mTallyLabel.setText("Completed");
                mTallyLabel.setTextColor(Color.parseColor("#4caf50"));
                mAddToGoal.setVisibility(View.VISIBLE);
            } else {
                mTallyLabel.setText("Done");
                mTallyLabel.setTextColor(Color.parseColor("#001970"));
                mAddToGoal.setVisibility(View.INVISIBLE);
            }
        }
    });
}

    private void addToCompleted() {
        int newTotal;
        if (!(mNumberPicked < 0)) {
            String currentTally = mTaskTally.getText().toString();
            if (!currentTally.equals("")) {
                int tallyInt = Integer.valueOf(currentTally);
                newTotal = mNumberPicked + tallyInt;
                mTaskTally.setText(String.valueOf(newTotal));
                tallyInt = Integer.valueOf(mTaskTally.getText().toString());
                int goalChecker1 = Integer.valueOf(mGoal.getText().toString());
                if (mNumberPicked <= tallyInt) {
                    mDecrement.setVisibility(View.VISIBLE);
                } else {
                    mDecrement.setVisibility(View.INVISIBLE);
                }
                if (goalChecker1 <= tallyInt) {
                    mTallyLabel.setText("Completed");
                    mTallyLabel.setTextColor(Color.parseColor("#4caf50"));
                    mAddToGoal.setVisibility(View.VISIBLE);;
                }
            } else {
                int tallyInt = 0;
                mTaskTally.setText(mNumberPicked.toString());
                tallyInt = Integer.valueOf(mTaskTally.getText().toString());
                int goalChecker2 = Integer.valueOf(mGoal.getText().toString());
                if (goalChecker2 <= tallyInt) {
                    mTallyLabel.setText("Completed");
                    mTallyLabel.setTextColor(Color.parseColor("#4caf50"));
                    mAddToGoal.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void subtractToCompleted(){
        int newTotal;
        if (mNumberPicked != 0){
            int tally = Integer.valueOf(mTaskTally.getText().toString());
            if ((tally - mNumberPicked) >= 0) {
                newTotal = tally - mNumberPicked;
                mTaskTally.setText(String.valueOf(newTotal));
                tally = Integer.valueOf(mTaskTally.getText().toString());
                int goalChecker2 = Integer.valueOf(mGoal.getText().toString());
                if (mNumberPicked <= tally) {
                    mDecrement.setVisibility(View.VISIBLE);
                } else {
                    mDecrement.setVisibility(View.INVISIBLE);
                }
                if (goalChecker2 > tally) {
                    mTallyLabel.setText("Done");
                    mTallyLabel.setTextColor(Color.parseColor("#001970"));
                    mAddToGoal.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void dismissKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
