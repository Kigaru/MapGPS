package sample;

import java.util.LinkedList;

public class Node {
    LinkedList<Edge> edges;

    public Node() {
        edges = new LinkedList<>();
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }
}
