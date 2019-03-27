package sample;

import java.util.LinkedList;
import java.util.List;

public class Node {
    private LinkedList<Edge> edges;

    public Node() {
        edges = new LinkedList<>();
    }

    public List<Node> getNeighbors() {
        LinkedList<Node> neighbors = new LinkedList<Node>();
        for(Edge e : edges) {
            neighbors.add(e.getDestination());
        }
        return neighbors;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }
}
