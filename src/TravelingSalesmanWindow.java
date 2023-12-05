import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

public class TravelingSalesmanWindow extends JFrame {
    private JPanel mainPanel;
    private CityPanel cityPanel;

    public TravelingSalesmanWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Traveling Salesman");
        setSize(800, 600);
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        cityPanel = new CityPanel();
        mainPanel.add(cityPanel, BorderLayout.CENTER);
    }

    public void displayCities(List<City> cities) {
        cityPanel.setCities(cities);
        cityPanel.repaint();
    }

    public static void showWindow(List<City> shortestRoute) {
        EventQueue.invokeLater(() -> {
            try {
                TravelingSalesmanWindow window = new TravelingSalesmanWindow();
                window.displayCities(shortestRoute);
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    class CityPanel extends JPanel {
        private List<City> cities;

        public void setCities(List<City> cities) {
            this.cities = cities;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Calculate scaling factors with increased zoom
            double scaleX = getScaleFactor(cities, true) * 1.5; // Increase zoom by 1.5 times
            double scaleY = getScaleFactor(cities, false) * 1.5;

            // Find the bounds of the scaled cities
            int minX = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;

            for (City city : cities) {
                int scaledX = (int) (city.getX() * scaleX);
                int scaledY = (int) (city.getY() * scaleY);
                minX = Math.min(minX, scaledX);
                maxX = Math.max(maxX, scaledX);
                minY = Math.min(minY, scaledY);
                maxY = Math.max(maxY, scaledY);
            }

            // Calculate translation to center the drawing
            int translateX = (getWidth() - (maxX - minX)) / 2 - minX;
            int translateY = (getHeight() - (maxY - minY)) / 2 - minY;

            City prevCity = null;
            for (City city : cities) {
                int scaledX = (int) (city.getX() * scaleX) + translateX;
                int scaledY = (int) (city.getY() * scaleY) + translateY;
                g.fillOval(scaledX - 2, scaledY - 2, 4, 4); // Draw a small circle for the city
                g.drawString(city.getName(), scaledX + 5, scaledY + 5); // Draw the city name

                if (prevCity != null) {
                    int prevScaledX = (int) (prevCity.getX() * scaleX) + translateX;
                    int prevScaledY = (int) (prevCity.getY() * scaleY) + translateY;
                    g.drawLine(prevScaledX, prevScaledY, scaledX, scaledY); // Draw a line to the previous city
                }
                prevCity = city;
            }
        }

        private double getScaleFactor(List<City> cities, boolean isX) {
            int maxCoordinate = 0;
            for (City city : cities) {
                maxCoordinate = Math.max(maxCoordinate, isX ? city.getX() : city.getY());
            }
            // Adjust to leave less margin
            double margin = isX ? getWidth() * 0.1 : getHeight() * 0.1; // 10% margin
            return maxCoordinate > 0 ? (isX ? getWidth() - margin : getHeight() - margin) / (double) maxCoordinate : 1;
        }
    }
}
