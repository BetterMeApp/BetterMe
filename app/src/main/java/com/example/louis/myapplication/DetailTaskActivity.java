package com.example.louis.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;

import Model.BetterMeTask;

public class DetailTaskActivity extends AppCompatActivity {
    private Task mSelectedTask;
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
    private ImageView mTaskImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mSelectedTask = BetterMeTask.selectedTask;

        ImageView imageView = (ImageView) findViewById(R.id.image_photo);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(roundedBitmapDrawable);

    }

    private void configureLayout() {
        mTaskTitle = (TextView) findViewById(R.id.task_title);
        mDescription = (TextView) findViewById(R.id.description);
        mDateStarted = (TextView) findViewById(R.id.date_started);
        mTimeStarted = (TextView) findViewById(R.id.time_started);
        mGoal = (TextView) findViewById(R.id.goal);
        mTaskTally = (TextView) findViewById(R.id.task_tally);
        mMinuteNumber = (TextView) findViewById(R.id.minute_number);
        mCheckedBoxes = (TextView) findViewById(R.id.checked_boxes);
        mLogo = (ImageView) findViewById(R.id.logo);
        mImagePhoto = (ImageView) findViewById(R.id.image_photo);
        mTaskImage = (ImageView) findViewById(R.id.task_image);
//        mTaskTitle.setText(mSelectedTask.task);
//        mDescription.setText(mSelectedTask.description);
//        mDateStarted.setText(mSelectedTask.dateStarted);
//        mTimeStarted.setText(mSelectedTask.timeStarted);
//        mGoal.setText(mSelectedTask.goal);
//        mTaskTally.setText(mSelectedTask.taskTally);
//        mTaskImage.(mSelectedTask.taskImage);

    }
}
