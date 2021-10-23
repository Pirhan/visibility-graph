package pison.graph;

public class Connection<Node> {
    Node from;
    Node to;
    double cost;

    public Connection(Node from, Node to, double cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public double getCost() {
        return cost;
    }
}
