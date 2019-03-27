package sample;

import java.util.LinkedList;

public class Graph {
    private LinkedList<Node> nodes;

    public LinkedList<Node> dijkstra(Node origin, Node destination) {
        //step 0: set all node destination cost to infinite
        //step 0.1: create a previous node table.
        //step 0.2: create settled and unsettled sets
        //              they will provide a list of nodes that have been already iterated.
        //step 1: choose an unsettled node with least amount of destination cost
        //step 2: iterate through each edge that is not settled on that iterated node, add these nodes to unsettled.
        //step 3: add total destination cost to each of these neighbors (referring back to step 0)
        //step 4: add the iterated node to each neighbor's previous node table.
        //step 5: add the iterated node to settled
        //step 6: step 1 - 5 until all nodes are settled
        //step 7: create a list of nodes to get the order of nodes to travel through.
        //step 7.1: add the destination node to the list
        //step 8: check the node that's last in the list to check it's previous node and add it to the list
        //step 9: step 8 until reached the origin
        //step 10: return the list.

        return null;
    }
}
