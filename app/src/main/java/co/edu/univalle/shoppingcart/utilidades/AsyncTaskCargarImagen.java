package co.edu.univalle.shoppingcart.utilidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AsyncTaskCargarImagen extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;

    public AsyncTaskCargarImagen(ImageView imageView){
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(strings[0]);
            bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage());
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
