package com.example.homebook.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebook.DAO.DAO;
import com.example.homebook.R;
import com.example.homebook.adapter.HomeBookApdater;
import com.example.homebook.model.Room;
import com.example.homebook.model.categories;

import java.util.ArrayList;

public class FindFragment extends Fragment {
    categories category;
    ToggleButton toggle_hotel, toggle_homestays,toggle_apartment;
    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView, linear, polay;
    RelativeLayout visual1, visual2, visual3, visual4;
    FrameLayout layout;
    Boolean checkLogin;
    ArrayList<Room> list;
    TextView txtUsername;
    Boolean toggle_hotelischeck = true;

    //Drawer Menu

    RecyclerView recyclerView, recyclerView1;
    DAO dao;
    EditText edtSearch;
    Toolbar toolbar;
    String sqlRoom = "select * from room_tb";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        recyclerView = view.findViewById(R.id.ds_homebook);
        recyclerView1 = view.findViewById(R.id.ds_favo_homebook);
        toggle_hotel = view.findViewById(R.id.toggle_hotel);
        toggle_apartment = view.findViewById(R.id.toggle_apartment);
        toggle_homestays = view.findViewById(R.id.toggle_homestays);


        edtSearch = view.findViewById(R.id.edt_search);
        linear = view.findViewById(R.id.linear);
        layout = view.findViewById(R.id.layout_click);
        polay = view.findViewById(R.id.poLay);
//        toolbar = view.findViewById(R.id.toolbar);

        dao = new DAO(getContext());
        ArrayList<Room> list2 = (ArrayList<Room>) dao.getRoom(sqlRoom,null);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String timkiem = edtSearch.getText().toString().trim();
                String sql = " SELECT * FROM room_tb where location like '%" + timkiem + "%' or fullname like '%" + timkiem + "%'";
                if (!timkiem.isEmpty()) {
                    ArrayList<Room> list1 = (ArrayList<Room>) dao.getRoom(sql,null);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    HomeBookApdater homeBookApdater = new HomeBookApdater(getContext(), list1, getActivity());
                    recyclerView.setAdapter(homeBookApdater);
                } else {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    HomeBookApdater homeBookApdater = new HomeBookApdater(getContext(), list2, getActivity());
                    recyclerView.setAdapter(homeBookApdater);
                }
            }
        });

        loadDaTa();
        FilterHome();
//        list = (ArrayList<Room>) dao.getRoom("select*from room_tb",null);
//        for(int i=0;i<list.size();i++){
//            check(i);
//        }
        return view;
    }
//public void check( int position){
//
//    List<order> listoder= dao.getOrder("select * from order_tb where status = 1 and room_id = "+list.get(position).getId()+"");
//    if(list.get(position).getStatus()-listoder.size()==0){
//        layout.setVisibility(View.GONE);
//    }
//}
    //Navigation Drawer Functions
//    private void naviagtionDrawer() {
//
//        //Naviagtion Drawer
//        navigationView.bringToFront();
////        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
//        navigationView.setCheckedItem(R.id.Find);
//
//        menuIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (drawerLayout.isDrawerVisible(GravityCompat.START))
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                else drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
//
//        animateNavigationDrawer();
//
//    }


//    private void animateNavigationDrawer() {
//
//
//        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//
//                // Scale the View based on current slide offset
//                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
//                final float offsetScale = 1 - diffScaledOffset;
//                contentView.setScaleX(offsetScale);
//                contentView.setScaleY(offsetScale);
//                // Translate the View, accounting for the scaled width
//                final float xOffset = drawerView.getWidth() * slideOffset;
//                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
//                final float xTranslation = xOffset - xOffsetDiff;
//                contentView.setTranslationX(xTranslation);
//            }
//        });
//
//    }
//      navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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


    //    public void gethomebook(){
//        ArrayList<rooms> list = (ArrayList<rooms>) dao.getRoom1();
//        ArrayList<roomImage> list1 = (ArrayList<roomImage>) dao.getAllHinhAnh();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        HomeBookApdater homeBookApdater = new HomeBookApdater(getContext(),list,list1);
//        recyclerView.setAdapter(homeBookApdater);
//    }

    public void loadDaTa() {
        DAO dao = new DAO(getActivity());

        ArrayList<Room> listPopular = (ArrayList<Room>) dao.getRoom("select * from room_tb where rate = '5'",null);

        ArrayList<Room> list = (ArrayList<Room>) dao.getRoom(sqlRoom,null);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView1.setLayoutManager(linearLayoutManager1);

        HomeBookApdater homeBookApdater = new HomeBookApdater(getContext(), list, getActivity());
        HomeBookApdater homeBookApdater1 = new HomeBookApdater(getContext(), listPopular, getActivity());


        recyclerView.setAdapter(homeBookApdater);
        recyclerView1.setAdapter(homeBookApdater1);




    }
    private void FilterHome(){
        toggle_hotel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("TAG", "onCreateView: " + toggle_hotel);
                boolean to = true;
                if (toggle_hotel.isChecked() == true) {
//                    Toast.makeText(getContext(), "hotel", Toast.LENGTH_SHORT).show();
                    toggle_apartment.setChecked(false);
                    toggle_homestays.setChecked(false);
                    String sqlRoom = "select * from room_tb where category_name = 'Hotel'";
                    ArrayList<Room> listPopular = (ArrayList<Room>) dao.getRoom("select * from room_tb where rate = '5'",null);
                    ArrayList<Room> list = (ArrayList<Room>) dao.getRoom(sqlRoom,null);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView1.setLayoutManager(linearLayoutManager1);
                    HomeBookApdater homeBookApdater = new HomeBookApdater(getContext(), list, getActivity());
                    HomeBookApdater homeBookApdater1 = new HomeBookApdater(getContext(), listPopular, getActivity());
                    recyclerView.setAdapter(homeBookApdater);
                    recyclerView1.setAdapter(homeBookApdater1);
                }else {
                    loadDaTa();
                }


//                }



//                if(toggle_apartment.isChecked() == true){
//                    Toast.makeText(getContext(), "apartment", Toast.LENGTH_SHORT).show();
//                    toggle_hotel.setChecked(false);
//                    toggle_homestays.setChecked(false);
//                }
//                if(toggle_homestays.isChecked() == true){
//                    Toast.makeText(getContext(), "homestays", Toast.LENGTH_SHORT).show();
//                    toggle_hotel.setChecked(false);
//                    toggle_apartment.setChecked(false);
//                }


            }
        });

        toggle_apartment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("TAG", "onCreateView: " + toggle_hotel);
                boolean to = true;
                if(toggle_apartment.isChecked() == true){
//                    Toast.makeText(getContext(), "apartment", Toast.LENGTH_SHORT).show();
                    toggle_hotel.setChecked(false);
                    toggle_homestays.setChecked(false);

                    String sqlRoom = "select * from room_tb where category_name = 'Apartment'";
                    ArrayList<Room> listPopular = (ArrayList<Room>) dao.getRoom("select * from room_tb where rate = '5'",null);
                    ArrayList<Room> list = (ArrayList<Room>) dao.getRoom(sqlRoom,null);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView1.setLayoutManager(linearLayoutManager1);
                    HomeBookApdater homeBookApdater = new HomeBookApdater(getContext(), list, getActivity());
                    HomeBookApdater homeBookApdater1 = new HomeBookApdater(getContext(), listPopular, getActivity());
                    recyclerView.setAdapter(homeBookApdater);
                    recyclerView1.setAdapter(homeBookApdater1);
                }else {
                    loadDaTa();
                }


            }
        });
        toggle_homestays.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("TAG", "onCreateView: " + toggle_hotel);
                boolean to = true;
                if(toggle_homestays.isChecked() == true){
//                    Toast.makeText(getContext(), "homestays", Toast.LENGTH_SHORT).show();
                    toggle_hotel.setChecked(false);
                    toggle_apartment.setChecked(false);

                    String sqlRoom = "select * from room_tb where category_name = 'Homestay'";
                    ArrayList<Room> listPopular = (ArrayList<Room>) dao.getRoom("select * from room_tb where rate = '5'",null);
                    ArrayList<Room> list = (ArrayList<Room>) dao.getRoom(sqlRoom,null);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView1.setLayoutManager(linearLayoutManager1);
                    HomeBookApdater homeBookApdater = new HomeBookApdater(getContext(), list, getActivity());
                    HomeBookApdater homeBookApdater1 = new HomeBookApdater(getContext(), listPopular, getActivity());
                    recyclerView.setAdapter(homeBookApdater);
                    recyclerView1.setAdapter(homeBookApdater1);
                }else {
                    loadDaTa();
                }



            }
        });
    }
}
