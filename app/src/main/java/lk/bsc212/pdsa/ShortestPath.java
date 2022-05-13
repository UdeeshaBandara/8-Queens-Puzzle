package lk.bsc212.pdsa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.LinkedList;

import lk.bsc212.pdsa.adapter.ShortestPathDistanceAdapter;
import lk.bsc212.pdsa.model.CityDistance;
import lk.bsc212.pdsa.model.PlacePredict;
import lk.bsc212.pdsa.model.ShortestDistanceAnswer;
import lk.bsc212.pdsa.model.ShortestDistanceAnswerCity;
import lk.bsc212.pdsa.model.WeightedGraph;
import lk.bsc212.pdsa.utils.DijkstraAlgorithm;
import lk.bsc212.pdsa.utils.TinyDB;
import lk.bsc212.pdsa.utils.WeightedGraphVisualizer;

public class ShortestPath extends AppCompatActivity {


    AppBarLayout appBarLayout;
    DijkstraAlgorithm dijkstraAlgorithm;

    RecyclerView recyclerDistance;
    KProgressHUD hud;
    ImageView refresh;
    TextView description;
    Button btnCheck;

    ShortestPathDistanceAdapter shortestPathDistanceAdapter;
    WeightedGraphVisualizer weightedGraphVisualizer;
    TinyDB tinyDB;
    WeightedGraph weightedGraph;

    //Data structures
    LinkedList<PlacePredict> predictedDistance = new LinkedList<>();

    int systemSelectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortest_path);

        init();

        initGraph();


    }


    void init() {

        recyclerDistance = findViewById(R.id.recycler_distance);
        refresh = findViewById(R.id.refresh);
        description = findViewById(R.id.description);
        btnCheck = findViewById(R.id.btn_check);
        appBarLayout = findViewById(R.id.app_bar);


        tinyDB = new TinyDB(ShortestPath.this);
        weightedGraphVisualizer = new WeightedGraphVisualizer(this);
        appBarLayout.addView(weightedGraphVisualizer);
        dijkstraAlgorithm = new DijkstraAlgorithm();




        description.setText("Predict shortest distance from city " + systemSelectedCity + " to following cities");
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);


        shortestPathDistanceAdapter = new ShortestPathDistanceAdapter(ShortestPath.this);
        recyclerDistance.setAdapter(shortestPathDistanceAdapter);
        recyclerDistance.setLayoutManager(new LinearLayoutManager(ShortestPath.this, LinearLayoutManager.VERTICAL, false));

        refresh.setOnClickListener(view -> {

          initGraph();

        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityDistance[] showingEdges = new CityDistance[weightedGraph.size()];
                ShortestDistanceAnswer[] predictedDis = new ShortestDistanceAnswer[9];

                if (predictedDistance.stream().anyMatch(o -> o.getPredictedDistance() == 0.0))
                    Toast.makeText(ShortestPath.this, "Please enter distance for all cities", Toast.LENGTH_SHORT).show();

                else {
                    AsyncTask.execute(() -> {
                        long answerId = MainApplication.shortestPathDao.insertAll(new ShortestDistanceAnswerCity(tinyDB.getLong("userId", 1), systemSelectedCity))[0];
                        int outerIndex = 0;
                        for (int row = 0; 0 < weightedGraph.getEdges().length; row++)
                            for (int col = 0; 0 < weightedGraph.getEdges().length; col++)
                                if (weightedGraph.getEdges()[row][col] != 0)
                                    showingEdges[outerIndex++] = new CityDistance(row, col, weightedGraph.getEdges()[row][col], answerId);

                        MainApplication.shortestPathDao.insertDistanceBetweenCities(showingEdges);

                        for (int in = 0; 0 < predictedDistance.size(); in++)
                            predictedDis[in] = new ShortestDistanceAnswer(predictedDistance.get(in).getCityName(), predictedDistance.get(in).getPredictedDistance(), answerId);


                        MainApplication.shortestPathDao.insertShortestPaths(predictedDis);

                    });
//

                }
            }


        });

    }
    void initGraph() {

        systemSelectedCity = (int) (Math.random() * (9 + 1) + 0);
        weightedGraph = new WeightedGraph(10);
        predictedDistance.clear();

        weightedGraphVisualizer.setData(weightedGraph);
        dijkstraAlgorithm.dijkstra(weightedGraph, systemSelectedCity);
        description.setText("Predict shortest distance from city " + systemSelectedCity + " to following cities");
        for (int index = 0; index < 10; index++)
            if (index != systemSelectedCity)
                predictedDistance.add(new PlacePredict(index));

        shortestPathDistanceAdapter.setPredictedDistance(predictedDistance);


    }



}