package sample;

import java.util.*;

public class Graph {
    public LinkedList<Node> getNodes() {
        return nodes;
    }

    private LinkedList<Node> nodes;

    public Graph() {
    nodes = new LinkedList<>();
}

    public void addNode(Node n) {
        nodes.add(n);
    }

    public void addAllNodes(Node... n){
        for (Node node: n) nodes.add(node);
    }

    public LinkedList<Edge> dijkstra(Node origin, Node destination) {
        HashMap<Node,Float> destinationCostMap = new HashMap();
        HashMap<Node,Edge> edgeTakenMap = new HashMap<>();
        HashSet<Node> traversed = new HashSet<>();
        HashSet<Node> toBeTraversed = new HashSet<>();

        //step 0: set all node destination cost to infinite except for origin
        for(Node n: nodes) destinationCostMap.put(n,Float.MAX_VALUE);
        destinationCostMap.replace(origin, 0f);
        //step 0.1: create a previous node table, with the origin pointing to itself, to verify (at the end) your initial origin faster.
        for(Node n: nodes) edgeTakenMap.put(n,null);
        //step 0.2: create settled and unsettled sets
        //              they will provide a list of nodes that have been already iterated.
        toBeTraversed.add(origin);
        while(!toBeTraversed.isEmpty()) {
            //step 1: choose an unsettled node with least amount of destination cost
            float lowestDestinationCost = Integer.MAX_VALUE;
            Node currentNode = toBeTraversed.iterator().next(); //needs to be initialized here, if for some reason the if statement fails
            for (Node n: toBeTraversed) {
                if (destinationCostMap.get(n) < lowestDestinationCost) {
                    lowestDestinationCost = destinationCostMap.get(n);
                    currentNode = n;
                }
            }
            for(Edge e: currentNode.getEdges()) {
                if(!traversed.contains(e.getDestination())){
                    float currentCost = destinationCostMap.get(currentNode) + e.getWeight();
                    //step 2: iterate through each edge that is not settled on that iterated node, add these nodes to unsettled.
                    toBeTraversed.add(e.getDestination());
                    //step 3: add total destination cost to each of these neighbors (referring back to step 0)
                    if(currentCost < destinationCostMap.get(e.getDestination())) {
                        destinationCostMap.replace(e.getDestination(),currentCost);
                        //step 4: add the iterated node to each neighbor's previous node table.
                        edgeTakenMap.replace(e.getDestination(),e);
                    }
                }
            }
            //step 5: add the iterated node to settled
            toBeTraversed.remove(currentNode);
            traversed.add(currentNode);
            //step 6: step 1 - 5 until all nodes are settled
        }
        //step 7: create a list of nodes to get the order of nodes to travel through.
        LinkedList<Edge> dijkstraPath = new LinkedList<>();
        //step 7.1: add the destination node to the list
        dijkstraPath.add(edgeTakenMap.get(destination));
        //step 8: check the node that's last in the list to check it's previous node and add it to the list
        while(dijkstraPath.getLast().getOrigin() != origin) dijkstraPath.add(edgeTakenMap.get(dijkstraPath.getLast().getOrigin()));
        //step 9: step 8 until reached the origin
        //step 10: return the list.

        LinkedList<Edge> flippedPath = new LinkedList<>();//TODO i thought there was a method to flip the list no? xd
        for(Edge e : dijkstraPath) {
            flippedPath.addFirst(e);
        }
        return flippedPath;
    }

}
