package jp.co.aainc.training_camp.team_mizuno.food_hunters.tasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.Restaurant;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.RestaurantSearcher;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.RestaurantSearcherFactory;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.SearchRequest;

public class RestaurantSearchAsyncTask extends AsyncTask<SearchRequest, String, List<Restaurant>> {

    private static final RestaurantSearcher searcher = RestaurantSearcherFactory.newSearcher();

    @Override
    protected List<Restaurant> doInBackground(SearchRequest... params) {
        List<Restaurant> restaurants = new ArrayList<>();
        for (SearchRequest request : params) {
            List<Restaurant> ressults = searcher.searchRestaurants(request);
            restaurants.addAll(ressults);
        }

        return restaurants;
    }
}
