package com.example.academy_intern.clouddime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.academy_intern.clouddime.MyFragmentClass.Splash;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.show,new Splash()).commit();
   }
}

