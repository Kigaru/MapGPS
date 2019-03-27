package sample;

public class Edge {
    private Node origin; //these names help if a route is unidirectional
    private Node destination; //TODO junit test whether edges connect to node
    private float weight;

    public Edge(Node origin, Node destination, float weight){
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        origin.getEdges().add(this);
        destination.getEdges().add(this);
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

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
