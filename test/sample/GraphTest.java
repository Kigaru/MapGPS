package sample;

//import kotlin.collections.EmptyList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    Graph graph = new Graph();
    Node a, b, c, d, e;
    Edge ab1, ac2, bc3, ce4, bd5, de6;

    @BeforeEach
    void prepareGraph(){
        a = new Node("a", 0, 0);
        b = new Node("b", 1, 1);
        c = new Node("c", 2, 2);
        d = new Node("d", 3, 3);
        e = new Node("e", 4, 4);
        graph.addAllNodes(a, b, c, d, e);

        ab1 = new Edge(a, b, 1);
        ac2 = new Edge(a, c, 2);
        bc3 = new Edge(b, c, 3);
        ce4 = new Edge(c, e, 4);
        bd5 = new Edge(b, d, 5);
        de6 = new Edge(d, e, 6);
    }

    @Test
    void dijkstraReturnsBestRoute() {
        LinkedList<Edge> route = new LinkedList<Edge>();
        route.add(ac2);
        route.add(ce4);

        assertTrue(graph.dijkstra(a, e).equals(route));
    }
}