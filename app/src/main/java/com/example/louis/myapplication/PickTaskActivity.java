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
import Model.Task;
import butterknife.BindView;

public class PickTaskActivity extends AppCompatActivity {

    private static final String TAG = "PickTaskActivity: ";

    private ListView mTasksListView;

    private ArrayList<Task> mTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_task);

        mTaskList = new ArrayList<>();
        mTasksListView = findViewById(R.id.listView_tasks_to_choose);
        createTaskArrayList();
        Log.d(TAG, "createTaskArrayList method created: " + mTaskList.toString());
        setTaskListView();

    }

    private void setTaskListView() {
        TaskListAdapter taskAdapter;
        taskAdapter = new TaskListAdapter(mTaskList);
        mTasksListView.setAdapter(taskAdapter);
    }

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

            ImageView taskImgView = view.findViewById(R.id.imageView_task_img);
            TextView taskTitleView = view.findViewById(R.id.textView_task_title);
            TextView taskDescriptionView = view.findViewById(R.id.textView_task_description);

            //new DownloadImageTask(taskImgView).execute();
            taskTitleView.setText(currentItem.title);
            taskDescriptionView.setText(currentItem.description);

            return view;
        }
    }

    private void createTaskArrayList() {
        Task pushups = new Task("Push-ups", "Stay fit!  Do push-ups throughout the day until you reach your goal.", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, null, null);

        Task compliments = new Task("Give compliments" ,"Give a compliment to a person or people in your life.", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, null, null);

        Task meditate = new Task("Meditate", "Set time aside to meditate each day.", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, 5, null);

        Task mornings = new Task("Wake n Make", "Wake up earlier to enjoy the morning before getting the day started. You will have more time to enjoy your morning coffee and plenty of time to make your bed each day!", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, 5, null);

        Task rejection = new Task("Get Rejected", "Work on social anxiety and the fear of rejection by getting rejected by a person once a day.  Ask a crush on a date or ask a stranger if you can borrow $100 dollars for a week.", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, null, null);

        Task vegandsmoothie = new Task("Vegetarian Meal and Smoothie", "Each day prepare one balanced vegetarian meal and make a fruit and vegetable smoothie", "http://www.euneighbours.eu/sites/default/files/2017-01/placeholder.png", null, null, 1, 0, null, 2);

        Collections.addAll(mTaskList, pushups, compliments, meditate, mornings, rejection, vegandsmoothie);
    }
}


