package io.boringideas.utube;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class HomeContentActivity extends AppCompatActivity {

    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_content);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();

        if (intent.hasExtra("CATEGORY")) {
            category = intent.getExtras().getString("CATEGORY");
        }


        showFragment();
    }

    private void showFragment() {
        if(category != ""){

        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
