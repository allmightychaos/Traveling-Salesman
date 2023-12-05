import javax.swing.*;
import java.awt.*;
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
            City prevCity = null;
            for (City city : cities) {
                int x = city.getX();
                int y = city.getY();
                System.out.println(x / 10 + " " + y / 10);
                g.fillOval((x / 10) - 2, (y / 10) - 2, 4, 4); // city
                g.drawString(city.getName(), (x / 10) + 5, (y / 10) + 5); // city name

                if (prevCity != null) {
                    g.drawLine(prevCity.getX(), prevCity.getY(), x, y); // line (previous city -> this)
                }
                prevCity = city;
            }
        }
    }
}
