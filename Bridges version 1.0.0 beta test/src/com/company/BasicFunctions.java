package com.company;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;
import java.util.*;

public class BasicFunctions {
    private static HashMap<Integer, List<Integer>> vertexMap = new HashMap<Integer, List<Integer>>(); //âåðøèí - ñïèñîê âåðøèí
    private static HashMap<Integer, List<Integer>> bridges = new HashMap<Integer, List<Integer>>(); //âåðøèíà - ñïèñîê âåðøèí
    private HashMap<Integer, List<Integer>> vertexCommunication = new HashMap<Integer, List<Integer>>(); //âåðøèíà - id ðžáåð
    public static Object[] bridgesGraphics;
    public static Object[] bridgesState;
    private boolean[] used;
    private List<Integer> vertexes;

    public void stepGo() {
        bridges.clear(); //î÷èùàåì êàðòó äëÿ ìîñòîâ
        vertexMap.clear();
        vertexCommunication.clear();
        initVertexCommunication();
        initVertexMap();
        printGraph();
        findDfs();
        bridgesState = new Object[vertexMap.size()];
        Arrays.fill(bridgesState,null);
        createBridgesState();
        //System.out.println(Arrays.toString(bridgesState));
        //System.out.println(Arrays.toString(bridgesState));
    }

    public void findBridgesButton(){
        bridges.clear(); //î÷èùàåì êàðòó äëÿ ìîñòîâ
        vertexMap.clear();
        vertexCommunication.clear();
        initVertexCommunication();
        initVertexMap();
        printGraph();
        findBridges();
        bridgesGraphics = new Object[vertexMap.size()];
        Arrays.fill(bridgesGraphics,null);
        createBridgeGraphics();
    }

    public void deleteBridgesButton(){
        bridges.clear(); //î÷èùàåì êàðòó äëÿ ìîñòîâ
        vertexMap.clear();
        vertexCommunication.clear();
        initVertexCommunication();
        initVertexMap();
        printGraph();
        deleteBridges();
        printBridges();
        printGraph();
        // î÷èùàåì êàðòó ìîñòîâ
    }

    public void deleteEdgeButton(){
        bridges.clear();
        vertexMap.clear();
        vertexCommunication.clear();
        initVertexCommunication();
        initVertexMap();
        delEdge();
        printGraph();
    }

    private void delEdge(){
        mxGraph graph = MainWindow.getGraph();
        Object[] q = graph.getChildCells(MainWindow.parent()); //áåðžì ýëåìåíòû ãðàôà
        mxCell[] onDel = new mxCell[q.length]; //ìàññèâ äëÿ óäàëåíèÿ
        int tmp = 0;
        for(Object c: q){
            mxCell cell = (mxCell) c;
            if (cell.isEdge()){
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
                        }
                    }
                }
            }
        }
        graph.cellsRemoved(onDel);
        for (int i = 0; i < onDel.length; i++) { //îòäåëÿåì óäàëžííûå ðžáðà îò ðîäèòåëÿ
            //System.out.println(onDel[i].toString());
            if(onDel[i]!=null)
                onDel[i].setParent(null);
        }
    }

    private void deleteBridges(){
        findBridges();
        if (bridges.isEmpty()) { //íåàäåêâàòíîå ïîâåäåíèå ôóíêöèè
            return;
        }
        for (Map.Entry<Integer, List<Integer>> info : bridges.entrySet()) {
            for (int i = 0; i < info.getValue().size(); i++) {
                deleteEdge(info.getKey(), info.getValue().get(i));
            }
        }//óäàëÿåì èç êàðòû ãðàôà
        mxGraph graph = MainWindow.getGraph();
        Object[] q = graph.getChildCells(MainWindow.parent()); //áåðžì ýëåìåíòû ãðàôà
        mxCell[] onDel = new mxCell[getBridgesNum() + 1]; //ìàññèâ äëÿ óäàëåíèÿ ýëåìåíòîâ ãðàôà
        int tmp = 0;
        for(Object c: q){ //ïðîõîäèìñÿ ïî âñåì ýëåìåíòàì
            mxCell cell = (mxCell) c;
            if(cell.isEdge()) {
                int id = Integer.parseInt(cell.getId());
                for(Map.Entry<Integer, List<Integer>> info : bridges.entrySet()) {
                    if (vertexCommunication.get(info.getKey()).contains(id)) { //åñëè âñïîìîãàòåëüíàÿ êàðòà ñîäåðæèò id ðåáðà
                        List<Integer> vertexes = info.getValue(); //áåðžì âñå âåðøèíû, â êîòîðûå èäžò ìîñò
                        for (int i = 0; i < vertexes.size(); i++) { //ïðîõîäèìñÿ ïî ýòèì âåðøèíàì
                            //åñëè â ìàññèâå âåðøèí åñòü id ðåáðà è íåò ïîâòîðÿþùèõñÿ ýëåìåíòîâ
                            if (vertexCommunication.get(vertexes.get(i)).contains(id) && !Arrays.asList(onDel).contains(cell)) {
                                onDel[tmp++] = cell; //äîáàâëÿåì ðåáðî â ìàññèâ íà óäàëåíèå
                            }
                        }
                    }
                }
            }
        }
        graph.cellsRemoved(onDel);
        for (int i = 0; i < onDel.length; i++) { //îòäåëÿåì óäàëžííûå ðžáðà îò ðîäèòåëÿ
            if(onDel[i]!= null)
                onDel[i].setParent(null);

        }
    }

    private void findBridges() {
        BasicFunctions.GraphCode g = new BasicFunctions.GraphCode(vertexMap.size() + 1); //ñîçäàžì ãðàô äëÿ ïîèñêà ìîñòîâ
        List<Integer> edges = new ArrayList<Integer>(); //âñïîìîãàòåëüíûé ñïèñîê ðžáåð
        for (Map.Entry<Integer, List<Integer>> entry : vertexMap.entrySet()){
            edges = entry.getValue();
            for (int i = 0; i < edges.size(); i++) { //çàïîëíåíèå ãðàôà ñóùåñòâóþùèìè ðžáðàìè
                g.edge(new BasicFunctions.Edge(entry.getKey(), edges.get(i)));
            }
        }
        int initialComponents = g.calcComponents();
        for (BasicFunctions.Edge e : g.getEdges()) {
            g.removeEdge(e);
            if (g.calcComponents() > initialComponents) {
                if (!hasVertexBridges(e.v)) {
                    bridges.put(e.v, new ArrayList<Integer>());
                }
                List <Integer> vertexes = bridges.get(e.v);
                if (!bridges.get(e.v).contains(e.u)) {
                    vertexes.add(e.u);
                    bridges.put(e.v, vertexes);
                }
            }
            g.edge(e);
        }

    }

    public int getBridgesNum (){
        findBridges();
        int counter = 0;
        for(Map.Entry<Integer, List<Integer>> info : bridges.entrySet()) {
            counter = counter + info.getValue().size();
            System.out.println();
        }
        return counter;
    }

    private void createBridgesState() {
        int size = 0;
        mxGraph graph = MainWindow.getGraph();
        Object[] q = graph.getChildCells(MainWindow.parent());
        for (int i = 0; i < q.length; i++) {
            mxCell tmp = (mxCell) q[i];
            if (tmp.isVertex()) {
                int vertex = Integer.parseInt(tmp.getId()) - 1;
                for (int j = 0; j < vertexes.size(); j++) {
                    //System.out.println("Vertexes " + vertexes.get(j));
                    if (vertex == vertexes.get(j)) {
                        bridgesState[j] = tmp;
                        System.out.println("j " + j + " tmp " + tmp);
                    }
                }
            }
        }
    }

    private void createBridgeGraphics() { //ñîçäàíèå ìàññèâà îáúåêòîâ ìîñòîâ äëÿ ðàáîòû â ãðàôå
        int size = 0;
        mxGraph graph = MainWindow.getGraph();
        Object[] q = graph.getChildCells(MainWindow.parent());
        for (int i = 0; i < q.length; i++) { //ïðîõîäèìñÿ ïî âñåì êîìïîíåíòàì ãðàôà
            mxCell tmp = (mxCell) q[i];
            if (tmp.isEdge()) {
                int edge = Integer.parseInt(tmp.getId()); //íåîáõîäèìî äëÿ ïîèñêà id â êàðòàõ
                for (Map.Entry<Integer, List<Integer>> entry : vertexCommunication.entrySet()) {
                    for (Map.Entry<Integer, List<Integer>> entry2 : bridges.entrySet()) {
                        //åñëè êëþ÷è âñïîìîãàòåëüíîé êàðòû è êàðòû ìîñòîâ ñîâïàäàþò è âñïîìîãàòåëüíàÿ êàðòà ñîäåðæèò id ðåáðà
                        if (entry.getKey().equals(entry2.getKey()) && entry.getValue().contains(edge)) {
                            List <Integer> edges = new ArrayList<Integer>();
                            edges = bridges.get(entry.getKey());
                            for (int j = 0; j < edges.size(); j++) {
                                if (vertexCommunication.get(edges.get(j)).contains(edge) ) {
                                    if (!Arrays.asList(bridgesGraphics).contains(tmp)) { //è íåò ïîâòîðÿþùèõñÿ ýëåìåíòîâ
                                        bridgesGraphics[size++] = tmp; //äîáàâëÿåì â ìàññèâ îáúåêòîâ
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void initVertexMap(){ //ñîçäàíèå ñïèñêà ñìåæíîñòè
        for (Map.Entry<Integer, List<Integer>> entry : vertexCommunication.entrySet()) {
            List<Integer> tmp = entry.getValue(); //òåêóùèé ñïèñîê ðžáåð
            for (Map.Entry<Integer, List<Integer>> entry2 : vertexCommunication.entrySet()) {
                for (int i = 0; i < tmp.size(); i++) { //íåîáõîäèìî ïðîéòèñü ïî ñïèñêó, ÷òîáû óçíàòü êàêèå ðžáðà ÿâëÿþòñÿ îáùèìè
                    //åñëè êëþ÷è íå ðàâíû è ðåáðî ñîäåðæèòñÿ â äðóãîì ñïèñêå
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
    }

    private void initVertexCommunication(){ //ñîçäàíèå íà÷àëüíîé âñïîìîãàòåëüíîé êàðòû
        Object[] edgesOfVertex;
        mxGraph graph = MainWindow.getGraph();
        Object[] q = graph.getChildCells(MainWindow.parent());
        for (int i = 0; i < q.length; i++) {
            mxCell tmp = (mxCell) q[i];
            if (tmp.isVertex()) {
                int vertex = Integer.parseInt(tmp.getId()) - 1; //ñîçäàžì èìÿ âåðøèíû
                addVertex(vertex); //äàæå åñëè âåðøèíà ïóñòàÿ, äîáàâëÿåì åž â êàðòó ãðàôà
                vertexCommunication.put(vertex, new ArrayList<Integer>()); //ñîçäàžì ñïèñîê äëÿ êàæäîé âåðøèíû, òóäà ïîìåñòèì ðžáðà
                edgesOfVertex = graph.getEdges(tmp); //ïîëó÷àåì ðžáðà âåðøèíû
                for (int f = 0; f < edgesOfVertex.length; f++) {
                    mxCell tmp2 = (mxCell) edgesOfVertex[f];
                    int edge = Integer.parseInt(tmp2.getId()); //ñîçäàžì èìÿ ðåáðà
                    List<Integer> edges = vertexCommunication.get(vertex); //ïåðåìåííûé ìàññèâ ðžáåð äëÿ äîáàâëåíèÿ
                    edges.add(edge); //äîáàâèì ðåáðî â ïðîìåæóòî÷íûé ñïèñîê è îòñîðòèðóåì
                    Collections.sort(edges);
                    vertexCommunication.put(vertex, edges); //äîáàâèì ïîëó÷åííûé ñïèñîê â êàðòó âåðøèíà - id ðžáåð
                }
            }
        }
    }

    private void addVertex(int vertexName) {
        if (!hasVertex(vertexName)) {
            vertexMap.put(vertexName, new ArrayList<Integer>());
        }
    }

    private void addVertexBridges(int vertexName) {
        if (!hasVertex(vertexName)) {
            bridges.put(vertexName, new ArrayList<Integer>());
        }
    }

    private boolean hasVertex(int vertexName) {
        return vertexMap.containsKey(vertexName);
    }

    private boolean hasVertexBridges(int vertexName) {
        return bridges.containsKey(vertexName);
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

    public void addEdgeBridges(Integer vertexName1, Integer vertexName2) {
        if (!hasVertexBridges(vertexName1))
            addVertexBridges(vertexName1);
        List<Integer> edges1 = bridges.get(vertexName1);
        edges1.add(vertexName2);
        Collections.sort(edges1);
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

    public void findDfs() {
        used = new boolean[vertexMap.size() + 1];
        vertexes = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : vertexMap.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                vertexes.add(entry.getKey());
                break;
            }
        }
        for (int i = 1; i <= vertexMap.size(); i++) {
            used[i] = false;
        }
        for (int i = 1; i <= vertexMap.size(); i++) {
            if (!used[i]) {
                dfs(i);
            }
        }
    }

    public void dfs(int pos) {
        used[pos] = true;
        //System.out.println("VERTEXES" + vertexes);
        for (int next : vertexMap.get(pos)){
            if (!used[next]){
                if (!vertexes.contains(next))
                    vertexes.add(next);
                System.out.println("VERTEXES" + vertexes);
                dfs(next);
            }
        }
    }

    private static class Edge { //êëàññ ðåáðà äëÿ êëàññà ãðàô, íàõîäÿùåãîñÿ íèæå
        public int u, v;

        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }

    public static class GraphCode { //êëàññ, ãðàôà äëÿ êîäà
        private Set<Integer>[] adjacent;
        public static int dfsCounter;

        public GraphCode(int vertices) {
            @SuppressWarnings("unchecked")
            Set<Integer>[] sets = (Set<Integer>[]) new Set[vertices];
            adjacent = sets;
            for (int v = 0; v < adjacent.length; v++) {
                adjacent[v] = new HashSet<Integer>();
            }
        }

        private void addVertex(int vertexName) {
            if (!hasVertex(vertexName)) {
                vertexMap.put(vertexName, new ArrayList<Integer>());
            }
        }

        private boolean hasVertex(int vertexName) {
            return vertexMap.containsKey(vertexName);
        }

        public void edge(BasicFunctions.Edge e) {
            adjacent[e.v].add(e.u);
            adjacent[e.u].add(e.v);
            addVertex(e.v);
            addVertex(e.u);
        }

        public void removeEdge(BasicFunctions.Edge e) {
            adjacent[e.v].remove(e.u);
            adjacent[e.u].remove(e.v);
        }

        public List<BasicFunctions.Edge> getEdges() {
            List<BasicFunctions.Edge> edges = new ArrayList<BasicFunctions.Edge>();
            for (int v = 0; v < adjacent.length; v++) {
                for (int u : adjacent[v]) {
                    if (v < u)
                        edges.add(new BasicFunctions.Edge(v, u));
                }
            }
            return edges;
        }

        public int calcComponents() {
            int count = 0;
            boolean[] visited = new boolean[adjacent.length];
            for (int i = 0; i < adjacent.length; i++) {
                if (!visited[i]) {
                    count++;
                    depthFirstSearch(i, visited);
                }
            }
            return count;
        }

        private void depthFirstSearch(int v, boolean[] visited) {
            visited[v] = true;
            for (int u : adjacent[v]) {
                if (!visited[u])
                    depthFirstSearch(u, visited);
            }
        }
    }
}

