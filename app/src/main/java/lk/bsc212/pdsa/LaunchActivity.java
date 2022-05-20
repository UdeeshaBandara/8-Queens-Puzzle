package lk.bsc212.pdsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import lk.bsc212.pdsa.R;
import lk.bsc212.pdsa.utils.TinyDB;

public class LaunchActivity extends AppCompatActivity {

    TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tinyDB = new TinyDB(LaunchActivity.this);
                if (tinyDB.getBoolean("isNameSelected")){
                    startActivity(new Intent(LaunchActivity.this, MenuActivity.class));
                }
                else{
                    startActivity(new Intent(LaunchActivity.this, NameActivity.class));
                }
                finishAffinity();
            }
        }, 3000);
    }
}