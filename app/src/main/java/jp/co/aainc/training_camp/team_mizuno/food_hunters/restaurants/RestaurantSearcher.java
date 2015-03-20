package jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants;

import java.util.List;

public interface RestaurantSearcher {

    List<Restaurant> searchRestaurants(SearchRequest request);
}
