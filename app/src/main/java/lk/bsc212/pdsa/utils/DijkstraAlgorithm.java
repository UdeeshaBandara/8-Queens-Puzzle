package lk.bsc212.pdsa.utils;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

import lk.bsc212.pdsa.model.WeightedGraph;

public class DijkstraAlgorithm  {


    public void performDijkstra(WeightedGraph G, int s, List<Integer> shortestPathAnswers) {


        final int[] dist = new int[G.size()];
        final int[] pred = new int[G.size()];
        final boolean[] visited = new boolean[G.size()];

        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[s] = 0;

        for (int i = 0; i < dist.length; i++) {
            final int next = minVertex(dist, visited);
            visited[next] = true;

            final int[] n = G.neighbors(next);
            for (final int v : n) {
                final int d = dist[next] + G.getWeight(next, v);
                if (dist[v] > d) {
                    dist[v] = d;
                    pred[v] = next;
                }
            }
        }

        for (int i = 0; i < G.size(); i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                System.out.println("There is no path between source " + s + " and vertex " + i);
            } else {
                shortestPathAnswers.add(dist[i]);
                Log.e("TAG","Shortest path from source:" + s + " to vertex " + i + " is " + dist[i]);
            }
        }

    }

    private static int minVertex(int[] dist, boolean[] v) {
        int x = Integer.MAX_VALUE;
        int y = -1;
        for (int i = 0; i < dist.length; i++) {
            if (!v[i] && dist[i] < x) {
                y = i;
                x = dist[i];
            }
        }
        return y;
    }

}
