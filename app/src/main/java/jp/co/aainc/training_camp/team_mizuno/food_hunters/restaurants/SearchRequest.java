package jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants;

public class SearchRequest {

    public final double lat;

    public final double log;

    public SearchRequest(double lat, double log) {
        this.lat = lat;
        this.log = log;
    }

    public double getLat() {
        return lat;
    }

    public double getLog() {
        return log;
    }
}
