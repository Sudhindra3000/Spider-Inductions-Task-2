package com.sudhindra.spiderinductionstask2.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.sudhindra.spiderinductionstask2.adapters.FragmentAdapter;
import com.sudhindra.spiderinductionstask2.databinding.ActivityMainBinding;
import com.sudhindra.spiderinductionstask2.fragments.PictureFragment;
import com.sudhindra.spiderinductionstask2.fragments.SearchFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ArrayList<Fragment> fragments;
    private FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragments = new ArrayList<>();
        fragments.add(new PictureFragment());
        fragments.add(new SearchFragment());

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());
        fragmentAdapter.setFragments(fragments);

        buildLayout();
    }

    private void buildLayout() {
        binding.viewPager.setAdapter(fragmentAdapter);

        TabLayoutMediator layoutMediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Picture of the Day");
                    break;
                case 1:
                    tab.setText("Search");
                    break;
            }
        });
        layoutMediator.attach();
    }
}