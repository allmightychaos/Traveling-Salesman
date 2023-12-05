import java.awt.Point;

public class City {
    private String name;
    private Point location;


    public City(String name, int x, int y) {
        this.name = name;
        this.location = new Point(x, y);
    }

    public double dist(City other) {
        return location.distance(other.location);
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return location.x;
    }

    public int getY() {
        return location.y;
    }

    @Override
    public String toString() {
        return name + " (" + location.x + ", " + location.y + ")";
    }
}