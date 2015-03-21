package jp.co.aainc.training_camp.team_mizuno.food_hunters.views;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import jp.co.aainc.training_camp.team_mizuno.food_hunters.R;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.Restaurant;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.utils.ViewHelper;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.utils.WebImageResource;

public class MapDetailRestaurantAdapter extends BaseAdapter {

    private final List<Restaurant> restaurants;

    private final LayoutInflater inflater;

    private final Location currentLoc;

    public MapDetailRestaurantAdapter(List<Restaurant> restaurants, LayoutInflater inflater, Location currentLoc) {
        this.restaurants = restaurants;
        this.inflater = inflater;
        this.currentLoc = currentLoc;
    }

    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            View newView = inflater.inflate(R.layout.my_view_item, null);
            Restaurant item = (Restaurant) getItem(position);
            newView.setTag(item);
            modifyTextContents(newView, item);
            return newView;
        } else {
            Restaurant restaurant = (Restaurant) convertView.getTag();
            modifyTextContents(convertView, restaurant);
            return convertView;
        }
    }

    private void modifyTextContents(View newView, Restaurant item) {
        Spanned nameHtml = Html.fromHtml("飲食店名: <a href=\"" + item.getUrlMobile() + "\">" + item.getName() + "<a/>");
        new ViewHelper(newView)
            .setLinkText(R.id.my_view_item_name, nameHtml)
            .setText(R.id.my_view_item_category, "カテゴリ: " + item.getCategory())
            .setText(R.id.my_view_item_pr, "説明: " + item.getPrShort())
            .setText(R.id.my_view_item_opentime, "営業時間: " + item.getOpenTime())
            .setText(R.id.my_view_item_holiday, "休業日: " + item.getHoliday())
            .setText(R.id.my_view_item_distance, "直線距離: " + (int)currentLoc.distanceTo(item.toLocation()) + "m");

        AsyncTask asyncTask = new ImageDownloadAsyncTask((ImageView) newView.findViewById(R.id.my_view_item_image));
        asyncTask.execute(new String[] {item.getQrCode()});
    }

    private class ImageDownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private final ImageView imageView;

        private ImageDownloadAsyncTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return WebImageResource.loadFrom(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
