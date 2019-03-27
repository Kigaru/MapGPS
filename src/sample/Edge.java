package sample;

public class Edge {
    private Node origin; //these names help if a route is unidirectional
    private Node destination; //TODO junit test whether edges connect to node
    private float weight;

    public Edge(Node origin, Node destination){
        this.origin = origin;
        this.destination = destination;
    }

    public Node getOrigin() {
        return origin;
    }

    public Node getDestination() {
        return destination;
    }

    public float getWeight() {
        return weight;
    }
}
