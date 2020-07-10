package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.FileWriter;
import java.lang.Object;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;

import static com.mxgraph.util.mxXmlUtils.getXml;
import static com.mxgraph.util.mxXmlUtils.parseXml;
import static java.lang.Math.min;
import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class MainWindow {
    public HashMap<Integer, List<Integer>> vertexMap = new HashMap<Integer, List<Integer>>();
    public HashMap<Integer, Integer> bridges = new HashMap<Integer, Integer>();
    //вершины и их рёбра в vertexCommunication вершина - ключ, id ребра - значение
    public HashMap<Integer, List<Integer>> vertexCommunication = new HashMap<Integer, List<Integer>>();
    public int time;
    public int rememberLength;
    public boolean[] used;
    public int[] fup;
    public int[] tin;

    private JPanel fld;
    private JButton addVertexButton;
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
    JFrame dia;
    JFrame wnd;

    MainWindow(String a) {

        //Установка стилей
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
        System.out.println(parent);
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
            ex.printStackTrace();
        }
        parent = graph.getDefaultParent();
        setAction();
        JFrame dia = new JFrame("dsfsdfs");
        dia.setResizable(false);
        dia.setContentPane(fld);
        dia.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dia.pack();
        dia.setVisible(true);

    }
    MainWindow(int a) {
        //НАЧАЛО ГРАФА
        //Установка стилей
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
        graph.setAllowDanglingEdges(false);
        //КОНЕЦ ГРАФА

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
        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mxCodec encoder = new mxCodec();
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
                            System.out.println("An error occurred.");
                            q.printStackTrace();
                        }
                }
            }
        });
        delEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] edgesOfVertex;
                Object[] q = graph.getChildCells(parent);
                for(int i = 0; i < q.length; i++){
                    mxCell tmp = (mxCell) q[i];
                    if(tmp.isVertex()) {
                        //создаём имя вершины
                        int vertex = Integer.parseInt(tmp.getId()) - 1;
                        addVertex(vertex);
                        //создаём список для каждой вершины, туда поместим рёбра
                        vertexCommunication.put(vertex, new ArrayList<Integer>());
                        //получаем рёбра
                        edgesOfVertex = graph.getEdges(tmp);
                        for (int f = 0; f < edgesOfVertex.length;f++){
                            mxCell tmp2 = (mxCell) edgesOfVertex[f];
                            //создаём имя ребра
                            int edge = Integer.parseInt(tmp2.getId());
                            //переменный массив рёбер для добавления
                            List<Integer> edges = vertexCommunication.get(vertex);
                            //добавим ребро в промежуточный список и отсортируем
                            edges.add(edge);
                            Collections.sort(edges);
                            //добавим полученный список в карту
                            vertexCommunication.put(vertex, edges);
                        }
                        //System.out.println(vertexCommunication);
                        //System.out.println();
                    }
                }

                //создание списка смежности
                for (Map.Entry<Integer, List<Integer>> entry : vertexCommunication.entrySet()) {
                    //текущий список рёбер
                    List<Integer> tmp = entry.getValue();
                    for (Map.Entry<Integer, List<Integer>> entry2 : vertexCommunication.entrySet()) {
                        //необходимо пройтись по списку, чтобы узнать какие рёбра являются общими
                        for (int i = 0; i < tmp.size(); i++) {
                            //если ключи не равны и ребро содержится в другом списке
                            if (!entry2.getKey().equals(entry.getKey()) && entry2.getValue().contains(tmp.get(i))) {
                                addVertex(entry.getKey());
                                if (vertexMap.get(entry.getKey()).contains(entry2.getKey())) {
                                    continue;
                                }
                                addEdge(entry.getKey(), entry2.getKey());
                            }
                        }
                    }
                }
                System.out.println(vertexMap);
                q= graph.getChildCells(parent);
                mxCell[] onDel = new mxCell[q.length];
                int tmp = 0;
                System.out.println(q.length);

                for(Object c: q){

                    mxCell cell = (mxCell) c;

                    if(cell.isVertex()) {
                        System.out.println(c.toString());
                    }
                    else {
                        if(graph.isCellSelected(cell)){
                            onDel[tmp++] = cell;
                            int id = Integer.parseInt(cell.getId());
                            for(Map.Entry<Integer, List<Integer>> info : vertexCommunication.entrySet()) {
                                for(Map.Entry<Integer, List<Integer>> info2 : vertexCommunication.entrySet()) {
                                    if (info.getValue().contains(id) && info2.getValue().contains(id) &&
                                            !info2.getKey().equals(info.getKey())) {
                                        vertexMap.get(info2.getKey()).remove(info.getKey());
                                        vertexMap.get(info.getKey()).remove(info2.getKey());
                                        printGraph();
                                    }
                                    //System.out.println(vertexCommunication);
                                }
                            }
                        }
                    }
                }
                graph.cellsRemoved(onDel);
                for (int i = 0; i < tmp; i++) {
                    onDel[i].setParent(null);
                }
                printGraph();
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
                        //создаём имя вершины
                        int vertex = Integer.parseInt(tmp.getId()) - 1;
                        //создаём список для каждой вершины, туда поместим рёбра
                        vertexCommunication.put(vertex, new ArrayList<Integer>());
                        //получаем рёбра
                        edgesOfVertex = graph.getEdges(tmp);
                        for (int f = 0; f < edgesOfVertex.length;f++){
                            mxCell tmp2 = (mxCell) edgesOfVertex[f];
                            //создаём имя ребра
                            int edge = Integer.parseInt(tmp2.getId());
                            //переменный массив рёбер для добавления
                            List<Integer> edges = vertexCommunication.get(vertex);
                            //добавим ребро в промежуточный список и отсортируем
                            edges.add(edge);
                            Collections.sort(edges);
                            //добавим полученный список в карту
                            vertexCommunication.put(vertex, edges);
                        }
                        //System.out.println(vertexCommunication);
                        //System.out.println();
                    }
                }

                //создание списка смежности
                for (Map.Entry<Integer, List<Integer>> entry : vertexCommunication.entrySet()) {
                    //текущий список рёбер
                    List<Integer> tmp = entry.getValue();
                    for (Map.Entry<Integer, List<Integer>> entry2 : vertexCommunication.entrySet()) {
                        //необходимо пройтись по списку, чтобы узнать какие рёбра являются общими
                        for (int i = 0; i < tmp.size(); i++) {
                            //если ключи не равны и ребро содержится в другом списке
                            if (!entry2.getKey().equals(entry.getKey()) && entry2.getValue().contains(tmp.get(i))) {
                                addVertex(entry.getKey());
                                if (vertexMap.get(entry.getKey()).contains(entry2.getKey())) {
                                    continue;
                                }
                                addEdge(entry.getKey(), entry2.getKey());
                            }
                        }

                    }
                }
                printGraph();
                deleteBridges();
                deleteBridges();
                printBridges();
                printGraph();
                rememberLength = 0;
                bridges.clear();
            }
        });
        graph.refresh();
        graph.getModel().endUpdate();
    }

    public void addVertex(int vertexName) {
        if (!hasVertex(vertexName)) {
            vertexMap.put(vertexName, new ArrayList<Integer>());
        }
    }

    public boolean hasVertex(int vertexName) {
        return vertexMap.containsKey(vertexName);
    }

    public void addEdge(Integer vertexName1, Integer vertexName2) {
        if (!hasVertex(vertexName1))
            addVertex(vertexName1);
        if (!hasVertex(vertexName2))
            addVertex(vertexName2);
        List<Integer> edges1 = vertexMap.get(vertexName1);
        List<Integer> edges2 = vertexMap.get(vertexName2);
        edges1.add(vertexName2);
        edges2.add(vertexName1);
        Collections.sort(edges1);
        Collections.sort(edges2);
    }

    public void printGraph() {
        System.out.println("Graph is: ");
        System.out.println(vertexMap);
    }

    public void printBridges() {
        System.out.println("Bridges is: ");
        System.out.println(bridges);
    }

    public boolean hasEdge(int vertexName1, int vertexName2) {
        if (!hasVertex(vertexName1))
            return false;
        List<Integer> edges = vertexMap.get(vertexName1);
        return Collections.binarySearch(edges, vertexName2) != -1;
    }

    public void deleteEdge(Integer vertexName1, Integer vertexName2) {
        if (!hasEdge(vertexName1, vertexName2))
            return;
        if (!hasEdge(vertexName2, vertexName1))
            return;
        List<Integer> edges1 = vertexMap.get(vertexName1);
        List<Integer> edges2 = vertexMap.get(vertexName2);
        edges1.remove(vertexName2);
        edges2.remove(vertexName1);
        Collections.sort(edges1);
        Collections.sort(edges2);

    }

    public void dfsBridges(int v, int p) {
        used[v] = true;
        tin[v] = fup[v] = time++;
        List<Integer> adjacentVertexes = vertexMap.get(v);
        for (int i = 0; i < adjacentVertexes.size(); i++) {
            int to = adjacentVertexes.get(i);
            if (to == p) {
                continue;
            }
            if (used[to]) {
                fup[v] = min(fup[v], tin[to]);
            }
            else {
                dfsBridges(to, v);
                fup[v] = min(fup[v], fup[to]);
                if (fup[to] > tin[v]) {
                    bridges.put(v, to);
                }
            }
        }
    }
public HashMap<Integer, Integer> getBridges(){
        return bridges;
}
    public void findBridges() {
        used = new boolean[vertexMap.size() + 1];
        fup = new int[vertexMap.size() + 1];
        tin = new int[vertexMap.size() + 1];
        for (int i = 1; i <= vertexMap.size(); i++) {
            used[i] = false;
        }
        for (int i = 1; i <= vertexMap.size(); i++) {
            if (!used[i]) {
                dfsBridges(i, -1);
            }
        }
    }

    public void deleteBridges() {
        findBridges();
        for (Map.Entry<Integer, Integer> info : bridges.entrySet()) {
            deleteEdge(info.getKey(), info.getValue());
        }//удаляем из графа
        Object[] q= graph.getChildCells(parent);
        mxCell[] onDel = new mxCell[bridges.size()];
        int tmp = 0;
        System.out.println(q.length);
        for(Object c: q){
            mxCell cell = (mxCell) c;
            if(cell.isEdge()) {
                int id = Integer.parseInt(cell.getId());
                for(Map.Entry<Integer, Integer> info : bridges.entrySet()) {
                    if (vertexCommunication.get(info.getKey()).contains(id) &&
                            vertexCommunication.get(info.getValue()).contains(id)) {
                        onDel[tmp++] = cell;
                        //cell.removeFromParent();
                    }
                    System.out.println(vertexCommunication);
                }
            }
        }
        graph.cellsRemoved(onDel);
        for (int i = 0; i < onDel.length - rememberLength; i++) {
            onDel[i].setParent(null);
        }
        rememberLength = onDel.length;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        graph = new mxGraph();
        mxGraphComponent1 = new mxGraphComponent(graph);



    }
}