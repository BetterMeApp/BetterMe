package Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.louis.myapplication.R;

import java.util.ArrayList;

/**
 * Created by paulhise on 11/1/17.
 */

public class TaskListAdapter extends BaseAdapter {
    private Context ctx;
    private ArrayList<Task> adapterTaskList;

    public TaskListAdapter(Context context, ArrayList<Task> taskArrayList) {
        super();
        this.adapterTaskList = taskArrayList;
        this.ctx = context;
    }

    @Override
    public int getCount() {
        return adapterTaskList.size();
    }

    @Override
    public Task getItem(int i) {
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

        view = LayoutInflater.from(ctx).inflate(R.layout.task_custom_listview_item, null);

        ImageView taskImgView = view.findViewById(R.id.imageView_task_img);
        TextView taskTitleView = view.findViewById(R.id.textView_task_title);
        TextView taskDescriptionView = view.findViewById(R.id.textView_task_description);

        new DownloadImageTask(taskURL, taskImgView).execute();
        taskTitleView.setText(currentItem.title);
        taskDescriptionView.setText(currentItem.description);

        return view;
    }
}