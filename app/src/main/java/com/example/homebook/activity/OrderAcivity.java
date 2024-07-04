package com.example.homebook.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.homebook.DAO.DAO;
import com.example.homebook.R;
import com.example.homebook.model.Favourite;
import com.example.homebook.model.Room;
import com.example.homebook.model.order;
import com.example.homebook.model.user;
import com.example.homebook.slideshow.The_Slide_Items_Model_Class;
import com.example.homebook.slideshow.adapter.The_Slide_Items_Pager_Adapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimerTask;

public class OrderAcivity extends AppCompatActivity {
    LinearLayout imgWifi, imgAC, imgParking, imgBuffet, imgPool, imgMinibar;
    TextView tvLocation, tvBeds, tvName, tvCategory, tvNote, tvDetails, tvCost;
    RatingBar ratingBar;
    private List<The_Slide_Items_Model_Class> listItems;
    private ViewPager page;
    private TabLayout tabLayout;
    EditText edtBookingDate, edtReturnDate;
    Date dateBooking;
    Date dateReturn;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    Button btnOrder;
    DAO dao;
    int id_room, status, cost, beds, rate, id_user;
    String name, category, note, location;
    String name_user, member_id;
    int dD, mM, yY, role;

    ToggleButton timF;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    String currentDate;
    byte[] img;
    boolean wifi, parking, buffet, ac, pool, minibar;
    //System.out.println(dateFormat.format(date));
    Button btnViewRate;
    int collaborate_id;

    TextInputEditText birthUp,emailUp,passUp,passUpAgain,nameUp,phoneUp;
    RadioButton radioCollaborate,radioMember;
    int role1;
    Button signUp;
    Date date1;
    TextView already,clickForDetails;
    CheckBox checkAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_room_detail);
        imgWifi = findViewById(R.id.img_wifi);
        imgAC = findViewById(R.id.img_ac);
        imgParking = findViewById(R.id.img_parking);
        imgPool = findViewById(R.id.img_pool);
        imgBuffet = findViewById(R.id.img_buffet);
        imgMinibar = findViewById(R.id.img_minibar);
        tvBeds = findViewById(R.id.tv_beds_order);
        tvLocation = findViewById(R.id.tv_location_order);
        tvName = findViewById(R.id.hotel_name_roomsdetail);
        tvCategory = findViewById(R.id.tv_category_order);
//        tvDetails = findViewById(R.id.tv_details_order);
        tvNote = findViewById(R.id.tv_note_order);
        tvCost = findViewById(R.id.tv_cost_order);
        ratingBar = findViewById(R.id.number_stars);
        edtBookingDate = findViewById(R.id.edt_bookingdate);
        edtReturnDate = findViewById(R.id.edt_returndate);
        btnOrder = findViewById(R.id.btn_order);

        timF = findViewById(R.id.timFavourite);
        btnViewRate = findViewById(R.id.btn_viewrate);
        //
        currentDate = dateFormat.format(date);
        //

        dao = new DAO(this);
        Calendar calendar = Calendar.getInstance();
        yY = calendar.get(Calendar.YEAR);
        mM = calendar.get(Calendar.MONTH);
        dD = calendar.get(Calendar.DATE);
        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("bundle");
        if (bundle != null) {
//            name = bundle.getString("name");
//            category = bundle.getString("category");
//            note = bundle.getString("note");
            id_room = bundle.getInt("id");
            Room room = dao.get1Room("select * from room_tb where id = ?", String.valueOf(id_room));
//            location = bundle.getString("location");
//            status = bundle.getInt("status");
//            beds = bundle.getInt("beds");
//            cost = bundle.getInt("cost");
//            rate = bundle.getInt("rate");
//            byte[] img = bundle.getByteArray("img");
//            boolean wifi = false;
//            boolean parking = bundle.getBoolean("parking");
//            boolean pool = bundle.getBoolean("pool");
//            boolean minibar = bundle.getBoolean("minibar");
//            boolean ac = bundle.getBoolean("ac");
//            boolean buffet = true;
            name = room.getName();
            category = room.getCategory();
            note = room.getNote();
            location = room.getLocation();
            status = room.getStatus();
            beds = room.getBeds();
            cost = room.getCost();
            rate = room.getRate();
            img = room.getIMG();
            wifi = room.isWifi();
            ac = room.isAc();
            pool = room.isPool();
            parking = room.isParking();
            buffet = room.isBuffet();
            minibar = room.isMinibar();
            tvLocation.setText(location);
            switch (room.getBeds()) {
                case 0:
                    tvBeds.setText("Single room");
                    break;
                case 1:
                    tvBeds.setText("Twin room");
                    break;
                case 2:
                    tvBeds.setText("Double room");
                    break;
                case 3:
                    tvBeds.setText("Triple room");
                    break;
                case 4:
                    tvBeds.setText("Quad room");
                    break;
            }
            tvName.setText(name);
            ratingBar.setRating(rate);
            tvCategory.setText(category);
            tvNote.setText(note);
//            tvDetails.setText(String.valueOf(status));
            tvCost.setText(String.valueOf(cost));
            if (wifi == true) {
                imgWifi.setVisibility(View.VISIBLE);
            } else {
                imgWifi.setVisibility(View.GONE);
            }
            if (ac == true) {
                imgAC.setVisibility(View.VISIBLE);
            } else {
                imgAC.setVisibility(View.GONE);
            }
            if (parking == true) {
                imgParking.setVisibility(View.VISIBLE);
            } else {
                imgParking.setVisibility(View.GONE);
            }
            if (pool == true) {
                imgPool.setVisibility(View.VISIBLE);
            } else {
                imgPool.setVisibility(View.GONE);
            }
            if (minibar == true) {
                imgMinibar.setVisibility(View.VISIBLE);
            } else {
                imgMinibar.setVisibility(View.GONE);
            }
            if (buffet == true) {
                imgBuffet.setVisibility(View.VISIBLE);
            } else {
                imgBuffet.setVisibility(View.GONE);
            }
            collaborate_id = room.getCollaborate_id();
        }

        SharedPreferences sP = getSharedPreferences("User_File", MODE_PRIVATE);
        String email = sP.getString("Email", "");
        String pass = sP.getString("Password", "");

        if (dao.checkLogin(email, pass)) {
            user x = dao.get1User("select * from user_tb where email = ?", email);
            if (dao.checkFavourite(id_room + "", x.getId() + "")) {
                timF.setChecked(true);
            } else {
                timF.setChecked(false);
            }
        } else {
            timF.setChecked(false);
        }

        timF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Room r = dao.get1Room("select * from room_tb where id = ?", id_room + "");
                if (email.equals("") || pass.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderAcivity.this);
                    builder.setMessage("Please login!");
                    builder.setCancelable(true);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    timF.setChecked(false);
                } else {
                    user u = dao.get1User("select * from user_tb where email = ?", email);
                    Favourite f = new Favourite(r.getId(), u.getId());
                    if (timF.isChecked()) {
                        dao.AddFavourite(f);
                        Toast.makeText(OrderAcivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                        Log.d("add", "Thêm thành công");
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderAcivity.this);
                        builder.setMessage("Bạn có muốn xóa không?");
                        builder.setCancelable(true);
                        builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Favourite fa = dao.get1Favourite(r.getId() + "", u.getId() + "");
                                dao.DeleteFavourite(fa.getId());
                                Toast.makeText(OrderAcivity.this, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                timF.setChecked(false);
                            }
                        });
                        builder.setNegativeButton("KO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                timF.setChecked(true);
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        Log.d("add", "Xóa thành công");
                    }
                }
            }
        });

//        timF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Room r = dao.get1Room("select * from room_tb where id = ?",id_room+"");
//                if(email.equals("") || pass.equals("")){
//
//                }else{
//                    user u = dao.get1User("select * from user_tb where email = ?", email);
//                    id_user = u.getId();
//                    Favourite f = new Favourite(r.getId(),id_user);
//                    if(isChecked){
//                        dao.AddFavourite(f);
//                    }else{
//                        favouriteFragment.showDialogXoa(r.getId()+"",id_user+"" );
//                    }
//                }
//            }
//        });

        page = findViewById(R.id.my_pager);
        tabLayout = findViewById(R.id.my_tablayout);

        listItems = new ArrayList<>();
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.khachsan1, "Slider 1 Title"));
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.khachsan2, "Slider 2 Title"));
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.khachsan3, "Slider 3 Title"));

        The_Slide_Items_Pager_Adapter itemsPager_adapter = new The_Slide_Items_Pager_Adapter(this, listItems);
        page.setAdapter(itemsPager_adapter);

        // The_slide_timer
        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new The_slide_timer(), 2000, 3000);
        tabLayout.setupWithViewPager(page, true);

        edtBookingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog.OnDateSetListener chonDate = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        int dD, mM, yY, role;
                        yY = year;
                        mM = month;
                        dD = dayOfMonth;
                        GregorianCalendar gC = new GregorianCalendar(yY, mM, dD);
                        edtBookingDate.setText(format.format(gC.getTime()));
                        try {
                            dateBooking = format.parse(format.format(gC.getTime()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                };

                DatePickerDialog d = new DatePickerDialog(OrderAcivity.this, 0, chonDate, yY, mM, dD);
                d.show();
            }
        });
        edtReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog.OnDateSetListener chonDate = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                        yY = year;
                        mM = month;
                        dD = dayOfMonth;
                        GregorianCalendar gC = new GregorianCalendar(yY, mM, dD);
                        edtReturnDate.setText(format.format(gC.getTime()));
                        try {
                            dateReturn = format.parse(format.format(gC.getTime()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                };

                DatePickerDialog d = new DatePickerDialog(OrderAcivity.this, 0, chonDate, yY, mM, dD);
                d.show();
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sethorizontal();

            }
        });
        btnViewRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialongViewRate();
                Intent intent = new Intent(OrderAcivity.this, ViewRateActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("room_id", id_room);
                intent.putExtra("bundle", bundle1);
                startActivity(intent);

            }
        });
    }

    public class The_slide_timer extends TimerTask {
        @Override
        public void run() {

            OrderAcivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (page.getCurrentItem() < listItems.size() - 1) {
                        page.setCurrentItem(page.getCurrentItem() + 1);
                    } else
                        page.setCurrentItem(0);
                }
            });
        }
    }

    private void sethorizontal() {
        SharedPreferences sP = getSharedPreferences("User_File", MODE_PRIVATE);
        String email = sP.getString("Email", "");
        String pass = sP.getString("Password", "");
        if (email.equals("") || pass.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please login!");
            builder.setCancelable(true);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.activity_login, null);
            builder.setView(view);
            AlertDialog alertDialog = builder.create();

            TextInputEditText emailIn = view.findViewById(R.id.emailIn);
            TextInputEditText passIn = view.findViewById(R.id.passIn);
            Button signIn = view.findViewById(R.id.signIn);
//            remember = view.findViewById(R.id.rememberBox);
            TextView forget = view.findViewById(R.id.quenMatKhau);
            TextView create = view.findViewById(R.id.dangky);

            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String eI = emailIn.getText().toString();
                    String pI = passIn.getText().toString();
                    Boolean check = true;
                    if (eI.trim().length() <= 0) {
                        check = false;
                        emailIn.setError("Enter your email.");
                    }
                    if (pI.trim().length() <= 0) {
                        check = false;
                        passIn.setError("Enter your password.");
                    }
                    if (check) {
                        if (dao.checkLogin(eI, pI)) {
                            Toast.makeText(OrderAcivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Log.d("ok", "OK");
                            SharedPreferences.Editor edit = sP.edit();
                            edit.putString("Email", eI);
                            edit.putString("Password", pI);
                            edit.commit();

//                        startActivity(new Intent(getActivity(), MainActivity.class));
                            Intent refresh = new Intent(OrderAcivity.this, BottomNavActivity.class);
                            startActivity(refresh);
                            OrderAcivity.this.overridePendingTransition(0, 0);
                            OrderAcivity.this.finish();
                        } else {
                            Toast.makeText(OrderAcivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            Log.d("ok", "KO OK");
                        }
                    }
                }
            });

            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderAcivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.activity_register, null);
                    builder.setView(view);
                    AlertDialog alertDialog1 = builder.create();

                    signUp = view.findViewById(R.id.signUp);
                    emailUp = view.findViewById(R.id.emailUp);
                    passUp = view.findViewById(R.id.passUp);
                    passUpAgain = view.findViewById(R.id.passUpAgain);
                    nameUp = view.findViewById(R.id.nameUp);
                    phoneUp = view.findViewById(R.id.phoneUp);
                    birthUp = view.findViewById(R.id.birthUp);
                    checkAccept = view.findViewById(R.id.checkAccept);
                    clickForDetails = view.findViewById(R.id.clickForDetails);
                    radioCollaborate = view.findViewById(R.id.collaborateUP);
                    radioMember = view.findViewById(R.id.memberUp);
                    dao = new DAO(OrderAcivity.this);

                    birthUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatePickerDialog.OnDateSetListener chonDate = new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    yY = year; mM = month; dD = dayOfMonth;
                                    GregorianCalendar gC = new GregorianCalendar(yY,mM,dD);
                                    birthUp.setText(format.format(gC.getTime()));
                                    date1 = gC.getTime();
                                }
                            };

                            Calendar calendar = Calendar.getInstance();
                            yY = calendar.get(Calendar.YEAR);
                            mM = calendar.get(Calendar.MONTH);
                            dD = calendar.get(Calendar.DATE);

                            DatePickerDialog d = new DatePickerDialog(OrderAcivity.this,0,chonDate,yY,mM,dD);
                            d.show();
                        }
                    });

                    signUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String email = emailUp.getText().toString();
                            String pass = passUp.getText().toString();
                            String passAgain = passUpAgain.getText().toString();
                            String name = nameUp.getText().toString();
                            String phone = phoneUp.getText().toString();
                            String birth = birthUp.getText().toString();
                            Animation animShake = AnimationUtils.loadAnimation(OrderAcivity.this, R.anim.shake);

                            Boolean check = true;
                            role1 = 1;
                            int ava = 0,money = 0;
                            birthUp.setError(null);

                            if(!radioCollaborate.isChecked() && !radioMember.isChecked()){
                                check = false;
                                Toast.makeText(OrderAcivity.this,"Choose your role",Toast.LENGTH_SHORT).show();
                                radioCollaborate.setAnimation(animShake);
                                radioMember.setAnimation(animShake);
                            }
                            if(!checkAccept.isChecked()){
                                check = false;
                                Toast.makeText(OrderAcivity.this,"Accept the license",Toast.LENGTH_SHORT).show();
                                checkAccept.setAnimation(animShake);
                            }
                            if(email.trim().length() <= 0){
                                check = false;
                                emailUp.setError("Enter the email.");
                            }
                            if(pass.trim().length() <= 0){
                                check = false;
                                passUp.setError("Enter the password.");
                            }
                            if(passAgain.trim().length() <= 0){
                                check = false;
                                passUpAgain.setError("Enter the password again.");
                            }
                            if(birth.trim().length() <= 0){
                                check = false;
                                birthUp.setError("Enter the birthday.");
                            }
                            if(name.trim().length() <= 0){
                                check = false;
                                nameUp.setError("Enter your name.");
                            }
                            if(phone.trim().length() <= 0){
                                check = false;
                                phoneUp.setError("Enter your phone number.");
                            }
                            else if(!passAgain.equals(pass)){
                                check = false;
                                passUpAgain.setError("The password again is not same as the password.");
                            }
                            else if(!email.matches("\\w+@\\w+\\.\\w{1,5}")){
                                check = false;
                                emailUp.setError("Email không đúng định dạng.");
                            }
                            else if(dao.checkEmail(email)){
                                check = false;
                                emailUp.setError("Email exists.");
                            }
                            if(check == true){
                                if(radioCollaborate.isChecked()){
                                    role1 = 0;
                                }else if(radioMember.isChecked()){
                                    role1 = 1;
                                }
                                Log.d("User","ADD OK");
                                user x = new user(ava,name,email,pass,date1,phone,role1,money);
                                dao.AddUser(x);
                                Toast.makeText(OrderAcivity.this,"Add completed",Toast.LENGTH_SHORT).show();

                                SharedPreferences.Editor edit = sP.edit();
                                edit.putString("Email", email);
                                edit.putString("Password", pass);
                                edit.commit();

//                        startActivity(new Intent(getActivity(), MainActivity.class));
                                Intent refresh = new Intent(OrderAcivity.this, BottomNavActivity.class);
                                startActivity(refresh);
                                OrderAcivity.this.overridePendingTransition(0, 0);
                                OrderAcivity.this.finish();

                                alertDialog1.dismiss();
                                alertDialog.dismiss();
                            }
                        }
                    });

                    alertDialog1.show();
                }
            });

            alertDialog.show();
        } else {

            dao = new DAO(this);
            if (dao.checkLogin(email, pass)) {
                user x = dao.get1User("select * from user_tb where email = ?", email);
                name_user = x.getFullname();
                id_user = x.getId();
                if (id_user == collaborate_id) {
                    DialongCheckAdd(email, pass);
                } else {
                    if (!edtReturnDate.getText().toString().equals("") && !edtBookingDate.getText().toString().equals("")) {
                        if (date.compareTo(dateBooking) > 0) {
                            Toast.makeText(OrderAcivity.this, "Order date must be after current date", Toast.LENGTH_SHORT).show();
                            DialogNotification("Order date must be after current date");
                        } else if (date.compareTo(dateReturn) > 0) {
                            Toast.makeText(OrderAcivity.this, "Payment date must be after current date", Toast.LENGTH_SHORT).show();
                            DialogNotification("Payment date must be after current date");
                        } else if (dateBooking.compareTo(dateReturn) >= 0) {
                            Toast.makeText(OrderAcivity.this, "Order date must be before payment date", Toast.LENGTH_SHORT).show();
                            DialogNotification("Order date must be before payment date");
                        } else {
                            DAO dao = new DAO(OrderAcivity.this);
                            if (dao.checkLogin(email, pass)) {
                                user x1 = dao.get1User("select * from user_tb where email = ?", email);
                                name_user = x1.getFullname();
                                id_user = x1.getId();

                                long diff = dateReturn.getTime() - dateBooking.getTime();
                                int dayCount = (int) diff / (24 * 60 * 60 * 1000);

                                if (x1.getMoney() >= (cost * dayCount)) {
                                    dao.AddOrder(new order(id_user, 5, dateBooking, dateReturn, "a", "b", id_room, note, 0, (cost * dayCount)));
                                    Toast.makeText(OrderAcivity.this, "Order was successful. Deposit 5% money.", Toast.LENGTH_SHORT).show();
                                    x1.setMoney(x1.getMoney() - ((cost * dayCount) * 5 / 100));
                                    dao.UpdateUser(x1);
                                    startActivity(new Intent(OrderAcivity.this, BottomNavActivity.class));
                                } else {
                                    Toast.makeText(OrderAcivity.this, "Not enough money", Toast.LENGTH_SHORT).show();
                                    DialogNotification("Not enough money");
                                }
                            }
                        }
                    } else {
                        Toast.makeText(OrderAcivity.this, "Don't leave the booking date and return date blank", Toast.LENGTH_SHORT).show();
                        DialogNotification("Don't leave the booking date and return date blank");
                    }
                }


            }
        }
    }

//    public void DialongViewRate() {
//        Dialog builder = new Dialog(this);
////        View alert = LayoutInflater.from(this).inflate(R.layout.layout_viewrate,null);
//        builder.setContentView(R.layout.layout_viewrate);
//        RecyclerView recyclerView = builder.findViewById(R.id.ds_rate);
//        DAO dao = new DAO(this);
//        List<rating> list = dao.GetAllRating("select * from rating_tb where room_id =" + id_room + "");
//        if (list.size() > 0) {
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//            recyclerView.setLayoutManager(linearLayoutManager);
//            RatingAdapter homeBookApdater = new RatingAdapter(OrderAcivity.this, (ArrayList<rating>) list);
//            recyclerView.setAdapter(homeBookApdater);
//        } else {
////            LinearLayout layout = builder.findViewById(R.id.layout_viewrate);
////            layout.setBackgroundResource(R.drawable.m4_cat);
//            builder.dismiss();
//            DialongNull();
//        }
////        builder.create();
//        builder.show();
//    }

    //nó vẫn k full mà =)) thì có mỗi 1 thg đánh giá chứ nhiêu
    public void DialongCheckAdd(String email, String pass) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        View alert = LayoutInflater.from(this).inflate(R.layout.layout_viewrate,null);
//        builder.setContentView(R.layout.layout_viewrate);
        builder.setMessage("Do you agree to try the rental?");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!edtReturnDate.getText().toString().equals("") && !edtBookingDate.getText().toString().equals("")) {
                    if (date.compareTo(dateBooking) > 0) {
                        Toast.makeText(OrderAcivity.this, "Order date must be after current date", Toast.LENGTH_SHORT).show();
                        DialogNotification("Order date must be after current date");
                    } else if (date.compareTo(dateReturn) > 0) {
                        Toast.makeText(OrderAcivity.this, "Payment date must be after current date", Toast.LENGTH_SHORT).show();
                        DialogNotification("Payment date must be after current date");
                    } else if (dateBooking.compareTo(dateReturn) >= 0) {
                        Toast.makeText(OrderAcivity.this, "Order date must be before payment date", Toast.LENGTH_SHORT).show();
                        DialogNotification("Order date must be before payment date");
                    } else {
                        DAO dao = new DAO(OrderAcivity.this);
                        if (dao.checkLogin(email, pass)) {
                            user x1 = dao.get1User("select * from user_tb where email = ?", email);
                            name_user = x1.getFullname();
                            id_user = x1.getId();

                            long diff = dateReturn.getTime() - dateBooking.getTime();
                            int dayCount = (int) diff / (24 * 60 * 60 * 1000);

                            if (x1.getMoney() >= (cost * dayCount)) {
                                dao.AddOrder(new order(id_user, 5, dateBooking, dateReturn, "a", "b", id_room, note, 0, (cost * dayCount)));
                                Toast.makeText(OrderAcivity.this, "Order was successful. Deposit 5% money.", Toast.LENGTH_SHORT).show();
                                x1.setMoney(x1.getMoney() - ((cost * dayCount) * 5 / 100));
                                dao.UpdateUser(x1);
                                startActivity(new Intent(OrderAcivity.this, BottomNavActivity.class));
                            } else {
                                Toast.makeText(OrderAcivity.this, "Not enough money", Toast.LENGTH_SHORT).show();
                                DialogNotification("Not enough money");
                            }
                        }
                    }
                } else {
                    Toast.makeText(OrderAcivity.this, "Don't leave the booking date and return date blank", Toast.LENGTH_SHORT).show();
                    DialogNotification("Don't leave the booking date and return date blank");
                }
            }
        });
        builder.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.create();
        alertDialog.show();
    }

    public void DialogNotification(String notification) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        View alert = LayoutInflater.from(this).inflate(R.layout.layout_viewrate,null);
//        builder.setContentView(R.layout.layout_viewrate);
        builder.setMessage(notification);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.create();
        alertDialog.show();
    }

}

