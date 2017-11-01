package com.example.louis.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import Model.DownloadImageTask;
import Model.BetterMeTask;
import butterknife.BindView;

public class PickTaskActivity extends AppCompatActivity {

    private static final String TAG = "PickTaskActivity: ";
    @BindView(R.id.listView_tasks_to_choose) ListView mTasksListView;
    ArrayList<BetterMeTask> mTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_task);

        createTaskArrayList();
        Log.d(TAG, "createTaskArrayList method created: " + mTaskList.toString());
        setTaskListView(mTaskList);

    }

    private void setTaskListView(ArrayList<BetterMeTask> taskArrayList) {
        TaskListAdapter taskAdapter = new TaskListAdapter(taskArrayList);
        mTasksListView.setAdapter(taskAdapter);
    }

    // inner class creating custom list adapter for the feed listview used in setupFeedListView
    class TaskListAdapter extends BaseAdapter {

        private ArrayList<BetterMeTask> adapterTaskList;

        public TaskListAdapter(ArrayList<BetterMeTask> taskArrayList) {
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

            BetterMeTask currentItem = adapterTaskList.get(i);

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


        BetterMeTask pushups = new BetterMeTask("Push-ups", "Stay fit!  Do push-ups throughout the day until you reach your goal.", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, null, null);

        BetterMeTask compliments = new BetterMeTask("Give compliments" ,"Give a compliment to a person or people in your life.", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, null, null);

        BetterMeTask meditate = new BetterMeTask("Meditate", "Set time aside to meditate each day.", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, 5, null);

        BetterMeTask mornings = new BetterMeTask("Wake n Make", "Wake up earlier to enjoy the morning before getting the day started. You will have more time to enjoy your morning coffee and plenty of time to make your bed each day!", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, 5, null);

        BetterMeTask rejection = new BetterMeTask("Get Rejected", "Work on social anxiety and the fear of rejection by getting rejected by a person once a day.  Ask a crush on a date or ask a stranger if you can borrow $100 dollars for a week.", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, null, null);

        BetterMeTask vegandsmoothie = new BetterMeTask("Vegetarian Meal and Smoothie", "Each day prepare one balanced vegetarian meal and make a fruit and vegetable smoothie", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, null, 2);

        Collections.addAll(mTaskList, pushups, compliments, meditate, mornings, rejection, vegandsmoothie);
    }

}


