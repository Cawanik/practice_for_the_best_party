package com.company;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import java.lang.reflect.Array;
import java.util.Arrays;

public class dfsFind {
    private Object[] objectGraph;
    private mxCell[] notColoredVertex;
    private mxCell[] ColoredVertex;
    private mxCell[] notColoredEdge;
    private mxCell[] ColoredEdge;

    dfsFind(mxGraph graph){
        objectGraph = graph.getChildCells(MainWindow.parent());

        notColoredVertex = new mxCell[objectGraph.length];
        Arrays.fill(notColoredVertex, null);

        notColoredEdge = new mxCell[objectGraph.length];
        Arrays.fill(notColoredEdge, null);

        ColoredEdge = new mxCell[objectGraph.length];
        Arrays.fill(notColoredEdge, null);

        ColoredVertex = new mxCell[objectGraph.length];
        Arrays.fill(notColoredEdge, null);

        initNotColored();
    }
    private void initNotColored(){
        int ncv = 0, nce = 0;
        for (int i = 0; i < objectGraph.length; i++){
            mxCell cell = (mxCell) objectGraph[i];
            if(cell.isVertex()){
                notColoredVertex[ncv++] = cell;
            }else{
                notColoredEdge[nce++] = cell;
            }
        }
    }
    public void dfsStart(){
        boolean haveNotColoredVertex = true;
        while (haveNotColoredVertex){
            haveNotColoredVertex = false;
            for(int i = 0; i < notColoredVertex.length; i++){
                if(notColoredVertex[i] != null){
                    haveNotColoredVertex = true;
                    notColoredVertex[i] = null;
                    dfs(notColoredVertex[i]);
                }
            }
        }
    }
    private void dfs(mxCell a){
        if(a.isVertex()){
            System.out.println(a.getValue());

            System.out.println();
            a.getEdgeCount();
        }
    }

}
