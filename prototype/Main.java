import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        SomeFunctions graph = new SomeFunctions();
        System.out.println("Введите граф: ");
        String vertex = "";
        Scanner in = new Scanner(System.in);

        while (true) {
            vertex = in.nextLine();
            if (vertex.equals("end"))
                break;
            graph.addVertex(vertex);
        }
        graph.print(graph.vertexMap);
        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.print(graph.vertexMap);
    }
}
