import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class Main {
    private static final int MAX_CITIES = 5;
    public static void main(String[] args) {
        CityMap cityMapAustria = new CityMap();
        loadData(cityMapAustria, "staedte_at.txt");;

        List<City> shortestRoute = cityMapAustria.findShortestRoute(false);

        System.out.println(shortestRoute);
        TravelingSalesmanWindow.showWindow(shortestRoute);

        /*
        CityMap cityMapGermany = new CityMap();
        loadData(cityMapGermany, "staedte_de.csv", MAX_CITIES);
        cityMapGermany.findShortestRoute(false);

        CityMap cityMapUSA = new CityMap();
        loadData(cityMapUSA, "staedte_usa.csv", MAX_CITIES);
        cityMapUSA.findShortestRoute(false);
        */
    }

    private static void loadData(CityMap cityMap, String fileName) {
        loadData(cityMap, fileName, Integer.MAX_VALUE);
    }

    private static void loadData(CityMap cityMap, String fileName, int maxCities) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(fileName))))) {
            String line;

            int i = 0;
            while ((line = reader.readLine()) != null && i < maxCities) {
                String[] parts;
                String cityName;
                int x, y;

                if (fileName.equals("staedte_usa.csv")) {
                    parts = line.split(";");
                    cityName = parts[0].split(",")[0];
                    x = (int) Double.parseDouble(parts[1].trim());
                    y = (int) Double.parseDouble(parts[2].trim());
                } else {
                    parts = line.split(";");
                    cityName = parts[0];
                    x = (int) Double.parseDouble(parts[1].trim());
                    y = (int) Double.parseDouble(parts[2].trim());
                }

                cityMap.addCity(new City(cityName, x, y));
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
