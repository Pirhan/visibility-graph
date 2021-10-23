package pison.pathfinding.algorithms;

import pison.graph.Connection;

public interface PathFindingNode<Node> {
    Node getNode();
    double getCost();
    Connection<Node> getConnection();
}
