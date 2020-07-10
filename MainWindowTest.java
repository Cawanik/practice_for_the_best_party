package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class MainWindowTest {
    MainWindow z = new MainWindow("");
    HashMap<Integer, Integer>  bridges;
    int a = 1, b = 2;
    @org.junit.Before
    public void setUp() throws Exception {
        bridges = new HashMap<Integer, Integer>();
        //vertexMap = new HashMap<Integer, List<Integer>>();
    }

    @org.junit.Test
    public void addEdge() {
        //vertexMap.put(a, new ArrayList<Integer>());
        //z.addVertex
        //vertexMap.put(b, new ArrayList<Integer>());
        z.addEdge(a, b);
        assertTrue(z.hasEdge(a,b));
    }

    @org.junit.Test
    public void hasEdge() {
    }

    @org.junit.Test
    public void deleteEdge() {
        z.addEdge(a, b);
        assertTrue(z.hasEdge(a,b));
        z.deleteEdge(a,b);
        assertFalse(z.hasEdge(a,b));
    }

    @org.junit.Test
    public void findBridges() {
        z.addEdge(a,b);
        z.findBridges();
        assertTrue(bridges.isEmpty());
        //assertEquals(z.getBridges(), new HashMap<Integer, Integer>(a,b));
    }

    @org.junit.Test
    public void deleteBridges() {
        z.addEdge(a,b);
        z.deleteBridges();
        assertEquals(bridges.isEmpty(), null);
    }
}