package com.example.louis.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;

import Model.DownloadImageTask;
import Model.Task;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PickTaskActivity extends MenuDrawer {

    private static final String TAG = "PickTaskActivity: ";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @BindView(R.id.listView_tasks_to_choose) ListView mTasksListView;
    ArrayList<Task> mTaskList;

    public int getLayoutId() {
        int id = R.layout.activity_pick_task;
        return id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //user is logged in
                } else {
                    finish();
                }
            }
        };

        createTaskArrayList();
        Log.d(TAG, "createTaskArrayList method created: " + mTaskList.toString());
        setTaskListView(mTaskList);

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

    private void setTaskListView(ArrayList<Task> taskArrayList) {
        TaskListAdapter taskAdapter = new TaskListAdapter(taskArrayList);
        mTasksListView.setAdapter(taskAdapter);
    }

    // inner class creating custom list adapter for the feed listview used in setupFeedListView
    class TaskListAdapter extends BaseAdapter {

        private ArrayList<Task> adapterTaskList;

        public TaskListAdapter(ArrayList<Task> taskArrayList) {
            super();
            this.adapterTaskList = taskArrayList;
        }

        @Override
        public int getCount() {
            return adapterTaskList.size();
        }

        @Override
        public Object getItem(int i) {
            return adapterTaskList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            Task currentItem = adapterTaskList.get(i);

            view = getLayoutInflater().inflate(R.layout.task_custom_listview_item, null);

            ImageView taskImgView = (ImageView) view.findViewById(R.id.imageView_task_img);
            TextView taskTitleView = (TextView) view.findViewById(R.id.textView_task_title);
            TextView taskDescriptionView = (TextView) view.findViewById(R.id.textView_task_description);

            new DownloadImageTask(taskImgView).execute("http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png");
            taskTitleView.setText(currentItem.title);
            taskDescriptionView.setText(currentItem.description);

            return view;
        }
    }

    private void createTaskArrayList() {

        mTaskList = new ArrayList<>();


        Task pushups = new Task("Push-ups", "Stay fit!  Do push-ups throughout the day until you reach your goal.", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, null, null);

        Task compliments = new Task("Give compliments" ,"Give a compliment to a person or people in your life.", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, null, null);

        Task meditate = new Task("Meditate", "Set time aside to meditate each day.", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, 5, null);

        Task mornings = new Task("Wake n Make", "Wake up earlier to enjoy the morning before getting the day started. You will have more time to enjoy your morning coffee and plenty of time to make your bed each day!", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, 5, null);

        Task rejection = new Task("Get Rejected", "Work on social anxiety and the fear of rejection by getting rejected by a person once a day.  Ask a crush on a date or ask a stranger if you can borrow $100 dollars for a week.", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, null, null);

        Task vegandsmoothie = new Task("Vegetarian Meal and Smoothie", "Each day prepare one balanced vegetarian meal and make a fruit and vegetable smoothie", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, null, 2);

        Collections.addAll(mTaskList, pushups, compliments, meditate, mornings, rejection, vegandsmoothie);
    }

}


