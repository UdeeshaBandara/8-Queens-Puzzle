package lk.bsc212.pdsa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;

import java.util.Arrays;

import lk.bsc212.pdsa.adapter.MinimumConnectorAdapter;
import lk.bsc212.pdsa.model.WeightedGraph;
import lk.bsc212.pdsa.model.room.CityDistanceMinimumConnector;
import lk.bsc212.pdsa.model.room.MinimumConnectorAnswer;
import lk.bsc212.pdsa.model.room.MinimumConnectorAnswerCity;
import lk.bsc212.pdsa.utils.PrimsAlgorithm;
import lk.bsc212.pdsa.utils.PrimsGraphDrawer;
import lk.bsc212.pdsa.utils.TinyDB;

public class MinimumConnectors extends AppCompatActivity {

    AppBarLayout appBarLayout;
    TextView description;
    Button btnCheck;

    RecyclerView recyclerMinimumConnector;
    MinimumConnectorAdapter minimumConnectorAdapter;
    PrimsGraphDrawer primsGraphDrawer;
    WeightedGraph weightedGraph;
    TinyDB tinyDB;
    int systemSelectedCity;

    int[] selectedFromCities = new int[9];
    int[] selectedToCities = new int[9];
    int[] selectedDistance = new int[9];

    int[] answerFromCities = new int[9];
    int[] answerToCities = new int[9];
    int[] answerDistance = new int[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minimum_connectors);

        Arrays.fill(selectedFromCities, -1);
        Arrays.fill(selectedToCities, -1);
        Arrays.fill(selectedDistance, -1);
        primsGraphDrawer = new PrimsGraphDrawer(this);
        appBarLayout = findViewById(R.id.app_bar);
        description = findViewById(R.id.description);
        btnCheck = findViewById(R.id.btn_check);
        appBarLayout.addView(primsGraphDrawer);
        tinyDB = new TinyDB(MinimumConnectors.this);
        initGraph();

        new PrimsAlgorithm().primMST(weightedGraph.getEdges(), systemSelectedCity, answerFromCities, answerToCities, answerDistance);

        btnCheck.setOnClickListener(view -> {

            if (Arrays.toString(selectedFromCities).contains("-1")) {
                Toast.makeText(MinimumConnectors.this, "Please select all from cities", Toast.LENGTH_SHORT).show();

            } else if (Arrays.toString(selectedToCities).contains("-1")) {
                Toast.makeText(MinimumConnectors.this, "Please select all destination cities", Toast.LENGTH_SHORT).show();

            } else if (Arrays.toString(selectedDistance).contains("-1")) {
                Toast.makeText(MinimumConnectors.this, "Please enter distance for all cities", Toast.LENGTH_SHORT).show();

            } else if (isCorrectAnswer()) {
                Toast.makeText(MinimumConnectors.this, "Correct answer", Toast.LENGTH_SHORT).show();

                new PerformDatabaseOperations().execute();



            } else
                Toast.makeText(MinimumConnectors.this, "Wrong answer! Try again!!!", Toast.LENGTH_SHORT).show();
        });

    }

    class PerformDatabaseOperations extends AsyncTask<Void, Integer, long[]> {

        @Override
        protected synchronized long[] doInBackground(Void... params) {


            CityDistanceMinimumConnector[] showingEdges = new CityDistanceMinimumConnector[18];
            MinimumConnectorAnswerCity[] predictedDis = new MinimumConnectorAnswerCity[9];

            long answerId = MainApplication.minimumConnectorDao.insertAll(new MinimumConnectorAnswer(tinyDB.getLong("userId", 1), systemSelectedCity))[0];

            int outerIndex = 0;
            for (int row = 0; row < (weightedGraph.getEdges().length); row++)
                for (int col = 0; col < (weightedGraph.getEdges()[0].length); col++)
                    if (weightedGraph.getEdges()[row][col] != 0 && weightedGraph.getEdges()[col][row] != 0) {
                        showingEdges[outerIndex++] = new CityDistanceMinimumConnector(row, col, weightedGraph.getEdges()[row][col], answerId);
                        weightedGraph.getEdges()[col][row] = 0;
                    }

            MainApplication.minimumConnectorDao.insertDistanceBetweenCities(showingEdges);

            for (int in = 0; in < selectedDistance.length; in++)
                predictedDis[in] = new MinimumConnectorAnswerCity(selectedFromCities[in], selectedToCities[in], selectedDistance[in], answerId);

            return MainApplication.minimumConnectorDao.insertShortestPaths(predictedDis);


        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

        }


        @Override
        protected synchronized void onPostExecute(long[] result) {
            super.onPostExecute(result);
            if (result.length > 0) {

                initGraph();
                Arrays.fill(selectedFromCities, -1);
                Arrays.fill(selectedToCities, -1);
                Arrays.fill(selectedDistance, -1);
            }

        }
    }


    void initGraph() {

        systemSelectedCity = (int) (Math.random() * (9 + 1) + 0);
        recyclerMinimumConnector = findViewById(R.id.recycler_minimum_connector);
        recyclerMinimumConnector.setLayoutManager(new LinearLayoutManager(MinimumConnectors.this, LinearLayoutManager.VERTICAL, false));


        weightedGraph = new WeightedGraph(10);

        primsGraphDrawer.setData(weightedGraph, systemSelectedCity);
        description.setText("Find minimum connectors from city " + systemSelectedCity);

        minimumConnectorAdapter = new MinimumConnectorAdapter(MinimumConnectors.this, systemSelectedCity, selectedFromCities, selectedToCities, selectedDistance);
        recyclerMinimumConnector.setAdapter(minimumConnectorAdapter);

    }

    boolean isCorrectAnswer() {

        boolean[] isCorrectAnswer = new boolean[9];

        for (int index = 0; index < selectedDistance.length; index++) {
            for (int innerIndex = 0; innerIndex < selectedDistance.length; innerIndex++) {
                if (answerToCities[innerIndex] == selectedToCities[index] && answerFromCities[innerIndex] == selectedFromCities[index] && answerDistance[innerIndex] == selectedDistance[index]) {
                    isCorrectAnswer[index] = true;

                    break;
                }
            }
        }
        return !Arrays.toString(isCorrectAnswer).contains("false");

    }


}