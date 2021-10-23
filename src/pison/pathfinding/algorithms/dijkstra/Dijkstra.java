package pison.pathfinding.algorithms.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pison.graph.Graph;
import pison.graph.Connection;
import pison.graph.Path;
import pison.graph.PathFinder;
import pison.pathfinding.algorithms.PathFindingList;
import pison.pathfinding.algorithms.PathFindingNode;

public class Dijkstra<Node> implements PathFinder<Node> {
    @Override
    public Path<Node> find(Graph<Node> graph, Node start, Node end) {
        DijkstraNode<Node> startRecord = new DijkstraNode<>(start, null, 0);

        PathFindingList<Node> openList = new PathFindingList<>();
        openList.insert(startRecord);

        PathFindingList<Node> closedList = new PathFindingList<>();

        PathFindingNode<Node> current = null;
        while (!openList.isEmpty()) {
            current = openList.removeSmallest();
            if (current.getNode().equals(end))
                break;

            List<Connection<Node>> outgoings = graph.getConnections(current.getNode());

            for (Connection<Node> connection : outgoings) {
                Node endNode = connection.getTo();
                if (closedList.contains(endNode))
                    continue;

                double endNodeCost = current.getCost() + connection.getCost();
                if (openList.contains(endNode)) {
                    PathFindingNode<Node> oldEndRecord = openList.find(endNode);

                    if (endNodeCost >= oldEndRecord.getCost())
                        continue;

                    openList.update(new DijkstraNode(endNode, connection, endNodeCost));
                } else openList.insert(new DijkstraNode(endNode, connection, endNodeCost));
            }

            closedList.insert(current);
        }

        if (current == null || !current.getNode().equals(end))
            return null;

        openList.printStats();
        return buildPath(current, start, closedList);
    }

    private Path<Node> buildPath(PathFindingNode<Node> current, Node start, PathFindingList<Node> closed) {
        Path<Node> path = new Path();
        List<Connection<Node>> edges = new ArrayList<>();

        while (!current.getNode().equals(start)) {
            Connection<Node> connection = current.getConnection();

            edges.add(connection);
            current = closed.find(connection.getFrom());

        }

        Collections.reverse(edges);
        path.setEdges(edges);
        return path;
    }
}
