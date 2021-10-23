package pison.graph;

import java.util.List;

public interface Graph<Node> {
    List<Connection<Node>> getConnections(Node from);
}
