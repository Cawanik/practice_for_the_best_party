import java.util.*;
import static java.lang.Math.min;

public class SomeFunctions{
    private HashMap<Integer, List<Integer>> vertexMap = new HashMap<Integer, List<Integer>>();
    private HashMap<Integer, Integer> bridges = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> stopBridgeForward = new HashMap<Integer, Integer>(); //ребро, которое остановило dfs
    private boolean forward; //остановка для шага вперёд
    private boolean stop; //остановка
    private int time;
    private boolean[] used;
    private int[] fup;
    private int[] tin;

    public void addVertex(int vertexName) {
        if (!hasVertex(vertexName)) {
            vertexMap.put(vertexName, new ArrayList<Integer>());
        }
    }

    public void deleteVertex(int vertexName) {
        if (!hasVertex(vertexName)) {
            return;
        }
        vertexMap.remove(vertexName);
    }

    public boolean hasVertex(int vertexName) {
        return vertexMap.containsKey(vertexName);
    }

    public boolean hasEdge(int vertexName1, int vertexName2) {
        if (!hasVertex(vertexName1))
            return false;
        List<Integer> edges = vertexMap.get(vertexName1);
        return Collections.binarySearch(edges, vertexName2) != -1;
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

    public Map<Integer, List<Integer>> getVertexMap() {
        return vertexMap;
    }

    public void printGraph() {
        System.out.println("Graph is: ");
        System.out.println(vertexMap);
    }

    public void printBridges() {
        System.out.println("Bridges is: ");
        System.out.println(bridges);
    }

    public void dfsBridges(int v, int p, boolean flag) {
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
                dfsBridges(to, v, flag);
                fup[v] = min(fup[v], fup[to]);
                if (fup[to] > tin[v]) {
                    bridges.put(v, to);
                    if (stopBridgeForward.containsKey(v) && forward) { //если остановочное ребро не пустое и содержит данный мост
                        //а ещё стоит флаг, что мы идём вперёд
                        if (to == stopBridgeForward.get(v)) //если мост уже был рассмотрен
                            continue;//то переходим к поиску следующего моста
                    }
                    if (flag && !stop) { //если flag = true, что значит, что пользователь выбрал поледовательный вывод
                        //мостов, а также флаг остановки ещё не поставлен, т.е. пользователь не поменял свой выбор
                        printBridges();
                        while (!stop){//пока не будет выбран вариант остановки последовательного вывода рёбер
                            Scanner in = new Scanner(System.in);
                            System.out.println("Вы хотите сделать шаг вперёд? yes/no");
                            String res = in.nextLine();
                            if (res.equals("yes")) { //если пользователь хочет сделать шаг вперёд, то продолжение
                                // последовательного вывода мостов
                                forward = true;
                                stopBridgeForward.put(v, to);//мост, который был напечатан сохраняется в остановочное ребро
                                findBridges(true);
                            }
                            if (res.equals("no")) { //если пользователь больше не хочет двигаться вперёд
                                System.out.println("Вы хотите сделать шаг назад? yes - (разрабатывается, рекомендуется написать 'no')/no");
                                res = in.nextLine();
                                if (res.equals("no")) { //если пользователь больше не хочет никуда двигаться по
                                    // последовательному выводу
                                    stop = true; //то происходит остановка процесса
                                    findBridges(false); //будут выведены мосты без последовательного вывода, а
                                    //сразу в качестве конечного результата
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void findBridges(boolean flag) {
        used = new boolean[vertexMap.size() + 1];
        fup = new int[vertexMap.size() + 1];
        tin = new int[vertexMap.size() + 1];
        stop = false;
        for (int i = 1; i <= vertexMap.size(); i++) {
            used[i] = false;
        }
        for (int i = 1; i <= vertexMap.size(); i++) {
            if (!used[i]) {
                dfsBridges(i, -1, flag);
            }
        }
        stop = true;
    }

    public void deleteBridges() {
        findBridges(false);
        for (Map.Entry<Integer, Integer> info : bridges.entrySet()) {
            deleteEdge(info.getKey(), info.getValue());
        }
    }
}
