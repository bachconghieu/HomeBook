package com.example.homebook.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.homebook.DAO.DAO;
import com.example.homebook.R;
import com.example.homebook.fragment.FavouriteFragment;
import com.example.homebook.fragment.FindFragment;
import com.example.homebook.fragment.Fragment3;
import com.example.homebook.fragment.TabCartFragment;
import com.example.homebook.fragment.fragmentNav.AcountFragment;
import com.example.homebook.fragment.fragmentNav.RateFragment;
import com.example.homebook.fragment.fragmentNav.SettingFragment;
import com.example.homebook.model.user;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BottomNavActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    ImageView menuIcon;
    LinearLayout linear, polay;
    FrameLayout contentView;
    RelativeLayout layout;
    TextView txtUsername;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    String name;
    ImageView avatar;

    static final float END_SCALE = 0.7f;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bottom_nav);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.frame);
        layout = findViewById(R.id.information);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        txtUsername = findViewById(R.id.txtUsername);
        avatar = findViewById(R.id.avaImg);

        SharedPreferences sP = getSharedPreferences("User_File", MODE_PRIVATE);
        String email = sP.getString("Email", "");
        String pass = sP.getString("Password", "");
        //
        View header = navigationView.getHeaderView(0);
        TextView txtUsernameHeader = header.findViewById(R.id.txtviewUsername);
        TextView txtCurrentDate = header.findViewById(R.id.tv_currentdate);
        ImageView imageAVT = header.findViewById(R.id.imageAvtar);
        //
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        navigationView.setVisibility(View.GONE);
        menuIcon.setVisibility(View.GONE);

        DAO dao = new DAO(BottomNavActivity.this);
        if (dao.checkLogin(email, pass)) {
            Date date = new Date();
            String dataFo1 = dateFormat.format(date);
            user x = dao.get1User("select * from user_tb where email = ?", email);
            name = x.getFullname();
            txtUsername.setText(name);
            txtUsernameHeader.setText(name);
            txtCurrentDate.setText(dataFo1);
            if (x.getAvatar() == 0) {
                avatar.setImageResource(R.drawable.usermanage);
                imageAVT.setImageResource(R.drawable.usermanage);
            } else {
                avatar.setImageResource(x.getAvatar());
                imageAVT.setImageResource(x.getAvatar());
            }

            int role = 0;
            if (x.getRole() == role) {
                navigationView.setVisibility(View.VISIBLE);
                menuIcon.setVisibility(View.VISIBLE);
            }
        }

        final Fragment fragment1 = new FindFragment();
        final Fragment fragment2 = new FavouriteFragment();
        final Fragment fragment3 = new Fragment3();
        final Fragment fragment4 = new TabCartFragment();

        naviagtionDrawer();

        sethorizontal();

        replaceFragment(new FindFragment());

        bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.Find:
                        navigationView.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);
                        replaceFragment(fragment1);
                        Intent refresh = new Intent(BottomNavActivity.this, BottomNavActivity.class);
                        startActivity(refresh);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.Cart:
                        navigationView.setVisibility(View.GONE);
                        menuIcon.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);
                        replaceFragment(fragment4);
//                        Intent refresh = new Intent(BottomNavActivity.this, BottomNavActivity.class);
//                        startActivity(refresh);
//                        overridePendingTransition(0, 0);
//                        finish();
                        break;

                    case R.id.Music:
                        navigationView.setVisibility(View.GONE);
                        menuIcon.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);
                        replaceFragment(fragment2);
                        break;

                    case R.id.Account:
                        layout.setVisibility(View.GONE);
                        replaceFragment(fragment3);
                        break;

                    default:
                        layout.setVisibility(View.VISIBLE);
                        replaceFragment(fragment1);
                        break;
                }
                return true;
            }
        });
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.mHome:
//                        Fragment fragment = new HomeFragment();
//                        FragmentManager fragmentManager = getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.frame, fragment).commit();
//                        drawerLayout.close();
//                        break;
//                    case R.id.mAcount:
//                        Fragment fragment1 = new AcountFragment();
//                        FragmentManager fragmentManager1 =getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
//                        fragmentTransaction1.replace(R.id.frame, fragment1).commit();
//                        drawerLayout.close();
//                        break;
//
//                    case R.id.mRate:
//                        Fragment fragment2 = new RateFragment();
//                        FragmentManager fragmentManager2 = getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
//                        fragmentTransaction2.replace(R.id.frame, fragment2).commit();
//                        drawerLayout.close();
//                        break;
//                    case R.id.mSetting:
//                        Fragment fragment3 = new SettingFragment();
//                        FragmentManager fragmentManager3 = getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
//                        fragmentTransaction3.replace(R.id.frame, fragment3).commit();
//                        drawerLayout.close();
//                        break;
////                    case R.id.back:
//////                        Intent intent = new Intent(MainActivity.this, SensorService.class);
//////                        startService(intent);
//////                        finish();
////                        break;
//                }
//                return false;
//            }
//        });

    }

    private void sethorizontal() {
        SharedPreferences sP = getSharedPreferences("User_File", MODE_PRIVATE);
        String email = sP.getString("Email", "");
        String pass = sP.getString("Password", "");
        DAO dao = new DAO(BottomNavActivity.this);
        if (dao.checkLogin(email, pass)) {
            user x = dao.get1User("select * from user_tb where email = ?", email);
            name = x.getFullname();
            txtUsername.setText(name);
        }
    }

    private void naviagtionDrawer() {

        //Naviagtion Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.Find);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();

    }

    private void animateNavigationDrawer() {


        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);
                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mHome:
                Fragment fragment = new FindFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment).commit();
                drawerLayout.close();
                break;
            case R.id.mAcount:
                Fragment fragment1 = new AcountFragment();
                FragmentManager fragmentManager1 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                fragmentTransaction1.replace(R.id.frame, fragment1).commit();
                drawerLayout.close();
                break;

            case R.id.mRate:
                Fragment fragment2 = new RateFragment();
                FragmentManager fragmentManager2 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.replace(R.id.frame, fragment2).commit();
                drawerLayout.close();
                break;
            case R.id.mSetting:
                Fragment fragment3 = new SettingFragment();
                FragmentManager fragmentManager3 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                fragmentTransaction3.replace(R.id.frame, fragment3).commit();
                drawerLayout.close();
                break;
//                    case R.id.back:
////                        Intent intent = new Intent(MainActivity.this, SensorService.class);
////                        startService(intent);
////                        finish();
//                        break;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sP = getSharedPreferences("User_File", MODE_PRIVATE);
        String email = sP.getString("Email", "");
        String pass = sP.getString("Password", "");
    }
}