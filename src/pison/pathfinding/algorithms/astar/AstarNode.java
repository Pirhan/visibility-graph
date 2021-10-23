package pison.pathfinding.algorithms.astar;

import pison.graph.Connection;
import pison.pathfinding.algorithms.PathFindingNode;

public class AstarNode<Node> implements PathFindingNode<Node> {
    Node node;
    Connection<Node> connection;
    double costSoFar;
    double estimatedTotalCost;

    public AstarNode(Node node, Connection<Node> connection, double costSoFar, double estimatedTotalCost) {
        this.node = node;
        this.connection = connection;
        this.costSoFar = costSoFar;
        this.estimatedTotalCost = estimatedTotalCost;
    }

    public double getEstimatedTotalCost() {
        return estimatedTotalCost;
    }

    public Node getNode() {
        return node;
    }

    public double getCostSoFar() {
        return costSoFar;
    }

    public double getCost() {
        return estimatedTotalCost;
    }

    public Connection<Node> getConnection() {
        return connection;
    }
}
