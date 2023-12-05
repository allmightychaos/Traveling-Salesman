import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CityMap {
    private List<City> cities;
    private boolean showIterations;
    private long currentIteration = 0;

    public CityMap() {
        cities = new ArrayList<>();
    }

    public void addCity(City city) {
        cities.add(city);
    }

    public List<City> findShortestRoute(boolean showIterations) {
        this.showIterations = showIterations;
        this.currentIteration = 0;
        RouteDistancePair shortestPair = new RouteDistancePair(new ArrayList<>(), Double.MAX_VALUE);
        long totalPermutations = factorial(cities.size());
        permute(new ArrayList<>(cities), 0, shortestPair, totalPermutations);

        System.out.println("Kürzeste Strecke: " + routeToString(shortestPair.route) +
                "\nDistanz: " + (Math.round(shortestPair.distance * 100.0) / 100.0) + " km");
        return shortestPair.route;
    }

    private void permute(List<City> arr, int k, RouteDistancePair shortestPair, long totalPermutations) {
        if (k == arr.size() - 1) {
            double distance = calculateRouteDistance(arr);
            if (distance < shortestPair.distance) {
                shortestPair.distance = distance;
                shortestPair.route = new ArrayList<>(arr);
            }
            if (showIterations) {
                if (totalPermutations != Integer.MAX_VALUE) {
                    currentIteration++;
                    System.out.println("Current Iteration: " + currentIteration + " / " + totalPermutations);
                } else {
                    System.out.println("Iteration is greater than Integer.MAX_VALUE.");
                }
            }
            return;
        }

        for (int i = k; i < arr.size(); i++) {
            Collections.swap(arr, i, k);
            permute(arr, k + 1, shortestPair, totalPermutations);
            Collections.swap(arr, k, i);
        }
    }

    private long factorial(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private double calculateRouteDistance(List<City> route) {
        double distance = 0.0;
        for (int i = 0; i < route.size() - 1; i++) {
            distance += route.get(i).dist(route.get(i + 1));
        }
        distance += route.get(route.size() - 1).dist(route.get(0)); // Zurück zur Startstadt
        return distance;
    }

    private String routeToString(List<City> route) {
        StringBuilder sb = new StringBuilder();
        for (City city : route) {
            sb.append(city.getName()).append(" -> ");
        }
        sb.append(route.get(0).getName()); // Zurück zur Startstadt
        return sb.toString();
    }

    private static class RouteDistancePair {
        List<City> route;
        double distance;

        public RouteDistancePair(List<City> route, double distance) {
            this.route = route;
            this.distance = distance;
        }
    }
}
