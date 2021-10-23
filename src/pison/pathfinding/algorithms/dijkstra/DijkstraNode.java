package pison.pathfinding.algorithms.dijkstra;

import pison.graph.Connection;
import pison.pathfinding.algorithms.PathFindingNode;

public class DijkstraNode<Node> implements PathFindingNode<Node> {
    Node node;
    Connection<Node> connection;
    double costSoFar;

    public DijkstraNode(Node node, Connection<Node> connection, double costSoFar) {
        this.node = node;
        this.connection = connection;
        this.costSoFar = costSoFar;
    }

    public Node getNode() {
        return node;
    }

    public double getCost() {
        return costSoFar;
    }

    public Connection<Node> getConnection() {
        return connection;
    }
}
