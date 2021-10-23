package pison.graph;

public interface PathFinder<Node> {
    Path<Node> find(Graph<Node> graph, Node start, Node end);
}
