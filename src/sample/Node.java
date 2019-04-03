package sample;

import java.util.LinkedList;
import java.util.List;

public class Node {
    private LinkedList<Edge> edges;
    private String name;
    private int x, y;

    public Node(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        edges = new LinkedList<>();
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public LinkedList<Edge> getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return "Node: " + name;
    }
}
