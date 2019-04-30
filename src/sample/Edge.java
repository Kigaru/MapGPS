package sample;

import java.util.LinkedList;

public class Edge {
    private Node origin; //these names help if a route is unidirectional
    private Node destination; //TODO junit test whether edges connect to node
    private float[] weight = new float[3];

    public Edge(Node origin, Node destination, float length, float difficulty, float safety){
        this.origin = origin;
        this.destination = destination;
        this.weight[0] = length;
        this.weight[1] = difficulty;
        this.weight[2] = safety;
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

    public float[] getWeight() {
        return weight;
    }

    public void setWeight(float[] weight) {
        this.weight = weight;
    }

    public void setLength(float length){
        this.weight[0] = length;
    }

    public void setDifficulty(float difficulty){
        this.weight[1] = difficulty;
    }

    public void setSafety(float safety){
        this.weight[2] = safety;
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
