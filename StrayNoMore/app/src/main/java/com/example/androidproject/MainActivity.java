package com.example.androidproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.androidproject.ui.main.SectionsPagerAdapter;
import com.example.androidproject.ViewPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int requestCode = 100;
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = findViewById(R.id.main_constraintLayout);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.add(new FragmentsBots(),"Bots");
        adapter.add(new FragmentCam(), "Camera");
        adapter.add(new FragmentAdopt(), "Adopt");
        adapter.add(new FragmentDonation(), "Donation");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                Toast.makeText(this, "You Clicked Logout",Toast.LENGTH_LONG).show();
                //Snackbar.make(constraintLayout,"You Logged Out",Snackbar.LENGTH_LONG).show();
                return true;
            case R.id.account_menu:
                Intent nintent = new Intent(this, MyAccount.class);
                startActivity(nintent);
                Toast.makeText(this, "You Clicked Account",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}