package sample;

import java.util.LinkedList;

public class Node {
    LinkedList<Edge> edges;

    public Node() {
        edges = new LinkedList<>();
    }

    void addEdge(Edge edge) {
        edges.add(edge);
    }
}
