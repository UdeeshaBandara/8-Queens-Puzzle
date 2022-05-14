package lk.bsc212.pdsa.model;

public class WeightedGraph {
    private int[][] edges;
    private int[] labels;

    public WeightedGraph(int n) {
        edges = new int[n][n];
        labels = new int[n];

        for (int in = 0; in < labels.length; in++)
            setLabel(in, in);

        generateRandomDistances();
    }

    public void generateRandomDistances() {


        addEdge(0, 1, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(0, 6, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(0, 9, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(1, 2, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(1, 8, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(2, 3, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(2, 8, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(3, 4, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(3, 5, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(3, 8, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(4, 7, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(4, 8, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(4, 9, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(4, 5, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(6, 9, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(6, 7, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(7, 9, (int) (Math.random() * (50 - 5 + 1) + 5));
        addEdge(8, 9, (int) (Math.random() * (50 - 5 + 1) + 5));

        //Backtrace the edge values to make it a bi-directional graph
        for (int row = 0; row < edges.length; row++)
            for (int column = 0; column < edges[0].length; column++)
                edges[column][row] = edges[row][column];


    }


    public int size() {
        return labels.length;
    }

    public void setLabel(int vertex, int label) {
        labels[vertex] = label;
    }

    public int getLabel(int vertex) {
        return labels[vertex];
    }

    public void addEdge(int source, int target, int w) {
        edges[source][target] = w;
    }

    public int[][] getEdges() {
        return edges;
    }

    public void setEdges(int[][] edges) {
        this.edges = edges;
    }

    public boolean isEdge(int source, int target) {
        return edges[source][target] > 0;
    }

    public void removeEdge(int source, int target) {
        edges[source][target] = 0;
    }

    public int getWeight(int source, int target) {
        return edges[source][target];
    }

    public int[] neighbors(int vertex) {
        int count = 0;
        for (int i = 0; i < edges[vertex].length; i++) {
            if (edges[vertex][i] > 0) count++;
        }
        final int[] answer = new int[count];
        count = 0;
        for (int i = 0; i < edges[vertex].length; i++) {
            if (edges[vertex][i] > 0) answer[count++] = i;
        }
        return answer;
    }
}