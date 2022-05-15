package lk.bsc212.pdsa;

import android.os.Bundle;
import android.view.View;
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
import lk.bsc212.pdsa.utils.PrimsAlgorithm;
import lk.bsc212.pdsa.utils.PrimsGraphDrawer;

public class MinimumConnectors extends AppCompatActivity {

    AppBarLayout appBarLayout;
    TextView description;
    Button btnCheck;

    RecyclerView recyclerMinimumConnector;
    MinimumConnectorAdapter minimumConnectorAdapter;
    PrimsGraphDrawer primsGraphDrawer;
    WeightedGraph weightedGraph;
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
        init();

        new PrimsAlgorithm().primMST(weightedGraph.getEdges(), systemSelectedCity, answerFromCities, answerToCities, answerDistance);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCorrectAnswer())
                    Toast.makeText(MinimumConnectors.this, "Correct answer", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void init() {
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