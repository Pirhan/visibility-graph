package pison.pathfinding.algorithms;

import java.util.HashMap;

import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

public class PathFindingList<Node> {
    long insertCount = 0;

    FibonacciHeap<Node> heap = new FibonacciHeap<>();
    HashMap<Node, FibonacciHeapNode<Node>> fibonacciHash = new HashMap<>();
    HashMap<Node, PathFindingNode<Node>> pathFindingHash = new HashMap<>();

    public void insert(PathFindingNode<Node> record) {
        FibonacciHeapNode<Node> fRecord = new FibonacciHeapNode<>(record.getNode());
        heap.insert(fRecord, record.getCost());
        fibonacciHash.put(record.getNode(), fRecord);
        pathFindingHash.put(record.getNode(), record);
        insertCount++;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void printStats() {
        System.out.println("Insertion Count:" + insertCount);
    }

    public PathFindingNode<Node> removeSmallest() {
        Node min = heap.min().getData();
        PathFindingNode minRecord = pathFindingHash.get(min);
        fibonacciHash.remove(min);
        pathFindingHash.remove(min);
        heap.removeMin();
        return minRecord;
    }

    public boolean contains(Node node) {

        return fibonacciHash.containsKey(node);
    }

    public PathFindingNode<Node> find(Node node) {
        return pathFindingHash.get(node);
    }

    public void update(PathFindingNode<Node> record) {
        if (!contains(record.getNode()))
            return;
        heap.decreaseKey(fibonacciHash.get(record.getNode()), record.getCost());
        pathFindingHash.put(record.getNode(), record);

    }

    public void remove(Node endNode) {
        if (!fibonacciHash.containsKey(endNode))
            return;

        heap.delete(fibonacciHash.get(endNode));
        fibonacciHash.remove(endNode);
        pathFindingHash.remove(endNode);
    }
}
