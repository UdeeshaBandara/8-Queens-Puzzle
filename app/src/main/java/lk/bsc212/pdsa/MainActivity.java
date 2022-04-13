package lk.bsc212.pdsa;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lk.bsc212.pdsa.adapter.ChessAdapter;
import lk.bsc212.pdsa.model.QueenPlace;
import lk.bsc212.pdsa.room.AppDatabase;
import lk.bsc212.pdsa.room.dao.PlaceDao;
import lk.bsc212.pdsa.utils.Queens;

public class MainActivity extends AppCompatActivity {

    //Data structures
    List<QueenPlace> possiblePlaces = new ArrayList<>();
    ArrayList<String> selectedPlaces = new ArrayList<>(Collections.nCopies(64, "0"));

    //UI elements
    RecyclerView recyclerChessBoard;
    private KProgressHUD hud;

    ChessAdapter chessAdapter;

    //Database
    public static AppDatabase appDatabase;
    public static PlaceDao placeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        //get the database instance and init Dao
        appDatabase = AppDatabase.getDatabase(MainActivity.this);
        placeDao = appDatabase.placeDao();

        loadData();


        findViewById(R.id.btn_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(MainActivity.this, possiblePlaces.get(0).places, Toast.LENGTH_SHORT).show();

                if (Collections.frequency(selectedPlaces, "1") != 8)

                    Toast.makeText(MainActivity.this, "Please select 8 places", Toast.LENGTH_SHORT).show();

                else {
                    String selectedPlacesString = selectedPlaces.stream().map(Object::toString)
                            .collect(Collectors.joining(", "));
                    for (QueenPlace placesArray : possiblePlaces) {

                        if (placesArray.places.equals(selectedPlacesString)) {
                            Toast.makeText(MainActivity.this, "Correct answer", Toast.LENGTH_SHORT).show();

                        }
//                        possiblePlaces.add(new ArrayList<>(Arrays.asList(Arrays.stream(place.places.split(",")).map(Boolean::parseBoolean).toArray(Boolean[]::new))));

                    }

                }


            }
        });
        findViewById(R.id.btn_clear).setOnClickListener(view -> {

            selectedPlaces = new ArrayList<>(Collections.nCopies(64, "0"));
            chessAdapter.updatePlaces(selectedPlaces);
           
        });

    }

    void init() {

        recyclerChessBoard = findViewById(R.id.recycler_chess_board);
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        chessAdapter = new ChessAdapter(MainActivity.this, selectedPlaces);
        recyclerChessBoard.setAdapter(chessAdapter);
        recyclerChessBoard.setLayoutManager(new GridLayoutManager(MainActivity.this, 8, GridLayoutManager.VERTICAL, false));
    }

    void loadData() {
        showHUD();

        new Thread(() -> {
            if (placeDao.combinationCount() != 92)
                Queens.enumerate(8);

            possiblePlaces = placeDao.getQueenPlaces();
//
//            for (QueenPlace place : placeDao.getQueenPlaces()) {
////                        possiblePlaces.add(new ArrayList<>(Arrays.asList(Arrays.stream(place.places.split(",")).map(Boolean::parseBoolean).toArray(Boolean[]::new))));
//
//                possiblePlaces.add(place.places);
//            }
            runOnUiThread(() -> {

                hideHUD();

            });


        }).start();


    }


    private void showHUD() {
        if (hud.isShowing()) {
            hud.dismiss();
        }
        hud.show();
    }

    private void hideHUD() {
        if (hud.isShowing()) {
            hud.dismiss();
        }
    }
}