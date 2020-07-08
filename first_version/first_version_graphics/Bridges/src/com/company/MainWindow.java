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

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class MainWindow {
    private JPanel fld;
    private JButton addEdge;
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


    }
    MainWindow(int a) {
        //НАЧАЛО ГРАФА
        mxStylesheet stl = new mxStylesheet();

        var styletmp = graph.getStylesheet().getDefaultVertexStyle();
        styletmp.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE) ;
        styletmp.put(mxConstants.STYLE_FONTCOLOR, "#000000");


        //stl.setDefaultVertexStyle(styletmp);

        //styletmp = graph.getStylesheet().getDefaultEdgeStyle();
        //styletmp.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_LINE);

        stl.setDefaultVertexStyle(styletmp);

        graph.setStylesheet(stl);
        System.out.println("Стиль graph:");
        System.out.println(graph.getStylesheet().getStyles());

        graph.getModel().beginUpdate();
        Object parent = graph.getDefaultParent();
        Object cell;
        Object cell2;
        cell = graph.insertVertex(parent, "1", "sfga", 30, 80, 100, 100);
        cell2 = graph.insertVertex(parent, "2", "qqqq",11,11,100,100);
        Object allCells[] = graph.getChildCells(parent);
        for(int i = 0; i < allCells.length; i++){
            System.out.println(allCells[i]);
        }

        graph.getModel().endUpdate();
        //КОНЕЦ ГРАФА
        tmpPan = new JPanel();

        graph = new mxGraph();

        createUIComponents();
        JFrame dia = new JFrame("dsfsdfs");
        dia.setContentPane(fld);
        dia.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dia.pack();
        dia.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        graph = new mxGraph();
        mxGraphComponent1 = new mxGraphComponent(graph);



    }
}
