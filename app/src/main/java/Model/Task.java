package Model;

import java.util.Date;

/**
 * Created by louis on 10/31/17.
 */

public class Task {
    public static String mCurrentTask;

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
}
