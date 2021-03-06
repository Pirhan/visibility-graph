package pison.visibilitygraph;

import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import pison.graph.Connection;
import pison.graph.Path;
import pison.pathfinding.algorithms.astar.Astar;
import pison.pathfinding.algorithms.astar.EuclideanDistanceHeuristic;
import pison.pathfinding.algorithms.dijkstra.Dijkstra;
import pison.visibilitygraph.styles.LineStyle;


public class VisibilityGraphPanel extends BasicGameState {
    private static final Color LineColor = Color.black;
    private static final Color FullCellColor = Color.blue;
    private final float shortestPathLineWidth = 4.0F;
    private float offsetX = -10;
    private float cellWidth = 20;
    private float offsetY = -10;
    private float cellHeight = 20;

    private final VisibilityGraph visibilityGraph;

    List<SimpleEntry<Path<Point>, LineStyle>> pathList = new ArrayList<>();

    public void addPath(Path<Point> path, LineStyle lineStyle) {
        pathList.add(new SimpleEntry<>(path, lineStyle));
    }

    public VisibilityGraphPanel(VisibilityGraph visibilityGraph) {
        this.visibilityGraph = visibilityGraph;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        for (int i = 0; i < visibilityGraph.polygons.size(); i++) {
            renderPolygon(graphics, visibilityGraph.polygons.get(i), FullCellColor);
        }

        Color c = Color.red;
        renderStartAndEnd(graphics, visibilityGraph.startPoint, visibilityGraph.endPoint, c);


        for (int i = 0; i < visibilityGraph.nodes.size(); i++) {
            Point currentPoint = visibilityGraph.nodes.get(i);
            List<Connection<Point>> currentConnections = visibilityGraph.getConnections(currentPoint);
            for (Connection<Point> currentConnection : currentConnections) {
                renderLine(graphics, currentConnection, c);
            }
        }

        renderPaths(graphics);
    }

    private void renderLine(Graphics graphics, Connection<Point> connection, Color fillColor) {
        graphics.setColor(fillColor);

        graphics.drawLine(connection.getFrom().getX(), connection.getFrom().getY(), connection.getTo().getX(), connection.getTo().getY());
    }


    private void renderStartAndEnd(Graphics graphics, Point startPoint, Point endPoint, Color fillColor) {
        graphics.setColor(fillColor);

        renderPoint(graphics, startPoint, fillColor);
        renderPoint(graphics, endPoint, fillColor);
    }

    private void renderPolygon(Graphics graphics, Polygon polygon, Color fillColor) {
        graphics.setColor(fillColor);

        graphics.fill(polygon);
        graphics.draw(polygon);
    }

    private void renderPaths(Graphics graphics) {
        for (SimpleEntry<Path<Point>, LineStyle> pathColorSimpleEntry : pathList) {
            renderPath(graphics, pathColorSimpleEntry);
        }
    }

    private void renderPath(Graphics graphics, SimpleEntry<Path<Point>, LineStyle> pathStylePair) {
        LineStyle lineStyle = pathStylePair.getValue();
        Path<Point> path = pathStylePair.getKey();

        if (path.isEmpty())
            return;

        graphics.setColor(lineStyle.getColor());
        graphics.setLineWidth(lineStyle.getWidth());

        for (int i = 0; i < path.size(); i++) {
            Connection<Point> connection = path.get(i);
            renderLine(graphics, connection, lineStyle.getColor());
            renderLine(graphics, connection, lineStyle.getColor());
        }

        graphics.resetLineWidth();
    }

    private void renderPoint(Graphics graphics, Point node, Color fillColor) {
        float x = offsetX + node.getX();
        float y = offsetY + node.getY();

        graphics.setColor(fillColor);
        graphics.fillOval(x, y, cellWidth, cellHeight);

        graphics.setColor(LineColor);
        graphics.drawOval(x, y, cellWidth, cellHeight);
    }

    public void prepareForRender() {
        Dijkstra<Point> dijkstra = new Dijkstra<>();
        Path<Point> p = dijkstra.find(visibilityGraph, visibilityGraph.startPoint, visibilityGraph.endPoint);

        Astar<Point> astar = new Astar<>(new EuclideanDistanceHeuristic(visibilityGraph.endPoint));
        Path<Point> p2 = astar.find(visibilityGraph, visibilityGraph.startPoint, visibilityGraph.endPoint);

        if (p.equals(p2)) {
            addPath(p, new LineStyle(Color.green, shortestPathLineWidth));
        } else {
            addPath(p, new LineStyle(Color.green, shortestPathLineWidth));
            addPath(p2, new LineStyle(Color.green, shortestPathLineWidth));
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        visibilityGraph.startPoint.setLocation(gameContainer.getInput().getMouseX(), gameContainer.getInput().getMouseY());

        visibilityGraph.startPoint.setCenterX(gameContainer.getInput().getMouseX());
        visibilityGraph.startPoint.setCenterY(gameContainer.getInput().getMouseY());
        visibilityGraph.buildConnections();

        pathList.clear();

        prepareForRender();
    }
}
