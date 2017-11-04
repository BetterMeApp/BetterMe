package com.example.louis.myapplication;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
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

public class DetailActivity extends MenuDrawer {
    private static final String TAG = "DetailActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private TextView mTaskTitle;
    private TextView mDescription;
    private TextView mDateStarted;
    private TextView mTimeStarted;
    private TextView mGoal;
    private TextView mTaskTally;
    private TextView mTallyLabel;
    private ImageView mLogo;
    private ImageView mImagePhoto;
    private ImageView mLogoBackground;
    private RelativeLayout mLayout;
    private Integer mNumberPicked;
    private Button mIncrement;
    private Button mDecrement;
    private Button mAddToGoal;

    public int getLayoutId() {
        int id = R.layout.activity_detail;
        return id;
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
        mLogo = findViewById(R.id.logo);
        mLogoBackground = findViewById(R.id.logo_background);
        mLayout = findViewById(R.id.relativeLayout_info_area);
        mIncrement = findViewById(R.id.button_increment);
        mDecrement = findViewById(R.id.button_decrement);
        mAddToGoal = findViewById(R.id.button_add_towards_goal);
        mTallyLabel = findViewById(R.id.textView_daily_tally_label);
    }

    private void configureFirebase() {
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
        String userId = "xZEHwfTM4jbNOJRBikKvQhzpkbh2";
        mDatabaseRef = mDatabase.getReference("users").child(userId);
        //        Query queryDatabase = mDatabaseRef.child("users").child("user");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //     String task = dataSnapshot.getValue(String.class);
                String selectedTask = "Push-ups";
                Log.d(TAG, "onDataChange: Task changed");
                DataSnapshot realTimeTaskInfo = dataSnapshot.child(selectedTask);
                Iterable<DataSnapshot> taskChildren = realTimeTaskInfo.getChildren();

                String date = realTimeTaskInfo.child("date").getValue(String.class);
                String title = realTimeTaskInfo.child("title").getValue(String.class);
                String imgUrl = realTimeTaskInfo.child("imgURL").getValue(String.class);
                Integer goal = Integer.valueOf(realTimeTaskInfo.child("goal").getValue().toString());
                //new DownloadImageTask(imgUrl, mImagePhoto).execute();
                mTaskTitle.setText(title);
                mDateStarted.setText(date);
                mGoal.setText(goal.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Error - " + databaseError.getMessage());

            }
        });

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
            numberPickedTextView.setText("Selected Number : " + newVal);
            mNumberPicked = newVal;
            mIncrement.setVisibility(View.VISIBLE);
            mDecrement.setVisibility(View.VISIBLE);
        }
    });
}

    private void addToCompleted() {
        int newTotal;
        if (mNumberPicked != 0) {
            String currentTally = mTaskTally.getText().toString();
            if (!currentTally.equals("")) {
                int tallyInt = Integer.valueOf(currentTally);
                newTotal = mNumberPicked + tallyInt;
                mTaskTally.setText(String.valueOf(newTotal));
                tallyInt = Integer.valueOf(mTaskTally.getText().toString());
                int goalChecker1 = Integer.valueOf(mGoal.getText().toString());
                if (goalChecker1 <= tallyInt) {
                    mTallyLabel.setTextColor(Color.parseColor("#4caf50"));
                }
            } else {
                int tallyInt = 0;
                mTaskTally.setText(mNumberPicked.toString());
                tallyInt = Integer.valueOf(mTaskTally.getText().toString());
                int goalChecker2 = Integer.valueOf(mGoal.getText().toString());
                if (goalChecker2 <= tallyInt) {
                    mTallyLabel.setTextColor(Color.parseColor("#4caf50"));
                }
                mAddToGoal.setVisibility(View.VISIBLE);
            }
        }
    }

    private void subtractToCompleted(){
        int newTotal;
        if (mNumberPicked != 0){
            int tally = Integer.valueOf(mTaskTally.getText().toString());
            if ((tally - mNumberPicked) > 0) {
                newTotal = tally - mNumberPicked;
                mTaskTally.setText(String.valueOf(newTotal));
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
