package Model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by paulhise on 11/1/17.
 */


public class CreateTasksList {

    private static ArrayList<Task> mTaskList;

    public static ArrayList<Task> createTaskArrayList() {
        mTaskList = new ArrayList<>();

        Task pushups = new Task("Push-ups", "Stay fit!  Do push-ups throughout the day until you reach your goal.", "https://firebasestorage.googleapis.com/v0/b/betterme-cf17.appspot.com/o/default%20task%20images%2Fpushup.jpg?alt=media&token=99aec2ef-07fe-42ca-945d-3a216d12b3ef", null, null, 1, 0, false, 0);

        Task complements = new Task("Give complements" ,"Give complements to a person or people in your life.", "https://firebasestorage.googleapis.com/v0/b/betterme-cf17.appspot.com/o/default%20task%20images%2Fcomplement.jpg?alt=media&token=ff3bf693-14ec-4c3a-b0e5-44e284f00a68", null, null, 1, 0, false, 0);

        Task meditate = new Task("Meditate", "Set time aside to meditate each day.", "https://firebasestorage.googleapis.com/v0/b/betterme-cf17.appspot.com/o/default%20task%20images%2Fmeditation.jpg?alt=media&token=2942258a-9e9d-4b9a-8c71-a22ebfd70b2d", null, null, 1, 0, false, 0);

        Task mornings = new Task("Wake n Make", "Wake up earlier to enjoy the morning before getting the day started.", "https://firebasestorage.googleapis.com/v0/b/betterme-cf17.appspot.com/o/default%20task%20images%2Fwake%20and%20make.jpg?alt=media&token=67755236-aed9-47e5-a4fa-de2f5e3eaf7c", null, null, 1, 0, false, 0);

        Task rejection = new Task("Get Rejected", "Work on social anxiety and the fear of rejection by getting rejected by a person once a day.", "https://firebasestorage.googleapis.com/v0/b/betterme-cf17.appspot.com/o/default%20task%20images%2Frejected.jpg?alt=media&token=c8c823bc-11f7-4b76-8f6a-ee06c23a6962", null, null, 1, 0, false, 0);

        Task vegandsmoothie = new Task("Vegetarian Meal and Smoothie", "Each day prepare one balanced vegetarian meal and make one fruit and vegetable smoothie", "https://firebasestorage.googleapis.com/v0/b/betterme-cf17.appspot.com/o/default%20task%20images%2Fvegetarian.jpg?alt=media&token=be36daab-eb69-4799-b8fe-bffff9d5d0d9", null, null, 1, 0, false, 0);

        Task letter = new Task ("Send Letters",  "Write and send a letter to someone each day.", "https://firebasestorage.googleapis.com/v0/b/betterme-cf17.appspot.com/o/default%20task%20images%2Fletter.jpg?alt=media&token=9bdec428-8001-4c4f-ab0f-f2c7732687f2", null, null, 1, 0, false, 0);

        Task dogLove = new Task ("Dog Love", "Do something extra each day to be a more loving owner of your happy dog.", "https://firebasestorage.googleapis.com/v0/b/betterme-cf17.appspot.com/o/default%20task%20images%2Fdoglove.jpg?alt=media&token=f24c8e34-018a-46a2-b5d8-9dfc4b92c46a", null, null, 1, 0, false, 0);

        Collections.addAll(mTaskList, pushups, complements, meditate, mornings, rejection, vegandsmoothie, letter, dogLove);

    return mTaskList;
    }
}
