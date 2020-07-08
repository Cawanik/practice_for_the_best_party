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

        tmpPan = new JPanel();

        graph = new mxGraph();
        mxGraphComponent1.setGraph(graph);
        graphComponent = new mxGraphComponent(graph);
        graphComponent.setPreferredSize(new Dimension(500, 500));
        graph.getModel().beginUpdate();

        //panelGraph.add(graphComponent);


        Object parent = graph.getDefaultParent();
        graph.insertVertex(parent, null, "TEST", 30, 80, 100, 50);
        graph.getModel().endUpdate();

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



        var style = graph.getStylesheet().getDefaultVertexStyle();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE) ;
        mxStylesheet a = new mxStylesheet();
        a.setDefaultVertexStyle(style);

        System.out.println(a.getDefaultVertexStyle());


        graph.setStylesheet(a);
        graph.setCellStyles(mxConstants.STYLE_GLASS,"");
        System.out.println("Стиль graph:");
        System.out.println(graph.getStylesheet().getStyles());

        graph.getModel().beginUpdate();
        Object parent = graph.getDefaultParent();
        System.out.println(parent.toString());
        Object cell;
        Object cell2;
        cell = graph.insertVertex(parent, "1", "TEST", 30, 80, 100, 50);


        mxGraphComponent1.repaint();
        graph.getModel().endUpdate();

    }
}
