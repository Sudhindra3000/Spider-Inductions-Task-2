package com.sudhindra.spiderinductionstask2.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.sudhindra.spiderinductionstask2.databinding.ActivityDetailsBinding;
import com.sudhindra.spiderinductionstask2.models.SearchItem;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    private SearchItem.Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        data = new Gson().fromJson(getIntent().getStringExtra("data"), SearchItem.Data.class);
        binding.setData(data);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
