package lk.bsc212.pdsa;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ncorti.slidetoact.SlideToActView;

import lk.bsc212.pdsa.utils.TinyDB;

public class MenuActivity extends AppCompatActivity {

    SlideToActView slideQueen, slideConnectors, slidePath;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tinyDB = new TinyDB(MenuActivity.this);
        slideQueen = findViewById(R.id.slideQueen);
        slideConnectors = findViewById(R.id.slideConnectors);
        slidePath = findViewById(R.id.slidePath);

        slideQueen.setOnSlideCompleteListener(slideToActView -> {
            slideQueen.resetSlider();
            startActivity(new Intent(MenuActivity.this, QueenPuzzle.class));
        });

        slideConnectors.setOnSlideCompleteListener(slideToActView -> {
            slideConnectors.resetSlider();
            startActivity(new Intent(MenuActivity.this, MinimumConnectors.class));
        });

        slidePath.setOnSlideCompleteListener(slideToActView -> {
            slidePath.resetSlider();
            startActivity(new Intent(MenuActivity.this, ShortestPath.class));
        });
        findViewById(R.id.btn_switch).setOnClickListener(view -> {

            tinyDB.putBoolean("isNameSelected", false);
            startActivity(new Intent(MenuActivity.this, NameActivity.class));
            finishAffinity();

        });
    }
}