package pison.visibilitygraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import pison.graph.Connection;
import pison.graph.Graph;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import pison.pathfinding.algorithms.astar.EuclideanDistanceHeuristic;

public class VisibilityGraph implements Graph<Point> {
    public Point startPoint;
    public Point endPoint;
    List<Polygon> polygons;
    List<Point> nodes;

    HashMap<Point, List<Connection<Point>>> connections;

    public VisibilityGraph(Point start, Point end, Polygon... polygons) {
        this.nodes = new ArrayList<>();

        this.startPoint = start;
        this.endPoint = end;

        this.nodes.add(start);
        this.nodes.add(end);

        this.polygons = new ArrayList<>(Arrays.asList(polygons));

        buildPolygons();

        buildConnections();
    }

    private void buildPolygons() {

        for(Polygon polygon : this.polygons) {
            float[] points = polygon.getPoints();

            for(int i = 1; i < points.length; i = i + 2) {
                this.nodes.add(new Point(points[i - 1], points[i]));
            }
        }
    }

    public void buildConnections() {
        this.connections = new HashMap<>();

        for(int i = 0; i < this.nodes.size(); i++) {
            List<Connection<Point>> connectionList = new ArrayList<>();

            for(int j = 0; j < this.nodes.size(); j++){
                if(j != i && !intersects(this.nodes.get(i),this.nodes.get(j))) {
                    EuclideanDistanceHeuristic heuristic = new EuclideanDistanceHeuristic(this.nodes.get(i));
                    connectionList.add(new Connection<>(this.nodes.get(i), this.nodes.get(j), heuristic.estimate(this.nodes.get(j))));
                }
            }

            this.connections.put(this.nodes.get(i), connectionList);
        }
    }

    private boolean intersects(Point point1, Point point2) {
        for(int i = 0 ; i < polygons.size(); i++) {
            if(new Line(point1.getX(),point1.getY(),point2.getX(),point2.getY()).intersects(polygons.get(i))
                    &&new Line(point1.getX() + 1,point1.getY(),point2.getX(),point2.getY()).intersects(polygons.get(i))
                    &&new Line(point1.getX() - 1,point1.getY(),point2.getX(),point2.getY()).intersects(polygons.get(i))
                    &&new Line(point1.getX(),point1.getY()+1,point2.getX(),point2.getY()).intersects(polygons.get(i))
                    &&new Line(point1.getX() + 1,point1.getY()+1,point2.getX() + 1,point2.getY() + 1).intersects(polygons.get(i))
                    &&new Line(point1.getX(),point1.getY()-1,point2.getX(),point2.getY()).intersects(polygons.get(i))
                    &&new Line(point1.getX() - 1,point1.getY()-1,point2.getX(),point2.getY()).intersects(polygons.get(i))
                    &&new Line(point1.getX()+1,point1.getY()-1,point2.getX(),point2.getY()).intersects(polygons.get(i))
                    &&new Line(point1.getX() - 1,point1.getY()+1,point2.getX(),point2.getY()).intersects(polygons.get(i))
                    &&new Line(point1.getX(),point1.getY(),point2.getX()+ 1,point2.getY()).intersects(polygons.get(i))
                    &&new Line(point1.getX(),point1.getY(),point2.getX() - 1,point2.getY()).intersects(polygons.get(i))
                    &&new Line(point1.getX(),point1.getY(),point2.getX(),point2.getY()+1).intersects(polygons.get(i))
                    &&new Line(point1.getX(),point1.getY(),point2.getX(),point2.getY()-1).intersects(polygons.get(i))
                    &&new Line(point1.getX() ,point1.getY()- 1,point2.getX(),point2.getY()-1).intersects(polygons.get(i))
                    &&new Line(point1.getX(),point1.getY(),point2.getX()+1,point2.getY()-1).intersects(polygons.get(i))
                    &&new Line(point1.getX(),point1.getY(),point2.getX() - 1,point2.getY()+1).intersects(polygons.get(i))
                    ){
                return true;
            }
        }

        return false;
    }

    @Override
    public List<Connection<Point>> getConnections(Point from) {
        return connections.get(from);
    }
}