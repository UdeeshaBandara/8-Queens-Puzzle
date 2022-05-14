package lk.bsc212.pdsa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lk.bsc212.pdsa.adapter.ShortestPathDistanceAdapter;
import lk.bsc212.pdsa.model.room.CityDistance;
import lk.bsc212.pdsa.model.PlacePredict;
import lk.bsc212.pdsa.model.room.ShortestDistanceAnswer;
import lk.bsc212.pdsa.model.room.ShortestDistanceAnswerCity;
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
    ArrayList<PlacePredict> predictedDistance = new ArrayList<>();
    List<Integer> shortestPathAnswers = new LinkedList<>();

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


        recyclerDistance.setLayoutManager(new LinearLayoutManager(ShortestPath.this, LinearLayoutManager.VERTICAL, false));

        refresh.setOnClickListener(view -> initGraph());


        btnCheck.setOnClickListener(view -> {

            CityDistance[] showingEdges = new CityDistance[18];
            ShortestDistanceAnswer[] predictedDis = new ShortestDistanceAnswer[9];

            if (predictedDistance.stream().anyMatch(o -> o.getPredictedDistance() == 0.0))
                Toast.makeText(ShortestPath.this, "Please enter distance for all cities", Toast.LENGTH_SHORT).show();

            else if (isCorrectAnswer()) {
                Toast.makeText(ShortestPath.this, "Correct answer", Toast.LENGTH_SHORT).show();
//
                AsyncTask.execute(() -> {
                    long answerId = MainApplication.shortestPathDao.insertAll(new ShortestDistanceAnswerCity(tinyDB.getLong("userId", 1), systemSelectedCity))[0];
                    int outerIndex = 0;
                    for (int row = 0; row < (weightedGraph.getEdges().length); row++)
                        for (int col = 0; col < (weightedGraph.getEdges()[0].length); col++)
                            if (weightedGraph.getEdges()[row][col] != 0 && weightedGraph.getEdges()[col][row] != 0) {
                                showingEdges[outerIndex++] = new CityDistance(row, col, weightedGraph.getEdges()[row][col], answerId);
                                weightedGraph.getEdges()[col][row] = 0;
                            }

                    MainApplication.shortestPathDao.insertDistanceBetweenCities(showingEdges);

                    for (int in = 0; in < predictedDistance.size(); in++)
                        predictedDis[in] = new ShortestDistanceAnswer(predictedDistance.get(in).getCityName(), predictedDistance.get(in).getPredictedDistance(), answerId);


                    MainApplication.shortestPathDao.insertShortestPaths(predictedDis);


                });
                initGraph();

            } else
                Toast.makeText(ShortestPath.this, "Wrong answer! Try again!!!", Toast.LENGTH_SHORT).show();
        });

    }


    void initGraph() {

        shortestPathDistanceAdapter = new ShortestPathDistanceAdapter(ShortestPath.this);
        recyclerDistance.setAdapter(shortestPathDistanceAdapter);

        systemSelectedCity = (int) (Math.random() * (9 + 1) + 0);
        weightedGraph = new WeightedGraph(10);
        predictedDistance = new ArrayList<>();

        weightedGraphVisualizer.setData(weightedGraph,systemSelectedCity);

        description.setText("Predict shortest distance from city " + systemSelectedCity + " to following cities");
        for (int index = 0; index < 10; index++)
            if (index != systemSelectedCity)
                predictedDistance.add(new PlacePredict(index));

        shortestPathDistanceAdapter.setPredictedDistance(predictedDistance);
        dijkstraAlgorithm.performDijkstra(weightedGraph, systemSelectedCity, shortestPathAnswers);


    }

    boolean isCorrectAnswer() {

        predictedDistance.add(systemSelectedCity, new PlacePredict(systemSelectedCity, 0));
        boolean isCorrectAnswer = true;

        for (int index = 0; index < shortestPathAnswers.size(); index++) {

            if (index == systemSelectedCity)
                continue;

            if (shortestPathAnswers.get(index) != predictedDistance.get(index).getPredictedDistance()) {
                isCorrectAnswer = false;
                break;
            }

        }
        predictedDistance.remove(systemSelectedCity);

        return isCorrectAnswer;

    }


}