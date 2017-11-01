package Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by steven on 10/28/17.
 */

public class DownloadImageTask extends AsyncTask<String, String, Bitmap> {
    private static final String TAG = "DownloadImageTask";

    private ImageView imgView;
    private String mUrl;
    private static Map<String, Bitmap> cache = new HashMap<>();

    public DownloadImageTask(String Url, ImageView imageView){
        this.mUrl = Url;
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
            if (cache.containsKey(mUrl)) {
                return cache.get(mUrl);
            }
            InputStream image = new java.net.URL(mUrl).openStream();
            bmp = BitmapFactory.decodeStream(image);

            cache.put(mUrl, bmp);
            return bmp;

        } catch (Exception e) {
            Log.d(TAG, "doInBackground: IO exception caught- " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bmp) {
        if (bmp != null) {
            super.onPostExecute(bmp);
            imgView.setImageBitmap(bmp);
        }
    }



}
