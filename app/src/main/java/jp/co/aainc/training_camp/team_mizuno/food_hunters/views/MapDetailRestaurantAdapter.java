package jp.co.aainc.training_camp.team_mizuno.food_hunters.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import jp.co.aainc.training_camp.team_mizuno.food_hunters.R;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.Restaurant;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.utils.TextViewHelper;

public class MapDetailRestaurantAdapter extends BaseAdapter {

    private final List<Restaurant> restaurants;

    private final LayoutInflater inflater;

    public MapDetailRestaurantAdapter(List<Restaurant> restaurants, LayoutInflater inflater) {
        this.restaurants = restaurants;
        this.inflater = inflater;
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
        new TextViewHelper(newView)
            .setText(R.id.my_view_item_name, "飲食店名: " + item.getName())
            .setText(R.id.my_view_item_category, "カテゴリ: " + item.getCategory())
            .setText(R.id.my_view_item_pr, "説明: " + item.getPrShort())
            .setText(R.id.my_view_item_opentime, "営業時間: " + item.getOpenTime())
            .setText(R.id.my_view_item_holiday, "休業日: " + item.getHoliday());
    }
}
