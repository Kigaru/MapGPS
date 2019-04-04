package sample;

//import kotlin.collections.EmptyList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scala.collection.immutable.List;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    Graph graph = new Graph();
    Node a, b, c, d, e;
    Edge ab1, ac2, bc3, ec4, bd5, de6;
    Graph complexGraph = new Graph();
    Node u,v,w,x,y,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,one;
    Edge uv20, uw4, ux5, uf3, vy5, wx4, xy42,fg3,gh5,gj55,hi5,hk11,hl13,ji1,jk11,ik4,kl8,km2,kn4,ko5,kp11,lm7,lt14,mn9,nq5,po6,oq13,qr6,rs5,st3;



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
        ec4 = new Edge(e, c, 4);
        bd5 = new Edge(b, d, 5);
        de6 = new Edge(d, e, 6);
    }

    @BeforeEach
    void moreComplexGraph() {
        u = new Node("a", 0,0);
        v = new Node("b", 1,1);
        w = new Node("c", 2,2);
        x = new Node("d", 3,3);
        y = new Node("e", 4,4);
        f = new Node("f", 5,5);
        g = new Node("g", 6,6);
        h = new Node("h", 7,7);
        i = new Node("i", 8,8);
        j = new Node("j", 9,9);
        k = new Node("k", 10,10);
        l = new Node("l", 11,11);
        m = new Node("m", 12,12);
        n = new Node("n", 13,13);
        o = new Node("o", 14,14);
        p = new Node("p", 15,15);
        q = new Node("q", 16,16);
        r = new Node("r", 17,17);
        s = new Node("s", 18,18);
        t = new Node("t", 19,19);
        complexGraph.addAllNodes(u,v,w,x,y,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t);

        uv20 = new Edge(u, v, 20);
        uw4 = new Edge(u, w, 4);
        ux5 = new Edge(u, x, 5);
        uf3 = new Edge(u, f, 3);
        vy5 = new Edge(v, y, 5);
        wx4 = new Edge(w, x, 4);
        xy42 = new Edge(x, y, 42);
        fg3 = new Edge(f, g, 3);
        gh5 = new Edge(g, h, 5);
        gj55 = new Edge(g, j, 55);
        hi5 = new Edge(h, i, 5);
        hk11 = new Edge(h,k , 11);
        hl13 = new Edge(h, l, 13);
        ji1 = new Edge(j, i, 1);
        jk11 = new Edge(j,k , 11);
        ik4 = new Edge(i, k, 4);
        kl8 = new Edge(k, l, 8);
        km2 = new Edge(k, m, 2);
        kn4 = new Edge(k, n, 4);
        ko5 = new Edge(k, o,5);
        kp11 = new Edge(k, p,11);
        lm7 = new Edge(l, m,7);
        lt14 = new Edge(l, t, 14);
        mn9 = new Edge(m, n, 9);
        nq5 = new Edge(n, q, 5);
        po6 = new Edge(p, o, 6);
        oq13 = new Edge(o,q , 13);
        qr6 = new Edge(q, r, 6);
        rs5 = new Edge(r, s,5 );
        st3 = new Edge(s,t , 3);
    }

    @Test
    void oneNodeGraph() {
     one = new Node("single city", 0,0);
     Graph singleGraph = new Graph();
     singleGraph.addNode(one);
     assertTrue(singleGraph.dijkstra(one,one).isEmpty());
    }

    @Test
    void dijkstraReturnsBestRoute() {
        LinkedList<Edge> route = new LinkedList<Edge>();
        route.add(ac2);
        route.add(ec4);

        assertTrue(graph.dijkstra(a, e).equals(route));
    }

    @Test
    void dijkstraComplexMap() {
        LinkedList<Edge> route = new LinkedList<>();
        route.add(rs5);
        route.add(qr6);
        route.add(nq5);
        route.add(kn4);
        route.add(ik4);
        route.add(hi5);
        route.add(gh5);
        route.add(fg3);
        route.add(uf3);
        route.add(uv20);
        route.add(vy5);

        assertTrue(complexGraph.dijkstra(s,y).equals(route));
    }
}