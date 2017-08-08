package com.nutricampus.app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.nutricampus.app.R;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
            voltarActivity();

        return true;
    }

    @Override
    public void onBackPressed(){
        voltarActivity();
    }

    private void voltarActivity() {
        Intent it = new Intent(
                ConfigActivity.this, MainActivity.class);

        startActivity(it);
        finish();
    }
}
