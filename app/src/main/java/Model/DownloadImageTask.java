package Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by steven on 10/28/17.
 */

public class DownloadImageTask extends AsyncTask<String, String, Bitmap> {
    private static final String TAG = "DownloadImageTask";
    private ImageView imgView;

    public DownloadImageTask(ImageView imageView){
        this.imgView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bmp = null;
        try {
            InputStream image = new java.net.URL(strings[0]).openStream();
            bmp = BitmapFactory.decodeStream(image);
        } catch (Exception e){
            Log.d(TAG, "doInBackground: error" + e);
        }
        return bmp;
    }

    @Override
    protected void onPostExecute(Bitmap bmp) {
        super.onPostExecute(bmp);

        imgView.setImageBitmap(bmp);
    }
}
