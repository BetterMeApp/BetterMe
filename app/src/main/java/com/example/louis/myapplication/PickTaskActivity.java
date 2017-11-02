package com.example.louis.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import java.util.Collections;
import Model.DownloadImageTask;
import Model.Task;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickTaskActivity extends MenuDrawer {

    private static final String TAG = "PickTaskActivity: ";

    private ListView mTasksListView;
    private ArrayList<Bitmap> mBmps;
    private ArrayList<Task> mTaskList;
    private Bitmap taskBmp;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ListView mTaskListView;
    private RelativeLayout mSelectedTaskLayout;
    private RelativeLayout mTaskListLayout;
    private Button mSetTotalsButton;
    private ToggleButton mToggleTotalTypeButton;
    private EditText mEnterTotalEditText;

    public int getLayoutId() {
        int id = R.layout.activity_pick_task;
        return id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        mTaskList = new ArrayList<>();
        mTaskListLayout = findViewById(R.id.relativeLayout_task_list);
        mSelectedTaskLayout = findViewById(R.id.relativeLayout_selected_task);
        mTasksListView = findViewById(R.id.listView_tasks_to_choose);
        mSetTotalsButton = findViewById(R.id.button_set_totals);
        mToggleTotalTypeButton = findViewById(R.id.toggleButton_totals);
        mEnterTotalEditText = findViewById(R.id.editText_enter_task_number);

        createTaskArrayList();
        setTaskListView(mTaskList);
        setTaskClickListener();
        setClickListeners();

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
    }

    public void setClickListeners() {
        mToggleTotalTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTotalType();
            }
        });


    }
    public void toggleTotalType(){
        if (mToggleTotalTypeButton.isChecked()){
            mEnterTotalEditText.setHint("push-ups per month");
        } else {
            mEnterTotalEditText.setHint("push-ups per day");
        }

        createTaskArrayList();
        setTaskListView(mTaskList);
        setTaskClickListener();
    }

    private void setTaskClickListener(){
        mTasksListView.setClickable(true);
        mTasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Object task = (Task) mTasksListView.getItemAtPosition(position);
                Log.d(TAG, "onItemClick: " + task.toString());
                mSelectedTaskLayout.setVisibility(View.VISIBLE);
                mTaskListLayout.setVisibility(View.GONE);


                // TODO: 10/31/17 add the task to the user in firebase with the starting values for their task
                // TODO: 10/31/17 send them to the DetailActivity



            }
        });

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
            String taskURL = currentItem.taskImgURL;

            view = getLayoutInflater().inflate(R.layout.task_custom_listview_item, null);

            ImageView taskImgView = view.findViewById(R.id.imageView_task_img);
            TextView taskTitleView = view.findViewById(R.id.textView_task_title);
            TextView taskDescriptionView = view.findViewById(R.id.textView_task_description);

            new DownloadImageTask(taskURL, taskImgView).execute();
            taskTitleView.setText(currentItem.title);
            taskDescriptionView.setText(currentItem.description);

            return view;
        }
    }

    private void createTaskArrayList() {

        Task pushups = new Task("Push-ups", "Stay fit!  Do push-ups throughout the day until you reach your goal.", "https://firebasestorage.googleapis.com/v0/b/betterme-cf17.appspot.com/o/default%20task%20images%2Fpushup.jpg?alt=media&token=99aec2ef-07fe-42ca-945d-3a216d12b3ef", null, null, 1, 0);

        Task complements = new Task("Give complements" ,"Give a complement to a person or people in your life.", "https://firebasestorage.googleapis.com/v0/b/betterme-cf17.appspot.com/o/default%20task%20images%2Fcomplement.jpg?alt=media&token=ff3bf693-14ec-4c3a-b0e5-44e284f00a68", null, null, 1, 0);

        Task meditate = new Task("Meditate", "Set time aside to meditate each day.", "https://firebasestorage.googleapis.com/v0/b/betterme-cf17.appspot.com/o/default%20task%20images%2Fmeditation.jpg?alt=media&token=2942258a-9e9d-4b9a-8c71-a22ebfd70b2d", null, null, 1, 0);

        Task mornings = new Task("Wake n Make", "Wake up earlier to enjoy the morning before getting the day started. You will have more time to enjoy your morning coffee and plenty of time to make your bed each day!", "https://firebasestorage.googleapis.com/v0/b/betterme-cf17.appspot.com/o/default%20task%20images%2Fwake%20and%20make.jpg?alt=media&token=67755236-aed9-47e5-a4fa-de2f5e3eaf7c", null, null, 1, 0);

        Task rejection = new Task("Get Rejected", "Work on social anxiety and the fear of rejection by getting rejected by a person once a day.  Ask a crush on a date or ask a stranger if you can borrow $100 dollars for a week.", "https://firebasestorage.googleapis.com/v0/b/betterme-cf17.appspot.com/o/default%20task%20images%2Frejected.jpg?alt=media&token=c8c823bc-11f7-4b76-8f6a-ee06c23a6962", null, null, 1, 0);

        Task vegandsmoothie = new Task("Vegetarian Meal and Smoothie", "Each day prepare one balanced vegetarian meal and make one fruit and vegetable smoothie", "https://firebasestorage.googleapis.com/v0/b/betterme-cf17.appspot.com/o/default%20task%20images%2Fvegetarian.jpg?alt=media&token=be36daab-eb69-4799-b8fe-bffff9d5d0d9", null, null, 1, 0);

        Task letter = new Task ("Send Letters",  "Write and send a letter to someone each day.  Letters can be a wonderful way to connect with people far away or to write something that might not be as easy to say.", "https://firebasestorage.googleapis.com/v0/b/betterme-cf17.appspot.com/o/default%20task%20images%2Fletter.jpg?alt=media&token=9bdec428-8001-4c4f-ab0f-f2c7732687f2", null, null, 1, 0);

        Task dogLove = new Task ("Dog Love", "Give your furry friend(s) special care each day.  Take your dog to the dog park, go running, get that overdue vet appointment scheduled, and give them an extra belly rub.  Just one thing each day extra to be a more loving owner of your happy dog.", "https://firebasestorage.googleapis.com/v0/b/betterme-cf17.appspot.com/o/default%20task%20images%2Fdoglove.jpg?alt=media&token=f24c8e34-018a-46a2-b5d8-9dfc4b92c46a", null, null, 1, 0);

        Collections.addAll(mTaskList, pushups, complements, meditate, mornings, rejection, vegandsmoothie, letter, dogLove);
    }
}


