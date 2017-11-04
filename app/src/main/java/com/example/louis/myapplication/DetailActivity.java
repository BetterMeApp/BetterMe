package com.example.louis.myapplication;


import android.content.Context;
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
    private TextView mMinuteNumber;
    private TextView mCheckedBoxes;
    private ImageView mLogo;
    private ImageView mImagePhoto;
    private ImageView mLogoBackground;
    private RelativeLayout mLayout;


    public int getLayoutId() {
        int id = R.layout.activity_detail;
        return id;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureLayout();
        configureFirebase();
        configureNumberPicker();
        dismissKeyboard();
    }

    private void configureLayout() {
        mTaskTitle = findViewById(R.id.task_title);
        mDateStarted = findViewById(R.id.date_started);
        mGoal = findViewById(R.id.goal);
        mTaskTally = findViewById(R.id.task_tally);
        mLogo = findViewById(R.id.logo);
        mLogoBackground = findViewById(R.id.logo_background);
        mLayout = findViewById(R.id.relativeLayout_info_area);
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
                String description = realTimeTaskInfo.child("description").getValue(String.class);
                Integer goal = Integer.valueOf(realTimeTaskInfo.child("goal").getValue().toString());
                Integer tally = Integer.valueOf(realTimeTaskInfo.child("done").getValue().toString());
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

    private void configureNumberPicker(){
        final TextView numberPicked = findViewById(R.id.textView_number_picked);
        NumberPicker numberPicker = findViewById(R.id.number_picker);
        dismissKeyboard();
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(25);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal){
            //Display the newly selected number from picker
            numberPicked.setText("Selected Number : " + newVal);
        }
    });
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
