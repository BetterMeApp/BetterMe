package Model;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.louis.myapplication.MenuDrawer;
import com.example.louis.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brandonholderman on 10/26/17.
 */

public class ProgressionBar extends AppCompatActivity {

    @BindView(R.id.progress_amount)
    TextView mProgressAmount;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.start_button)
    Button mStartButton;

    private int progressStatus = 0;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progression_bar);
        ButterKnife.bind(this);

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
