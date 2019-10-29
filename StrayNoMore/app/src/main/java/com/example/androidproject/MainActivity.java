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

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static String email;
    private String name;
    private String phone;
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    ConstraintLayout constraintLayout;
    private Intent data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = getIntent();

        constraintLayout = findViewById(R.id.main_constraintLayout);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    protected void onResume() {
        super.onResume();
        email = data.getStringExtra("email_id");
        name = data.getStringExtra("name");
        phone = data.getStringExtra("phone");
    }
    public static String getEmail(){
        return email;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
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
                try {
                    nintent.putExtra("email_id",email);
                    nintent.putExtra("name",name);
                    nintent.putExtra("phone",phone);
                    startActivity(nintent);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}