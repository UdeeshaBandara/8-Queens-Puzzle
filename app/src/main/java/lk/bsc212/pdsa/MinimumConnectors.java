package lk.bsc212.pdsa;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;

import lk.bsc212.pdsa.adapter.MinimumConnectorAdapter;
import lk.bsc212.pdsa.adapter.ShortestPathDistanceAdapter;
import lk.bsc212.pdsa.model.WeightedGraph;
import lk.bsc212.pdsa.utils.DijkstraAlgorithm;
import lk.bsc212.pdsa.utils.WeightedGraphVisualizer;

public class MinimumConnectors extends AppCompatActivity {

    AppBarLayout appBarLayout;
    DijkstraAlgorithm dijkstraAlgorithm;

    RecyclerView recyclerMinimumConnector;
    MinimumConnectorAdapter minimumConnectorAdapter;
    WeightedGraphVisualizer weightedGraphVisualizer;
    WeightedGraph weightedGraph;
    int systemSelectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minimum_connectors);

        weightedGraphVisualizer= new WeightedGraphVisualizer(this);
        appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addView(weightedGraphVisualizer);
        dijkstraAlgorithm = new DijkstraAlgorithm();
        init();

    }

    void init() {

        recyclerMinimumConnector = findViewById(R.id.recycler_minimum_connector);
        recyclerMinimumConnector.setLayoutManager(new LinearLayoutManager(MinimumConnectors.this, LinearLayoutManager.VERTICAL, false));

        systemSelectedCity = (int) (Math.random() * (9 + 1) + 0);
        weightedGraph = new WeightedGraph(10);
        weightedGraphVisualizer.setData(weightedGraph,systemSelectedCity);

        minimumConnectorAdapter = new MinimumConnectorAdapter(MinimumConnectors.this);
        recyclerMinimumConnector.setAdapter(minimumConnectorAdapter);

    }


}