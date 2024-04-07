package com.example.chatapp.Frontend.Supervisor;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.chatapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class SupervisorActionsActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    SupervisorActionsPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_supervisor_actions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.view_pager);
        adapter = new SupervisorActionsPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Add listener to handle tab selection
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // When a tab is selected, set the current item of the ViewPager2 to the selected tab position
                viewPager.setCurrentItem(tab.getPosition());
            }

            // Automatically created these 2 methods as well but wtv dont need em (have them because the thing goes red)
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // when a page is selected in the ViewPager2, select the corresponding tab in the TabLayout
                // Did the Objects.requireNull by itself for some reason
                Objects.requireNonNull(tabLayout.getTabAt(position)).select();
            }
        });

    }
}