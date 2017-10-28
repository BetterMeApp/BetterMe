package com.example.louis.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailActivity extends MenuActivity {

    @BindView(R.id.progress_amount) TextView mProgressAmount;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.start_button) Button mStartButton;

    private int progressStatus = 0;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


    }

    @OnClick(R.id.start_button)
    public void startButtonPressed() {
        progressStatus = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < 100) {
                    progressStatus += 1;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(progressStatus);
                            mProgressAmount.setText("Progress" + progressStatus + "/" + mProgressBar.getMax());
                        }
                    });
                }
            }
        }).start();
    }
}
