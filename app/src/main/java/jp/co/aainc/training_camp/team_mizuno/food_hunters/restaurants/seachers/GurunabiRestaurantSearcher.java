package jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.seachers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.Restaurant;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.RestaurantSearcher;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.SearchRequest;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.utils.Sax;

public class GurunabiRestaurantSearcher implements RestaurantSearcher {

    private static final String SEARCH_URL = "http://api.gnavi.co.jp/ver2/RestSearchAPI/?keyid=2621e4dd2b1d9e2856ae7ff055a8d25e&latitude=%f&longitude=%f&range=3&hit_per_page=100";

    @Override
    public List<Restaurant> searchRestaurants(SearchRequest request) {
        String searchUrl = String.format(SEARCH_URL, request.getLat(), request.getLog());
        InputStream searchResult = getSearchResult(searchUrl);
        GurunabiAPIHandler apiHandler = new GurunabiAPIHandler();
        Sax.parse(searchResult, apiHandler);

        return  apiHandler.restaurants;
    }

    private InputStream getSearchResult(String searchUrl) {
        try {
            URL url = new URL(searchUrl);
            return url.openStream();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class GurunabiAPIHandler extends DefaultHandler {

        private static final Set<String> TARGET_NAMES = Collections.unmodifiableSet(new HashSet<>(
                Arrays.asList("name", "latitude", "longitude", "category", "shop_image1",
                        "qrcode", "opentime", "holiday", "pr_short")
        ));

        private List<Restaurant> restaurants = new ArrayList<>();

        private Stack<String> tagStack = new Stack<>();

        private StringBuilder textBuilder = new StringBuilder();

        private String restaurantName;

        private double lat;

        private double log;

        private String category;

        private String shopImage;

        private String qrCode;

        private String opentime;

        private String holiday;

        private String prShort;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            tagStack.push(localName);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (TARGET_NAMES.contains(tagStack.peek())) {
                // ダセー！
                boolean canExtract = ! "name".equals(tagStack.peek());
                boolean isNestedName = tagStack.size() >= 2 && "name".equals(tagStack.lastElement()) && "name".equals(tagStack.get(tagStack.size() - 2));
                if (canExtract || isNestedName) {
                    char[] partialChars = Arrays.copyOfRange(ch, start, length);
                    textBuilder.append(partialChars);
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (tagStack.pop()) {
                case "rest":
                    restaurants.add(new Restaurant(lat, log, restaurantName, category, shopImage,
                            qrCode, opentime, holiday, prShort));
                break;

                case "name":
                    if ("name".equals(tagStack.peek())) {
                        restaurantName = textBuilder.toString();
                    }
                break;

                case "latitude":
                    lat = Double.parseDouble(textBuilder.toString());
                break;

                case "longitude":
                    log = Double.parseDouble(textBuilder.toString());
                break;

                case "category":
                    category = textBuilder.toString();
                break;

                case "shop_image1":
                    shopImage = textBuilder.toString();
                break;

                case "qrcode":
                    qrCode = textBuilder.toString();
                break;

                case "opentime":
                    opentime = filterNoise(textBuilder);
                break;

                case "holiday":
                    holiday = textBuilder.toString();
                break;

                case "pr_short":
                    prShort = filterNoise(textBuilder);
                break;
            }

            textBuilder = new StringBuilder();
        }

        private String filterNoise(StringBuilder builder) {
            String text = builder.toString();
            return text.replaceAll("<br/>", "").replaceAll("null", "");
        }
    }
}
