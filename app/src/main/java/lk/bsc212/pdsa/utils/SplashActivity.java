package lk.bsc212.pdsa.utils;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import lk.bsc212.pdsa.MainActivity;
import lk.bsc212.pdsa.NameActivity;

public class SplashActivity extends AppCompatActivity {

    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tinyDB =new TinyDB(SplashActivity.this);

        if(tinyDB.getBoolean("isNameSelected"))
            startActivity(new Intent(this, MainActivity.class));
        else
            startActivity(new Intent(this, NameActivity.class));


        finishAffinity();

    }

}
