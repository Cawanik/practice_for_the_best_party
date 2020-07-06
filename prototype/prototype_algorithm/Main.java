import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        SomeFunctions graph = new SomeFunctions();
        System.out.println("Введите граф: ");
        int vertex;
        Scanner in = new Scanner(System.in);

        while (true) {
            vertex = in.nextInt();
            if (vertex == -1)
                break;
            graph.addVertex(vertex);
        }
        graph.printGraph();
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);
        graph.addEdge(2, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 7);
        graph.addEdge(7, 6);
        graph.addEdge(6, 4);
        graph.addEdge(7, 10);
        graph.addEdge(10, 8);
        graph.addEdge(8, 9);
        graph.addEdge(9, 11);
        graph.addEdge(11, 10);
        graph.addEdge(11, 12);
        graph.printGraph();
        graph.findBridges();
        graph.printBridges();
        graph.deleteBridges();
        graph.printGraph();
        graph.deleteVertex(12);
        graph.printGraph();
        graph.findBridges();
        graph.printGraph();
    }
}
