package lk.bsc212.pdsa.utils;

import android.util.Log;

public class PrimsAlgorithm {

    private static final int totalCities = 10;
    private int systemSelectedCity;
    int[] answerFromCities;
    int[] answerToCities;
    int[] answerDistance;

    int minKey(int[] key, Boolean[] mstSet) {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < totalCities; v++)
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    void printMST(int[] parent, int[][] graph) {
        Log.e("Prims", "Edge \tWeight");
        int incrementer = 0;
        for (int i = 0; i < totalCities; i++)
            if (i != systemSelectedCity) {
                answerFromCities[incrementer] = parent[i];
                answerToCities[incrementer] = i;
                answerDistance[incrementer] = graph[i][parent[i]];
                Log.e("Prims", parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
                incrementer++;
            }
    }

    public void primMST(int[][] graph, int systemSelectedCity, int[] answerFromCities, int[] answerToCities, int[] answerDistance) {

        this.systemSelectedCity = systemSelectedCity;
        this.answerFromCities = answerFromCities;
        this.answerToCities = answerToCities;
        this.answerDistance = answerDistance;
        // Array to store constructed MST
        int[] parent = new int[totalCities];

        // Key values used to pick minimum weight edge in cut
        int[] key = new int[totalCities];

        // To represent set of vertices included in MST
        Boolean[] mstSet = new Boolean[totalCities];

        // Initialize all keys as INFINITE
        for (int i = 0; i < totalCities; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }


        key[systemSelectedCity] = 0; // Make key 0 so that this vertex is
        // picked as first vertex
        parent[systemSelectedCity] = -1; // First node is always root of MST

        // The MST will have totalCities vertices
        for (int count = 0; count < totalCities - 1; count++) {
            // Pick thd minimum key vertex from the set of vertices
            // not yet included in MST
            int u = minKey(key, mstSet);

            // Add the picked vertex to the MST Set
            mstSet[u] = true;

            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < totalCities; v++)

                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
        }

        // print the constructed MST
        printMST(parent, graph);
    }
}
