package jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants;

import jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.seachers.GurunabiRestaurantSearcher;

public class RestaurantSearcherFactory {

    public static RestaurantSearcher newSearcher() {
        return new GurunabiRestaurantSearcher();
    }
}
