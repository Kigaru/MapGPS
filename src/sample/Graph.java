package sample;

import java.util.*;

public class Graph {
    private LinkedList<Node> nodes;
    private HashSet<Edge> edges;

    public Graph() {
    nodes = new LinkedList<>();
    edges = new HashSet<>();
    }

//    public void addNode(Node n) {
//        nodes.add(n);
//    }

    public void addNode(Node... n){
        for (Node node: n) nodes.add(node);
    }

    public void addEdge(Edge... e){
        for(Edge edge : e) edges.add(edge);
    }

    public LinkedList<Edge> dijkstra(Node origin, Node destination, int criteria) {
        HashMap<Node,Float> destinationCostMap = new HashMap();
        HashMap<Node,Edge> edgeTakenMap = new HashMap<>();
        HashSet<Node> traversed = new HashSet<>();
        HashSet<Node> toBeTraversed = new HashSet<>();

        //step 0: set all node destination cost to infinite except for origin
        for(Node n: nodes) destinationCostMap.put(n,Float.MAX_VALUE);
        destinationCostMap.replace(origin, 0f);
        //step 0.1: create a edge taken table.
        for(Node n: nodes) edgeTakenMap.put(n,null);
        //step 0.2: create traversed and toBeTraversed sets
        //              they will provide a list of nodes that have been already iterated.
        toBeTraversed.add(origin);
        while(!toBeTraversed.isEmpty()) {
            float lowestDestinationCost = Integer.MAX_VALUE;
            Node currentNode = toBeTraversed.iterator().next(); //needs to be initialized here, if for some reason the if statement fails
            //step 1: choose an unsettled node with least amount of destination cost
            for (Node n: toBeTraversed) {
                if (destinationCostMap.get(n) < lowestDestinationCost) {
                    lowestDestinationCost = destinationCostMap.get(n);
                    currentNode = n;
                }
            }
            for(Edge e: currentNode.getEdges()) {
                Node dest = e.getTheOtherNode(currentNode);//this is the node that is connected to the current node with the edge e
                if(!traversed.contains(dest)){
                    float currentCost = destinationCostMap.get(currentNode) + e.getWeight()[criteria];
                    //step 2: iterate through each edge that is not settled on that iterated node, add these nodes to unsettled.
                    toBeTraversed.add(dest);
                    //if the current route cost is cheaper than the one already in the table
                    //do steps 3 & 4
                    if(currentCost < destinationCostMap.get(dest)) {
                        //step 3: add total destination cost to each of these neighbors
                        destinationCostMap.replace(dest,currentCost);
                        //step 4: add the iterated node to each neighbor's edge taken table.
                        edgeTakenMap.replace(dest,e);
                    }
                }
            }
            //step 5: add the iterated node to settled
            toBeTraversed.remove(currentNode);
            traversed.add(currentNode);
            //step 6: step 1 - 5 until all nodes are settled
        }
        //step 7: create a list of edges to get the order of edges to travel through.
        LinkedList<Edge> dijkstraPath = new LinkedList<>();
        //create a current destination node
        if(traversed.contains(destination)) {
            Node dest = destination;
            while (!dest.equals(origin)) {
                //step 8: add the edge that's taken from the current destination node to the route list.

                dijkstraPath.add(edgeTakenMap.get(dest));
                dest = dijkstraPath.getLast().getTheOtherNode(dest);
            }
        }
        //step 9: step 8 until reached the origin
        //step 10: return the list.
        Collections.reverse(dijkstraPath);
        return dijkstraPath;
    }

    public LinkedList<Node> getNodes() {
        return nodes;
    }

    public Set<Edge> getEdges() {
        return edges;
    }
}
