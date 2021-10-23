package pison.pathfinding.algorithms.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pison.graph.Graph;
import pison.graph.Connection;
import pison.graph.Path;
import pison.graph.PathFinder;
import pison.pathfinding.algorithms.PathFindingList;
import pison.pathfinding.algorithms.PathFindingNode;

public class Astar<Node> implements PathFinder<Node> {
    AstarHeuristic<Node> heuristic;

    PathFindingList<Node> openList;
    PathFindingList<Node> closedList;


    public Astar(AstarHeuristic<Node> heuristic) {
        this.heuristic = heuristic;
    }

    private void init(Node start) {
        AstarNode<Node> startRecord = new AstarNode<>(start, null, 0, heuristic.estimate(start));

        openList = new PathFindingList<>();
        openList.insert(startRecord);

        closedList = new PathFindingList<>();
    }

    @Override
    public Path<Node> find(Graph<Node> graph, Node start, Node end) {
        init(start);

        AstarNode<Node> current = null;
        while (!openList.isEmpty()) {
            current = (AstarNode<Node>) openList.removeSmallest();
            if (current.getNode().equals(end))
                break;

            List<Connection<Node>> outgoings = graph.getConnections(current.getNode());

            for (Connection<Node> connection : outgoings) {
                Node endNode = connection.getTo();
                double endNodeCost = current.getCostSoFar() + connection.getCost();
                double endNodeHeuristic;
                if (closedList.contains(endNode)) {
                    AstarNode<Node> endRecord = (AstarNode<Node>) closedList.find(endNode);
                    // If it is not a better path just ignore
                    if (endRecord.getCostSoFar() <= endNodeCost)
                        continue;

                    closedList.remove(endNode);
                    endNodeHeuristic = endRecord.getEstimatedTotalCost() - endRecord.getCostSoFar();

                } else if (openList.contains(endNode)) {
                    AstarNode<Node> endRecord = (AstarNode<Node>) openList.find(endNode);
                    if (endRecord.getCostSoFar() <= endNodeCost)
                        continue;

                    endNodeHeuristic = endRecord.getEstimatedTotalCost() - endRecord.getCostSoFar();
                } else {
                    endNodeHeuristic = heuristic.estimate(endNode);
                }

                AstarNode<Node> endNodeRecord = new AstarNode<>(endNode, connection, endNodeCost, endNodeCost + endNodeHeuristic);

                if (!openList.contains(endNode))
                    openList.insert(endNodeRecord);
                else openList.update(endNodeRecord);

            }

            closedList.insert(current);
        }

        if (current == null || !current.getNode().equals(end))
            return null;

        openList.printStats();
        return buildPath(current, start, closedList);
    }

    private Path<Node> buildPath(PathFindingNode<Node> current, Node start, PathFindingList<Node> closed) {
        List<Connection<Node>> connections = new ArrayList<>();

        while (!current.getNode().equals(start)) {
            Connection<Node> connection = current.getConnection();

            connections.add(connection);
            current = closed.find(connection.getFrom());

        }

        Collections.reverse(connections);
        Path<Node> path = new Path<>();
        path.setEdges(connections);
        return path;
    }
}
