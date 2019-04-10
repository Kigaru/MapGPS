package sample;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NodeEdgeTest {
    ArrayList<Node> nodes;

    @BeforeEach
    public void fillNodes(){
        nodes = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            nodes.add(new Node(String.valueOf(i), 0, 0));
        }
    }

    @Test
    public void neighboursMatch(){
        Node mainNode = new Node("main", 0, 0);

        for (int i = 0; i < 100; i++) new Edge(mainNode, nodes.get(i), i);

        assertEquals(100, mainNode.getNeighbors().size());
        assertEquals(100, mainNode.getEdges().size());
    }

    @Test
    public void neighboursOfaNeighbourMatch(){
        Node firstNode = new Node("first", 0, 0);
        Node secondNode = new Node("second", 0, 0);

        new Edge(firstNode, secondNode, 1);
        for (int i = 0; i < 100; i++) new Edge(firstNode.getNeighbors().get(0), nodes.get(i), i);

        assertEquals(101, secondNode.getNeighbors().size());
    }





}