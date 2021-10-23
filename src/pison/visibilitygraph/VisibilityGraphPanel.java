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


public class VisibilityGraphPanel extends BasicGameState {
    private static final Color LineColor = Color.black;
    private static final Color FullCellColor = Color.blue;
    private float offsetX = -10;
    private float cellWidth = 20;
    private float offsetY = -10;
    private float cellHeight = 20;

    private final VisibilityGraph visibilityGraph;

    List<SimpleEntry<Path<Point>, Color>> pathList = new ArrayList<>();

    public void addPath(Path<Point> p, Color c) {
        pathList.add(new SimpleEntry<>(p, c));
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
        for (SimpleEntry<Path<Point>, Color> pathColorSimpleEntry : pathList) {
            renderPath(graphics, pathColorSimpleEntry);
        }
    }

    private void renderPath(Graphics graphics, SimpleEntry<Path<Point>, Color> pathColorPair) {
        Color c = pathColorPair.getValue();
        Path<Point> p = pathColorPair.getKey();

        if (p.isEmpty())
            return;

        graphics.setColor(c);
        for (int i = 0; i < p.size(); i++) {
            Connection<Point> connection = p.get(i);
            renderLine(graphics, connection, c);
            renderLine(graphics, connection, c);
        }
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
            addPath(p, Color.green);
        } else {
            addPath(p, Color.white);
            addPath(p2, Color.yellow);
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
