package pison.pathfinding.algorithms.astar;

import org.newdawn.slick.geom.Point;
import pison.utils.EuclideanDistance;

public class EuclideanDistanceHeuristic implements AstarHeuristic<Point> {
    Point target;

    public EuclideanDistanceHeuristic(Point target) {
        this.target = target;
    }

    @Override
    public double estimate(Point point) {
        return EuclideanDistance.calculateDistance(target, point);
    }
}
