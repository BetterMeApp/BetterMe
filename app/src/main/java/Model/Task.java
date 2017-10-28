package Model;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by brandonholderman on 10/26/17.
 */

public class Task {

    String mTaskTitle;
    String mDescription;
    Time mStartTime;
    Date mStartDate;
    Integer mGoalNumber;
    Integer mCompletedNumber;

    public Task(String title, String description, Time time, Date date, Integer goal, Integer completed){
        this.mTaskTitle = title;
        this.mDescription = description;
        this.mStartTime = time;
        this.mStartDate = date;
        this.mGoalNumber = goal;
        this.mCompletedNumber = completed;
    }

}
