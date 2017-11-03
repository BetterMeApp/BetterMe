package Model;


import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by louis on 10/31/17.
 */

public class Task {

    public static FirebaseAuth mAuth;

    public static FirebaseDatabase mDatabase;
    public static DatabaseReference mDatabaseRef;

    public static ListView mTasksCompleted;
    public static ArrayList<Task> mTaskArrayList;
    public static TaskListAdapter mTaskListAdapter;

    public String title;
    public String description;
    public String taskImgURL;
    public Long startTime;
    public Date startDate;
    public Integer goalNumber;
    public Integer completedNumber;
    public Boolean completed;
    public Integer daysCompleted;

    public Task(String title, String description, String imgURL, Long time, Date date, Integer goal, Integer done, Boolean isCompleted, Integer daysCompleted){
        this.title = title;
        this.description = description;
        this.taskImgURL = imgURL;
        this.startTime = time;
        this.startDate = date;
        this.goalNumber = goal;
        this.completedNumber = done;
        this.completed = isCompleted;
        this.daysCompleted = daysCompleted;

    }

    public static void dataListener() {
//        mDatabase = FirebaseDatabase.getInstance();
//        mDatabaseRef = mDatabase.getReference("users").child(mAuth.getCurrentUser().getUid());
//        mTaskArrayList = new ArrayList<>();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            public static final String TAG = "Data Listener";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: " + data.getValue().toString());

                    if (data.child("completed").getValue().toString().equals("false")) {
                        continue;
                    }

                    try {
                        Log.d(TAG, "onDataChange: " + data.child("title"));

                        String title = data.child("title").getValue().toString();
                        String description = data.child("description").getValue().toString();
                        String taskImgURL = data.child("imgURL").getValue().toString();
                        Long startTime = Long.valueOf(data.child("time").getValue().toString());
                        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(data.child("date").getValue().toString());
                        Integer goalNumber = Integer.valueOf(data.child("goal").getValue().toString());
                        Integer completedNumber = Integer.valueOf(data.child("done").getValue().toString());
                        Boolean completed = (Boolean) data.child("isCompleted").getValue();
                        Integer daysCompleted = Integer.valueOf(data.child("dayscompleted").getValue().toString());

                        Task newCompletedTask = new Task(title,
                                description,
                                taskImgURL,
                                startTime,
                                startDate,
                                goalNumber,
                                completedNumber,
                                completed,
                                daysCompleted);

                        mTaskArrayList.add(newCompletedTask);
                        Log.d(TAG, "onDataChange: Arraylist being built? " + mTaskArrayList.toString());
                    } catch (Exception e) {
                        Log.d(TAG, "onDataChange: ArrayList failed" + e.getMessage());
                        e.printStackTrace();
                    }
                }
                mTaskListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Error - " + databaseError.getMessage());
            }
        });
    }
}
