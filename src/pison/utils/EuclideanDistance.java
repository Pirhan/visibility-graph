package pison.utils;

import org.newdawn.slick.geom.Point;

public class EuclideanDistance {
    public static double calculateDistance(Point point1, Point point2) {
        double x = Math.pow(point1.getX() - point2.getX(), 2.0);
        double y = Math.pow(point1.getY() - point2.getY(), 2.0);

        return x+y;
    }
}
