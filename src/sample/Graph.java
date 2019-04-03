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
        HashMap<Node,Integer> destinationCostMap = new HashMap();
        HashMap<Node,Edge> edgeTakenMap = new HashMap<>();
        HashSet<Node> traversed = new HashSet<>();
        HashSet<Node> toBeTraversed = new HashSet<>();

        //step 0: set all node destination cost to infinite except for origin
        for(Node n: nodes) destinationCostMap.put(n,Integer.MAX_VALUE);
        destinationCostMap.replace(origin, 0);
        //step 0.1: create a previous node table, with the origin pointing to itself, to verify (at the end) your initial origin faster.
        for(Node n: nodes) edgeTakenMap.put(n,null);
        //step 0.2: create settled and unsettled sets
        //              they will provide a list of nodes that have been already iterated.
        toBeTraversed.add(origin);
        while(!toBeTraversed.isEmpty()) {
            //step 1: choose an unsettled node with least amount of destination cost
            int lowestDestinationCost = Integer.MAX_VALUE;
            Node currentNode = toBeTraversed.iterator().next(); //needs to be initialized here, if for some reason the if statement fails
            for (Node n: toBeTraversed) {
                if (destinationCostMap.get(n) < lowestDestinationCost) {
                    lowestDestinationCost = destinationCostMap.get(n);
                    currentNode = n;
                }
            }
            //step 2: iterate through each edge that is not settled on that iterated node, add these nodes to unsettled.
            for(Node n: currentNode.getNeighbors()) {

            }
            //step 3: add total destination cost to each of these neighbors (referring back to step 0)
            //step 4: add the iterated node to each neighbor's previous node table.
            //step 5: add the iterated node to settled
            //step 6: step 1 - 5 until all nodes are settled
        }
        //step 7: create a list of nodes to get the order of nodes to travel through.
        //step 7.1: add the destination node to the list
        //step 8: check the node that's last in the list to check it's previous node and add it to the list
        //step 9: step 8 until reached the origin
        //step 10: return the list.

        return null;
    }

}
