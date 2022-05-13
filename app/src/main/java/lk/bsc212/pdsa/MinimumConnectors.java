package lk.bsc212.pdsa;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;

import lk.bsc212.pdsa.model.WeightedGraph;
import lk.bsc212.pdsa.utils.DijkstraAlgorithm;
import lk.bsc212.pdsa.utils.WeightedGraphVisualizer;

public class MinimumConnectors extends AppCompatActivity {

    AppBarLayout appBarLayout;
    DijkstraAlgorithm dijkstraAlgorithm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minimum_connectors);

        WeightedGraphVisualizer visualizer = new WeightedGraphVisualizer(this);
        appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addView(visualizer);
        dijkstraAlgorithm = new DijkstraAlgorithm();

    }

    public WeightedGraph createWeightedGraph2(int size) {
        WeightedGraph graph = new WeightedGraph(size);
        graph.setLabel(0, 0);
        graph.setLabel(1, 1);
        graph.setLabel(2, 2);
        graph.setLabel(3, 3);
        graph.setLabel(4, 4);
        graph.setLabel(5, 5);
        graph.setLabel(6, 6);
        graph.setLabel(7, 7);
        graph.setLabel(8, 8);
        graph.setLabel(9, 9);
        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, 2);
        graph.addEdge(3, 4, 2);
        graph.addEdge(3, 5, 2);
        graph.addEdge(3, 8, 2);
        graph.addEdge(5, 4, 2);
        graph.addEdge(4, 7, 2);
        graph.addEdge(4, 8, 50);
        graph.addEdge(4, 9, 2);
        graph.addEdge(7, 6, 2);
        graph.addEdge(6, 0, 42);
        graph.addEdge(6, 9, 2);
        graph.addEdge(8, 9, 32);
        graph.addEdge(9, 0, 22);
        graph.addEdge(9, 7, 2);
        graph.addEdge(8, 2, 2);
        graph.addEdge(8, 1, 2);
        return graph;
    }
}