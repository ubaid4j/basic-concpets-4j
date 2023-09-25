package dev.ubaid.labs;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class AvenueCode {
    
    
    @Test
    void test() {
        System.out.println(finestFoodOutlet("Seattle", 500));
    }
    
 
    
    

    Gson gson = new Gson();

    Comparator<Outlet> sortByHighestRating = Comparator
        .comparingDouble((Outlet o) -> o.getRating().getAverageRating())
        .reversed();
    
    Comparator<Outlet> sortByHighestVote = Comparator
        .comparingInt((Outlet o) -> o.getRating().getVotes())
        .reversed();


    public String finestFoodOutlet(String city, int votes) {
        List<Outlet> outlets = getAllOutLets();
        // filter the result by city name, the highest rating and vote count is greater or equal to given votes
        
        //filter
        outlets = outlets
            .stream()
            .filter(outlet -> city.equals(outlet.getCity()))
            .filter(outlet -> outlet.getRating().getVotes() >= votes)
            .collect(Collectors.toList());
        
        //sort
        outlets.sort(sortByHighestRating);
        
        Double highestRating = outlets
            .stream()
            .findFirst()
            .orElseThrow().getRating().getAverageRating();

        outlets = outlets
            .stream()
            .filter(outlet ->  outlet.getRating().getAverageRating() == highestRating)
            .collect(Collectors.toList());
        
        outlets.sort(sortByHighestVote);
        
        
        
        return outlets.get(0).getName();
    }

    private static List<Outlet> getAllOutLets() {
        List<Outlet> outlets = new ArrayList<>();
        // Connect to the URL using java's native library
        try {
            URL url = new URL("https://jsonmock.hackerrank.com/api/food_outlets?page=1");
            BufferedReader rd = new BufferedReader(new InputStreamReader(url.openStream(), Charset.forName("UTF-8")));
            String json = readAll(rd);
            String total = json.substring(json.indexOf("total"));
            total = total.substring(total.indexOf(":") + 1, total.indexOf(","));
            System.out.println("total: " + total);
            json = json.replaceAll("\\s+", "");
            String totalPages = json.substring(json.indexOf("total_pages"));
            totalPages = totalPages.substring(totalPages.indexOf(":") + 1, totalPages.indexOf(","));
            
            for (int page = 1; page <= Integer.parseInt(totalPages); page++) {
                url = new URL("https://jsonmock.hackerrank.com/api/food_outlets?page=" + page);
                rd = new BufferedReader(new InputStreamReader(url.openStream(), Charset.forName("UTF-8")));
                json = readAll(rd);
                String data = json.substring(json.indexOf("[{"), json.lastIndexOf("}]"));
                String[] outletsStr = data.split("\\},\\{");
                for (String outletStr: outletsStr) {
                    outletStr = outletStr.replaceAll("\\s+", "");
                    String name = outletStr.substring(outletStr.indexOf("name"));
                    name = name.substring(name.indexOf(":") + 1, name.indexOf(","));
                    name = name.replaceAll("\"", "");
                    
                    String city = outletStr.substring(outletStr.indexOf("city"));
                    city = city.substring(city.indexOf(":") + 1, city.indexOf(","));
                    city = city.replaceAll("\"", "");
                    
                    String estimatedCost = outletStr.substring(outletStr.indexOf("estimated_cost"));
                    estimatedCost = estimatedCost.substring(estimatedCost.indexOf(":") + 1, estimatedCost.indexOf(","));

                    String averageRating = outletStr.substring(outletStr.indexOf("average_rating"));
                    averageRating = averageRating.substring(averageRating.indexOf(":") + 1, averageRating.indexOf(","));

                    String votes = outletStr.substring(outletStr.indexOf("votes"));
                    votes = votes.substring(votes.indexOf(":") + 1, votes.indexOf("},"));

                    try {
                        UserRating rating = new UserRating(Double.parseDouble(averageRating), Integer.parseInt(votes));
                        Outlet outlet = new Outlet(name, city, Double.parseDouble(estimatedCost), rating);
                        outlets.add(outlet);
                    } catch (NumberFormatException exp) {
                        System.out.println(exp);
                    }
                }

            }
            System.out.println("total outlets: " + outlets.size());
        } catch(Exception exp) {
            System.err.println("exception:" + exp);
        }
        return outlets;
    }


    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    

}

class Outlet {
    private final String name;
    private final String city;
    private final Double estimatedCost;

    private final UserRating rating;

    public Outlet(String name, String city, Double estimatedCost, UserRating rating) {
        this.name = name;
        this.city = city;
        this.estimatedCost = estimatedCost;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public UserRating getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Outlet{" +
            "name='" + name + '\'' +
            ", city='" + city + '\'' +
            ", estimatedCost=" + estimatedCost +
            ", rating=" + rating +
            '}';
    }
}

class UserRating {
    private final Double averageRating;
    private final Integer votes;

    public UserRating(Double averageRating, Integer votes) {
        this.averageRating = averageRating;
        this.votes = votes;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Integer getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return "UserRating{" +
            "averageRating=" + averageRating +
            ", votes=" + votes +
            '}';
    }
}
