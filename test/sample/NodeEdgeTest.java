package sample;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NodeEdgeTest {
    ArrayList<Node> nodes;

    @Before
    public void fillNodes(){
        nodes = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            nodes.add(new Node(String.valueOf(i)));
        }
    }

    @Test
    public void neighboursMatch(){
        Node mainNode = new Node("main");

        for (int i = 0; i < 100; i++) mainNode.addEdge(new Edge(mainNode, nodes.get(i)));

        assertEquals(100, mainNode.getNeighbors().size());
    }

}