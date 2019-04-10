package sample;

import java.util.LinkedList;

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
//        if(bidirectional) {
//            new Edge(destination, origin, weight,false);
//        }
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

    public Node getTheOtherNode(Node n) {
        return n.equals(origin) ? destination : origin;
    }

    public LinkedList<Node> getNodes() {
        LinkedList<Node> list = new LinkedList<>();
        list.add(origin);
        list.add(destination);
        return list;
    }

    @Override
    public String toString() {
        return "Edge: " + origin + "->" + destination + " cost of: " + weight;
    }

    @Override
    public boolean equals(Object obj) {
        return origin.equals(((Edge)obj).getOrigin())
                && destination.equals(((Edge) obj).getDestination())
                && weight == ((Edge) obj).getWeight();
    }
}
