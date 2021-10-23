package pison.pathfinding.algorithms.astar;

public interface AstarHeuristic<Node> {
    double estimate(Node node);
}
