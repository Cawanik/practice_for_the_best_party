import java.util.*;
import static java.lang.Math.min;

public class SomeFunctions{
    private HashMap<Integer, List<Integer>> vertexMap = new HashMap<Integer, List<Integer>>();
    private HashMap<Integer, Integer> bridges = new HashMap<Integer, Integer>();
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
        }
    }
}
