package com.company;

import javax.swing.*;
import java.awt.*;
import java.lang.Object;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class MainWindow {
    private JPanel fld;
    private JButton addVertex;
    private JButton delEdge;
    private JButton delBridges;
    private JButton findBridges;
    private JButton saveAs;
    private JLabel numOfCon;
    private JLabel numOfBridges;
    public JPanel panelGraph;
    private mxGraphComponent mxGraphComponent1;
    private mxGraph graph;
    private mxGraphComponent graphComponent;
    Object parent;
    JPanel tmpPan;
    JFrame dia;
    JFrame wnd;
    MainWindow(String a) {
        graph = new mxGraph();

        graphComponent.setPreferredSize(new Dimension(500, 500));
        panelGraph.add(graphComponent);
        Object parent = graph.getDefaultParent();
        graph.insertVertex(parent, null, "TEST", 30, 80, 100, 50);
        graph.getModel().endUpdate();
        setAction();



    }
    MainWindow(int a) {
        //НАЧАЛО ГРАФА
        mxStylesheet stl = new mxStylesheet();

        var styleTmp = graph.getStylesheet().getDefaultVertexStyle();
        styleTmp.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE) ;
        styleTmp.put(mxConstants.STYLE_FONTCOLOR, "#000000");


        stl.setDefaultVertexStyle(styleTmp);

        styleTmp = graph.getStylesheet().getDefaultEdgeStyle();
        styleTmp.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);

        stl.setDefaultEdgeStyle(styleTmp);

        graph.setStylesheet(stl);
        graph.getStylesheet();
        System.out.println("Стиль graph:");
        System.out.println(graph.getStylesheet().getStyles());

        graph.getModel().beginUpdate();
        parent = graph.getDefaultParent();
        Object cell;
        Object cell2;
        for(int i = 0; i < a; i++){
            graph.insertVertex(parent, null, i+1, 30, 80, 50, 50);
        }

        Object allCells[] = graph.getChildCells(parent);
        for(int i = 0; i < allCells.length; i++){
            System.out.println(allCells[i]);
        }

        graph.getModel().endUpdate();
        graph.setAllowDanglingEdges(false);
        //КОНЕЦ ГРАФА
        tmpPan = new JPanel();

        setAction();
        JFrame dia = new JFrame("dsfsdfs");
        dia.setResizable(false);
        dia.setContentPane(fld);
        dia.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dia.pack();
        dia.setVisible(true);

    }

    private void setAction(){
        graph.getModel().beginUpdate();
        delEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] q= graph.getChildCells(parent);
                mxCell[] onDel = new mxCell[q.length];
                int tmp = 0;
                System.out.println(q.length);
                for(Object c: q){

                    mxCell cell = (mxCell) c;

                    if(cell.isVertex()) {
                        System.out.println(c.toString());
                    }
                    else {
                        System.out.println("Edge");
                        if(graph.isCellSelected(cell)){
                            onDel[tmp++] = cell;
                            System.out.println(graph.isCellDeletable(cell));
                            System.out.println(cell.toString());
                        }

                        //graph.removeSelectionCell();

                    }
                }
                graph.cellsRemoved(onDel);
                //graph.removeSelectionCells(graph.getSelectionCells());

            }
        });
        delBridges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] edgesOfVertex;
                Object[] q = graph.getChildCells(parent);
                for(int i = 0; i < q.length; i++){
                    mxCell tmp = (mxCell) q[i];
                    if(tmp.isVertex()) {
                        edgesOfVertex = graph.getEdges(tmp);
                        for (int f = 0; f < edgesOfVertex.length;f++){
                            mxCell tmp2 = (mxCell) edgesOfVertex[f];

                            System.out.println(tmp2.getId());
                            System.out.println(edgesOfVertex[f].toString());
                        }
                        System.out.println();
                    }
                }
            }
        });




        graph.refresh();

        graph.getModel().endUpdate();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        graph = new mxGraph();
        mxGraphComponent1 = new mxGraphComponent(graph);



    }
}
