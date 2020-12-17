package com.coldfier.myfinancemanager2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private CardsCollectionFragment collectionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        collectionFragment = new CardsCollectionFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_container, collectionFragment).commit();
    }
}