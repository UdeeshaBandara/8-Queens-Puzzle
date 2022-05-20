package lk.bsc212.pdsa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;

import java.util.Arrays;

import lk.bsc212.pdsa.adapter.MinimumConnectorAdapter;
import lk.bsc212.pdsa.model.WeightedGraph;
import lk.bsc212.pdsa.model.room.CityDistanceMinimumConnector;
import lk.bsc212.pdsa.model.room.MinimumConnectorAnswer;
import lk.bsc212.pdsa.utils.AlertDialog;
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

    //Data structures
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
                new AlertDialog().negativeAlert(MinimumConnectors.this, "Invalid Move!!!", " Please complete all cities as for starting cities of pairs", "OK");

            } else if (Arrays.toString(selectedToCities).contains("-1")) {
                new AlertDialog().negativeAlert(MinimumConnectors.this, "Invalid Move!!!", "Please complete all cities as for destinations as of pairs", "OK");

            } else if (Arrays.toString(selectedDistance).contains("-1")) {
                new AlertDialog().negativeAlert(MinimumConnectors.this, "Invalid Move!!!", "Please fill out  distances for each pairs paired as choice", "OK");

            } else if (isCorrectAnswer()) {
                new AlertDialog().positiveAlert(MinimumConnectors.this, "Hurray and Congratulations!!!", "You have Successfully completed the Game", "OK");

                new PerformDatabaseOperations().execute();

            } else
                new AlertDialog().negativeAlert(MinimumConnectors.this, "Oops !!!", "Wrong answer, Better try again for your winning choices", "OK");
        });

    }

    class PerformDatabaseOperations extends AsyncTask<Void, Integer, Void> {

        @Override
        protected synchronized Void doInBackground(Void... params) {


            CityDistanceMinimumConnector[] showingEdges = new CityDistanceMinimumConnector[18];

            long answerId = MainApplication.minimumConnectorDao.insertAll(new MinimumConnectorAnswer(tinyDB.getLong("userId", 1), systemSelectedCity))[0];

            int outerIndex = 0;
            for (int count = 0; count < answerDistance.length; count++)
                showingEdges[outerIndex++] = new CityDistanceMinimumConnector(selectedFromCities[count], selectedToCities[count], selectedDistance[count], answerId);


            for (int row = 0; row < (weightedGraph.getEdges().length); row++)
                outerLoop:
                for (int col = 0; col < (weightedGraph.getEdges()[0].length); col++) {
                    for (int enteredIndex = 0; enteredIndex < answerDistance.length; enteredIndex++)

                        if ((showingEdges[enteredIndex].fromCityName == row && showingEdges[enteredIndex].toCityName == col) || (showingEdges[enteredIndex].toCityName == row && showingEdges[enteredIndex].fromCityName == col)) {
                            continue outerLoop;

                        }
                    if (weightedGraph.getEdges()[row][col] != 0 && weightedGraph.getEdges()[col][row] != 0) {

                        showingEdges[outerIndex++] = new CityDistanceMinimumConnector(row, col, weightedGraph.getEdges()[row][col], answerId);
                        //assign 0 to transposed location
                        weightedGraph.getEdges()[col][row] = 0;
                    }
                }

            MainApplication.minimumConnectorDao.insertDistanceBetweenCities(showingEdges);

            for (int in = 0; in < selectedDistance.length; in++)
                MainApplication.minimumConnectorDao.updateVisitedFlag(String.valueOf(selectedFromCities[in]), String.valueOf(selectedToCities[in]), String.valueOf(answerId));


            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

        }


        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            initGraph();
        }
    }


    void initGraph() {
        Arrays.fill(selectedFromCities, -1);
        Arrays.fill(selectedToCities, -1);
        Arrays.fill(selectedDistance, -1);

        systemSelectedCity = (int) (Math.random() * (9 + 1) + 0);
        recyclerMinimumConnector = findViewById(R.id.recycler_minimum_connector);
        recyclerMinimumConnector.setLayoutManager(new LinearLayoutManager(MinimumConnectors.this, LinearLayoutManager.VERTICAL, false));


        weightedGraph = new WeightedGraph(10);

        primsGraphDrawer.setData(weightedGraph, systemSelectedCity);
        description.setText("Lets Find the Minimum connectors starting from city " + systemSelectedCity);

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