package jp.co.aainc.training_camp.team_mizuno.food_hunters.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class WebImageResource {

    public static Bitmap loadFrom(String url) {
        try {
            URL url1 = new URL(url);
            InputStream inputStream = url1.openStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
