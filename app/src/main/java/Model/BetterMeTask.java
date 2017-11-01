package Model;

import com.google.android.gms.tasks.Task;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by brandonholderman on 10/26/17.
 */

public class BetterMeTask {

    public static Task selectedTask;
//    private static final String Tag = "BetterMeTask";
    public String title;
    public String description;
    public String taskImg;
    public Time startTime;
    public Date startDate;
    public Integer goalNumber;
    public Integer completedNumber;
    public Integer amountMinutes;
    public Integer numberOfCheckBoxes;

    public BetterMeTask(String title, String description, String imgURL, Time time, Date date, Integer goal, Integer done, Integer minutes, Integer checkBoxes){
        this.title = title;
        this.description = description;
        this.taskImg = imgURL;
        this.startTime = time;
        this.startDate = date;
        this.goalNumber = goal;
        this.completedNumber = done;
        this.amountMinutes = minutes;
        this.numberOfCheckBoxes = checkBoxes;
    }
}
