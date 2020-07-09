package com.company;

import javax.swing.*;
import java.awt.*;
import java.lang.Object;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;

import static java.lang.Math.min;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class MainWindow {
    private HashMap<Integer, List<Integer>> vertexMap = new HashMap<Integer, List<Integer>>();
    private HashMap<Integer, Integer> bridges = new HashMap<Integer, Integer>();
    //вершины и их рёбра в vertexCommunication вершина - ключ, id ребра - значение
    HashMap<Integer, List<Integer>> vertexCommunication = new HashMap<Integer, List<Integer>>();
    private int time;
    private boolean[] used;
    private int[] fup;
    private int[] tin;

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
                        //System.out.println("Edge");
                        if(graph.isCellSelected(cell)){
                            onDel[tmp++] = cell;
                            //System.out.println(graph.isCellDeletable(cell));
                            //System.out.println(cell.toString());
                        }

                        //graph.removeSelectionCell();

                    }
                }
                graph.cellsRemoved(onDel);
                //graph.removeSelectionCells(graph.getSelectionCells());
                System.out.println(onDel.length);
                for (int i = 0; i < tmp; i++) {
                    onDel[i].setParent(null);
                }
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
                printBridges();
                printGraph();
                bridges.clear();
            }
        });
        graph.refresh();
        graph.getModel().endUpdate();
    }

    private void addVertex(int vertexName) {
        if (!hasVertex(vertexName)) {
            vertexMap.put(vertexName, new ArrayList<Integer>());
        }
    }

    private boolean hasVertex(int vertexName) {
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
        for (int i = 0; i < onDel.length; i++) {
            onDel[i].setParent(null);
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        graph = new mxGraph();
        mxGraphComponent1 = new mxGraphComponent(graph);



    }
}
