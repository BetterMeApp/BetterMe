package Model;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by louis on 10/31/17.
 */

public class Task {

    public String title;
    public String description;
    public String taskImgURL;
    public Time startTime;
    public Date startDate;
    public Integer goalNumber;
    public Integer completedNumber;
    public Boolean completed;
    public Integer dayscompleted;

    public Task(String title, String description, String imgURL, Time time, Date date, Integer goal, Integer done, Boolean isCompleted, Integer days){
        this.title = title;
        this.description = description;
        this.taskImgURL = imgURL;
        this.startTime = time;
        this.startDate = date;
        this.goalNumber = goal;
        this.completedNumber = done;
        this.completed = isCompleted;
        this.dayscompleted = days;

    }

}
