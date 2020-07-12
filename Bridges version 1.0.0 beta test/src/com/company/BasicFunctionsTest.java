package com.company;

import java.util.HashMap;

import static org.junit.Assert.*;

public class BasicFunctionsTest {
    BasicFunctions z;
    HashMap<Integer, Integer> bridges;
    int a = 1, b = 2;
    @org.junit.Before
    public void setUp() throws Exception {
        z = new BasicFunctions();
        bridges = new HashMap<Integer, Integer>();
    }

    @org.junit.Test
    public void addEdge() {
        z.addEdge(a, b);
        assertTrue(z.hasEdge(a,b));
    }

    @org.junit.Test
    public void addEdgeBridges() {
        a = 7;
        b = 8;
        int k = z.getBridgesNum();
        z.addEdgeBridges(a,b);
        assertEquals(z.getBridgesNum(), ++k);
    }

    @org.junit.Test
    public void deleteEdge() {
        a = 5;
        b = 6;
        z.addEdge(a, b);
        assertTrue(z.hasEdge(a,b));
        z.deleteEdge(a,b);
        assertFalse(z.hasEdge(a,b));
    }

}