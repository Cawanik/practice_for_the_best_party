package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileWriter;
import java.lang.Object;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.min;
import com.mxgraph.io.mxCodec;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.handler.mxConnectionHandler;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class MainWindow {
    private JPanel fld;
    private JButton stepByStepButton;
    private JButton delEdge;
    private JButton delBridges;
    private JButton findBridges;
    private JButton saveAs;
    private JLabel numOfBridges;
    public JPanel panelGraph;
    private mxGraphComponent mxGraphComponent1;
    private JButton prevButton;
    private JButton nextButton;
    private static mxGraph graph;
    private static Object parent;
    private int maxStep;
    static JFrame dia;
    private int flagState = 0;
    static int whatPush = 0;
    MainWindow(String a) {

        //Установка стилей
        setStyleGraph();
        //------------------------------------------
        try
        {
            Document document = mxXmlUtils.parseXml(mxUtils.readFile(a));
            System.out.println(mxUtils.readFile(a));
            mxCodec codec = new mxCodec(document);
            codec.decode(document.getDocumentElement(), graph.getModel());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(dia, "Файл поврежден", "Ошибка", JOptionPane.ERROR_MESSAGE);
            dia.getDefaultCloseOperation();
        }

        parent = graph.getDefaultParent();
        //обнуление стилей
        Object[] tmpCells = graph.getChildCells(parent);
        for (int i = 0; i < tmpCells.length; i++){
            mxCell FTS = (mxCell) tmpCells[i];
            FTS.setStyle("");

        }
        //------------------------------------------

        setAction();
        JFrame dia = new JFrame("Граф");
        dia.setResizable(false);
        dia.setLocationRelativeTo(null);
        dia.setContentPane(fld);
        dia.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dia.pack();
        dia.setVisible(true);



    }

    MainWindow(int a) {
        //НАЧАЛО ГРАФА
        //Установка стилей
        setStyleGraph();
        //---------------------------------
        //Вставка вершин


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
        //---------------------------------

        //КОНЕЦ ГРАФА

        setAction();
        JFrame dia = new JFrame("Граф");
        dia.setResizable(false);
        dia.setLocationRelativeTo(null);
        dia.setContentPane(fld);
        dia.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dia.pack();
        dia.setVisible(true);


    }

    private  void setStyleGraph(){
        mxStylesheet stl = new mxStylesheet();
        Map<String,Object> bridgeEdge = new HashMap<>();
        bridgeEdge.put(mxConstants.STYLE_STROKECOLOR, "#0000FF");
        bridgeEdge.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
        bridgeEdge.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        bridgeEdge.put(mxConstants.STYLE_FONTCOLOR, "#446299");
        bridgeEdge.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        bridgeEdge.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        mxStyleRegistry.putValue("DFSEdge", bridgeEdge);
        stl.putCellStyle("DFSEdge", bridgeEdge);

        Map<String,Object> isStepOf = new HashMap<>();
        isStepOf.put(mxConstants.STYLE_STROKECOLOR, "#FF0000");
        isStepOf.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
        isStepOf.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        isStepOf.put(mxConstants.STYLE_FONTCOLOR, "#446299");
        isStepOf.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        isStepOf.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        mxStyleRegistry.putValue("bridgeEdge", isStepOf);
        stl.putCellStyle("bridgeEdge", isStepOf);

        Map<String,Object> bridgeVertex = new HashMap<>();
        bridgeVertex.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_RECTANGLE);
        bridgeVertex.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        bridgeVertex.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        bridgeVertex.put(mxConstants.STYLE_STROKECOLOR, "#64FFB9");
        bridgeVertex.put(mxConstants.STYLE_FILLCOLOR, "#FFFFFF");
        bridgeVertex.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        bridgeVertex.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        mxStyleRegistry.putValue("bridgeVertex", bridgeVertex);
        stl.putCellStyle("bridgeVertex",bridgeVertex);

        var styleTmp = stl.getDefaultVertexStyle();
        styleTmp.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE) ;
        styleTmp.put(mxConstants.STYLE_FONTCOLOR, "#000000");



        var styleTmp1 = stl.getDefaultEdgeStyle();
        styleTmp1.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        styleTmp1.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
        new mxCircleLayout(graph).execute(graph.getDefaultParent());

        new mxParallelEdgeLayout(graph).execute(graph.getDefaultParent());

        graph.setStylesheet(stl);
        System.out.println(graph.getStylesheet().getStyles());
        graph.setAllowDanglingEdges(false);


    }

    public static void setDFSEdge(Object[] masOfEdge){
        try {

            for (int i = 0; i < masOfEdge.length; i++) {
                if(masOfEdge[i]==null) continue;
                mxCell a = (mxCell) masOfEdge[i];
                if(a.isEdge())
                    a.setStyle("DFSEdge");
            }
            graph.repaint();
            graph.refresh();
            System.out.println(mxStyleRegistry.getValue("DFSEdge"));
        }catch (Exception e){
            JOptionPane.showMessageDialog(dia, "Невозможно присвоить стиль 'DFSEdge'", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void setBridgeEdge(Object[] masOfEdge){
        try {

            for (int i = 0; i < masOfEdge.length; i++) {
                if(masOfEdge[i]==null) continue;
                mxCell a = (mxCell) masOfEdge[i];
                if(a.isEdge())
                    a.setStyle("bridgeEdge");
            }
            graph.repaint();
            graph.refresh();
            System.out.println(mxStyleRegistry.getValue("bridgeEdge"));
        }catch (Exception e){
            JOptionPane.showMessageDialog(dia, "Невозможно присвоить стиль 'bridgeEdge'", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void setDefaultEdge(Object[] masOfEdge){
        try {

            for (int i = 0; i < masOfEdge.length; i++) {
                if(masOfEdge[i]==null) continue;
                mxCell a = (mxCell) masOfEdge[i];
                if(a.isEdge())
                    a.setStyle("");
            }
            graph.repaint();
            graph.refresh();
            System.out.println(mxStyleRegistry.getValue(""));
        }catch (Exception e){
            JOptionPane.showMessageDialog(dia, "Невозможно присвоить стандартный стиль ", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void setBridgesVertex(Object[] masOfVertex){
        try {

            for (int i = 0; i < masOfVertex.length; i++) {
                if(masOfVertex[i]==null) continue;
                mxCell a = (mxCell) masOfVertex[i];
                if(a.isVertex())
                    a.setStyle("bridgeVertex");
            }
            graph.repaint();
            graph.refresh();
            System.out.println(mxStyleRegistry.getValue("bridgeVertex"));
        }catch (Exception e){
            JOptionPane.showMessageDialog(dia, "Невозможно присвоить стиль 'bridgeVertex'", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void setDefaultVertex(Object[] masOfVertex){
        try {

            for (int i = 0; i < masOfVertex.length; i++) {
                if(masOfVertex[i]==null) continue;
                mxCell a = (mxCell) masOfVertex[i];
                if(a.isVertex())
                    a.setStyle("");
            }
            graph.repaint();
            graph.refresh();
            System.out.println(mxStyleRegistry.getValue(""));
        }catch (Exception e){
            JOptionPane.showMessageDialog(dia, "Невозможно присвоить стандартный стиль", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Object parent(){
        return parent;
    }

    public static mxGraph getGraph(){
        return graph;
    }

    private void setDFSSelectStyle(Object[] edges){
        int tmp = whatPush;
        Object[] toPaint = new Object[tmp];
        int i = 0;
        System.out.println();
        while(tmp > 0 && i < edges.length){
            mxCell FTS = (mxCell) edges[i];
            if(edges[i] == null || !FTS.isVertex()){
                System.out.println(edges[i]);
                i++;


            }else {
                System.out.println(edges[i]);
                toPaint[--tmp] = edges[i++];
            }
        }

        setBridgesVertex(toPaint);
    }
    private void setAction(){
        graph.getModel().beginUpdate();

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {


                    whatPush++;
                    if (whatPush < maxStep) {
                        nextButton.setEnabled(true);
                    } else {
                        nextButton.setEnabled(false);
                    }
                    if(whatPush  == maxStep){
                        setDefaultEdge(graph.getChildCells(parent));
                        BasicFunctions f = new BasicFunctions();
                        f.findBridgesButton();
                        setBridgeEdge(f.bridgesGraphics);
                        Arrays.fill(f.bridgesGraphics, null);
                    }
                    prevButton.setEnabled(true);
                    setDFSSelectStyle(BasicFunctions.bridgesState);
                }
                catch (Exception exc){
                    JOptionPane.showMessageDialog(dia, "Невозможно сделать шаг вперед", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    whatPush--;
                    if (whatPush > 0) {
                        prevButton.setEnabled(true);
                    } else {
                        prevButton.setEnabled(false);
                    }
                    nextButton.setEnabled(true);

                    graph.setCellStyle("", graph.getChildCells(parent));
                    setDFSSelectStyle(BasicFunctions.bridgesState);
                }
                catch (Exception exc){
                    JOptionPane.showMessageDialog(dia, "Невозможно сделать шаг назад", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        stepByStepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(flagState == 0) {
                    nextButton.setEnabled(true);
                    prevButton.setEnabled(false);
                    delBridges.setEnabled(false);
                    findBridges.setEnabled(false);
                    delEdge.setEnabled(false);
                    stepByStepButton.setText("Обычный режим");
                    mxGraphComponent1.setConnectable(false);
                    graph.setCellStyle("",graph.getChildCells(parent));
                    flagState++;
                    BasicFunctions f = new BasicFunctions();
                    f.stepGo();
                    maxStep = BasicFunctions.bridgesState.length + 1;
                    System.out.println("Max step = " + BasicFunctions.bridgesState.length);

                }else{
                    whatPush = 0;
                    nextButton.setEnabled(false);
                    prevButton.setEnabled(false);
                    delBridges.setEnabled(true);
                    stepByStepButton.setText("Пошаговый режим");
                    findBridges.setEnabled(true);
                    delEdge.setEnabled(true);
                    mxGraphComponent1.setConnectable(true);
                    graph.setCellStyle("",graph.getChildCells(parent));
                    flagState--;
                }
                //graph.isResetEdgesOnConnect();

                //graph.;
            }
        });
        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mxCodec encoder = new mxCodec();
                mxGraph saveGraph = new mxGraph();


                Node node = encoder.encode(graph.getModel());
                String a = mxXmlUtils.getXml(node);
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Сохранение файла");
                // Определение режима - только файл
                FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
                fileChooser.setFileFilter(filter);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showSaveDialog(fld);
                // Если файл выбран, то представим его в сообщении

                if (result == JFileChooser.APPROVE_OPTION ) {
                        try {

                            FileWriter myWriter;
                            if(fileChooser.getSelectedFile().getPath().endsWith(".xml")){
                                myWriter = new FileWriter(fileChooser.getSelectedFile().getPath());
                            }else{
                                File fl = new File(fileChooser.getSelectedFile().getPath() + ".xml");
                                myWriter = new FileWriter(fl);
                            }
                            myWriter.write(a);
                            myWriter.close();
                            System.out.println("Successfully wrote to the file.");
                        } catch (Exception q) {
                            JOptionPane.showMessageDialog(dia, "Файл невозможно сохранить", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                }
            }
        });
        findBridges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    setDefaultEdge(graph.getChildCells(parent));
                    BasicFunctions f = new BasicFunctions();
                    f.findBridgesButton();
                    setBridgeEdge(f.bridgesGraphics);
                    Arrays.fill(f.bridgesGraphics, null);
                    //for (int i = 0; i < f.bridgesGraphics.length; i++)
                    numOfBridges.setText("Количество мостов: " + f.getBridgesNum());
                }
                catch (Exception exc){
                    JOptionPane.showMessageDialog(dia, "Невозможно найти мосты", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    graph.setCellStyle("");
                }

            }
        });
        delEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BasicFunctions f = new BasicFunctions();
                    f.deleteEdgeButton();
                }
                catch (Exception exc){
                    JOptionPane.showMessageDialog(dia, "Невозможно удалить ребро", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        delBridges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BasicFunctions f = new BasicFunctions();
                    f.deleteBridgesButton();
                }
                catch (Exception exc){
                    JOptionPane.showMessageDialog(dia, "Невозможно удалить мосты", "Ошибка", JOptionPane.ERROR_MESSAGE);
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