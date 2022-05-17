package lk.bsc212.pdsa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.ncorti.slidetoact.SlideToActView;

import lk.bsc212.pdsa.utils.AlertDialog;

public class MenuActivity extends AppCompatActivity {

    SlideToActView slideQueen, slideConnectors, slidePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        slideQueen = findViewById(R.id.slideQueen);
        slideConnectors = findViewById(R.id.slideConnectors);
        slidePath = findViewById(R.id.slidePath);

        slideQueen.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView slideToActView) {
                slideQueen.resetSlider();
                startActivity(new Intent(MenuActivity.this, QueenPuzzle.class));
//                new AlertDialog().positiveAlert(MenuActivity.this,"Congratulations","You have Successfully Completed this event", "OK" );
//                new AlertDialog().negativeAlert(MenuActivity.this,"Sorry","You have not Successfully Completed this event", "Back to Menu" );
//                new AlertDialog().positiveAlert(MenuActivity.this,"Congratulations","You have Successfully Completed this event", "Try Again!", new Intent(MenuActivity.this, QueenPuzzle.class));
            }
        });

        slideConnectors.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView slideToActView) {
                slideConnectors.resetSlider();
                startActivity(new Intent(MenuActivity.this, MinimumConnectors.class));
            }
        });

        slidePath.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView slideToActView) {
                slidePath.resetSlider();
                startActivity(new Intent(MenuActivity.this, ShortestPath.class));
            }
        });
    }
}