package pison.graph;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path)) return false;

        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i) != ((Path<?>) o).edges.get(i)) {
                return false;
            }
        }
        return edges.size() == ((Path<?>) o).edges.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(edges);
    }
}
