package pison.graph;

import java.util.List;

public class Path<Node> {
    List<Connection<Node>> edges;

    public boolean isEmpty() {
        return edges.isEmpty();
    }

    public int size() {
        return edges.size();
    }

    public Connection<Node> get(int i) {
        return edges.get(i);
    }

    public void add(Connection<Node> connection) {
        edges.add(connection);
    }

    public void setEdges(List<Connection<Node>> edges) {
        this.edges = edges;
    }
}
