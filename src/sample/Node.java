package sample;

import java.util.LinkedList;
import java.util.List;

public class Node {
    private LinkedList<Edge> edges;
    private String name;

    public Node(String name) {
        this.name = name;
        edges = new LinkedList<>();
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public List<Node> getNeighbors() {
        LinkedList<Node> neighbors = new LinkedList<Node>();
        for(Edge e : edges) {
            neighbors.add(e.getDestination());
        }
        return neighbors;
    }

    public String getName() {
        return name;
    }
}
