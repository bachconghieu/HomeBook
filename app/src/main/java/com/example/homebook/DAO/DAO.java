package com.example.homebook.DAO;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.homebook.database.AppSQL;
import com.example.homebook.model.DateCurrent;
import com.example.homebook.model.Favourite;
import com.example.homebook.model.NapThe;
import com.example.homebook.model.Room;
import com.example.homebook.model.admin;
import com.example.homebook.model.order;
import com.example.homebook.model.rating;
import com.example.homebook.model.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAO {
    private SQLiteDatabase db;
    AppSQL appSQL;
    private Context context;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public DAO(Context context) {
        this.context = context;
        appSQL = new AppSQL(context);
        db = appSQL.getWritableDatabase();
    }
    /////////////////////////////////////////////////////////////////////

    public boolean checkLogin(String email, String pass) {
        String sql = "SELECT * FROM user_tb WHERE email=? and password=?";
        db = appSQL.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{email, pass});
        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }

    public boolean checkAdmin(String user, String pass) {
        String sql = "SELECT * FROM adminstrator_tb WHERE username = ? and password = ?";
        db = appSQL.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{user, pass});
        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }

    public boolean checkEmail(String email) {
        String sql = "SELECT * FROM user_tb WHERE email=?";
        db = appSQL.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{email});
        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }

    public admin get1Admin(String x, String y) {
        List<admin> list = new ArrayList<>();
        Cursor c = db.rawQuery("select * from adminstrator_tb WHERE username = ? and password = ?", new String[]{x, y});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String user = c.getString(0);
            String pass = c.getString(1);
            int money = c.getInt(2);
            admin ad = new admin(user, pass, money);
            list.add(ad);
            c.moveToNext();
        }
        c.close();
        return list.get(0);
    }

//    public List<user> getUser_name (String email, String pass) {
//        List<user> list = new ArrayList<>();
//        String sql = "SELECT * FROM user_tb WHERE email=? and password=?";
//        db = appSQL.getReadableDatabase();
//        Cursor c = db.rawQuery(sql, new String[]{email, pass});
//        c.moveToFirst();
//        while (!c.isAfterLast()) {
//            int id = c.getInt(0);
//            int ava = c.getInt(1);
//            String name = c.getString(2);
//            String Email = c.getString(3);
//            String Pass = c.getString(4);
//            int role = c.getInt(5);
//            Date ngay = null;
//            try {
//                ngay = format.parse(c.getString(6));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            String phone = c.getString(7);
//            int money = c.getInt(8);
//            user x = new user(id, ava, name, Email, Pass, ngay, phone, role, money);
//            list.add(x);
//            c.moveToNext();
//        }
//        c.close();
//        return list;
////        if (cursor.getCount() != 0) {
////            return true;
////        }
////        return false;
//    }

    public List<Favourite> getFavourite(String user) {
        List<Favourite> list = new ArrayList<>();
        Cursor c = db.rawQuery("select * from room_favourite_tb where user_id = ?", new String[]{user});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(0);
            int room_id = c.getInt(1);
            int user_id = c.getInt(2);
            Favourite x = new Favourite(id, room_id, user_id);
            list.add(x);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public Favourite get1Favourite(String room, String user) {
        List<Favourite> list = new ArrayList<>();
        Cursor c = db.rawQuery("select * from room_favourite_tb where room_id = ? and user_id = ?", new String[]{room, user});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(0);
            int room_id = c.getInt(1);
            int user_id = c.getInt(2);
            Favourite x = new Favourite(id, room_id, user_id);
            list.add(x);
            c.moveToNext();
        }
        c.close();
        return list.get(0);
    }

    public boolean checkFavourite(String room, String user) {
        db = appSQL.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from room_favourite_tb where room_id = ? and user_id = ?", new String[]{room, user});
        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }

    public long AddFavourite(Favourite x) {
        ContentValues value = new ContentValues();
        value.put("room_id", x.getRoom_id());
        value.put("user_id", x.getUser_id());
        return db.insert("room_favourite_tb", null, value);
    }

    public void DeleteFavourite(int ID) {
        db.delete("room_favourite_tb", "id=?", new String[]{String.valueOf(ID)});
    }
    public void DeleteFavourite2(int ID) {
        db.delete("room_favourite_tb", "room_id=?", new String[]{String.valueOf(ID)});
    }

    public List<user> getUser(String sql, String... args) {
        List<user> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, args);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(0);
            int ava = c.getInt(1);
            String name = c.getString(2);
            String email = c.getString(3);
            String pass = c.getString(4);
            int role = c.getInt(5);
            Date ngay = null;
            try {
                ngay = format.parse(c.getString(6));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String phone = c.getString(7);
            int money = c.getInt(8);
            user x = new user(id, ava, name, email, pass, ngay, phone, role, money);
            list.add(x);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public user get1User(String sql, String x) {
        List<user> list = getUser(sql, x);
        return list.get(0);
    }


    public long AddUser(user x) {
        ContentValues value = new ContentValues();
        value.put("avatar", x.getAvatar());
        value.put("fullname", x.getFullname());
        value.put("email", x.getEmail());
        value.put("password", x.getPassword());
        value.put("role", x.getRole());
        value.put("birthday", format.format(x.getBirth_day()));
        value.put("phonenumber", x.getPhone());
        value.put("money", x.getMoney());
        return db.insert("user_tb", null, value);
    }

    public long UpdateUser(user x) {
        ContentValues value = new ContentValues();
        value.put("avatar", x.getAvatar());
        value.put("fullname", x.getFullname());
        value.put("email", x.getEmail());
        value.put("password", x.getPassword());
        value.put("role", x.getRole());
        value.put("birthday", format.format(x.getBirth_day()));
        value.put("phonenumber", x.getPhone());
        value.put("money", x.getMoney());
        return db.update("user_tb", value, "id=?", new String[]{String.valueOf(x.getId())});
    }

    public void DeleteUser(int ID) {
        db.delete("user_tb", "id=?", new String[]{String.valueOf(ID)});
    }

//    public ArrayList<Room> Search(String location1) {
//        String sql = " SELECT * FROM room_tb where location like '%" + location1 + "%'";
//        getRoom(sql);
//        return null;
//    }

    public List<Room> getRoom(String sql, String... args) {
        List<Room> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, args);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            String category = c.getString(2);
            String location = c.getString(3);
            int rate = c.getInt(4);
            int beds = c.getInt(5);
            String note = c.getString(15);
            int cost = c.getInt(12);
            int status = c.getInt(13);
            int wf = c.getInt(6);
            int aC = c.getInt(7);
            int parKing = c.getInt(8);
            int miniBar = c.getInt(9);
            int Pool = c.getInt(10);
            int Buffet = c.getInt(11);
            int collaborate_id = c.getInt(16);
//            int people = c.getInt(c.getColumnIndex("number_people"));
            boolean wifi, ac, buffet, pool, minibar, parking;
            if (wf == 0) {
                wifi = false;
            } else {
                wifi = true;
            }
            if (aC == 0) {
                ac = false;
            } else {
                ac = true;
            }
            if (Buffet == 0) {
                buffet = false;
            } else {
                buffet = true;
            }
            if (Pool == 0) {
                pool = false;
            } else {
                pool = true;
            }
            if (miniBar == 0) {
                minibar = false;
            } else {
                minibar = true;
            }
            if (parKing == 0) {
                parking = false;
            } else {
                parking = true;
            }
            byte[] IMG = c.getBlob(14);
            Room x = new Room(id, rate, beds, status, cost, wifi, ac, buffet, parking, pool, minibar, note, name, category, location, IMG,collaborate_id);
            list.add(x);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public Room get1Room(String sql, String x) {
        List<Room> list = getRoom(sql, x);
        return list.get(0);
    }


//    public Room getRoom2(String sql, String... args) {
//        List<Room> list = new ArrayList<>();
//        Room x = null;
//        SQLiteDatabase sqLiteDatabase = appSQL.getReadableDatabase();
//        Cursor c = sqLiteDatabase.rawQuery(sql, args);
//        c.moveToFirst();
//        while (!c.isAfterLast()) {
//            int id = c.getInt(c.getColumnIndex("id"));
//            String name = c.getString(c.getColumnIndex("fullname"));
//            String category = c.getString(c.getColumnIndex("category_name"));
//            String location = c.getString(c.getColumnIndex("location"));
//            int rate = c.getInt(c.getColumnIndex("rate"));
//            int beds = c.getInt(c.getColumnIndex("beds"));
//            String note = c.getString(c.getColumnIndex("note"));
//            int cost = c.getInt(c.getColumnIndex("cost"));
//            int people = c.getInt(c.getColumnIndex("number_people"));
//            int status = c.getInt(c.getColumnIndex("status"));
//            int wf = c.getInt(c.getColumnIndex("wifi"));
//            int aC = c.getInt(c.getColumnIndex("ac"));
//            int parKing = c.getInt(c.getColumnIndex("parking"));
//            int miniBar = c.getInt(c.getColumnIndex("minibar"));
//            int Pool = c.getInt(c.getColumnIndex("pool"));
//            int Buffet = c.getInt(c.getColumnIndex("buffet"));
//            boolean wifi, ac, buffet, pool, minibar, parking;
//            if (wf == 0) {
//                wifi = false;
//            } else {
//                wifi = true;
//            }
//            if (aC == 0) {
//                ac = false;
//            } else {
//                ac = true;
//            }
//            if (Buffet == 0) {
//                buffet = false;
//            } else {
//                buffet = true;
//            }
//            if (Pool == 0) {
//                pool = false;
//            } else {
//                pool = true;
//            }
//            if (miniBar == 0) {
//                minibar = false;
//            } else {
//                minibar = true;
//            }
//            if (parKing == 0) {
//                parking = false;
//            } else {
//                parking = true;
//            }
//            byte[] IMG = c.getBlob(c.getColumnIndex("image"));
//            Room x = new Room(id, rate, beds, status, cost, wifi, ac, buffet, parking, pool, minibar, note, name, category, location, IMG);
//            list.add(x);
//            c.moveToNext();
//        }
//        c.close();
//        return list;
//    }

    public long AddRoom(Room x) {
        ContentValues value = new ContentValues();
        value.put("fullname", x.getName());
        value.put("category_name", x.getCategory());
        value.put("location", x.getLocation());
        value.put("rate", x.getRate());
        value.put("beds", x.getBeds());
        value.put("note", x.getNote());
        value.put("cost", x.getCost());
        value.put("status", x.getStatus());
        value.put("image", x.getIMG());
        value.put("collaborate_id",x.getCollaborate_id());
        if (x.isWifi() == false) {
            value.put("wifi", 0);
        } else {
            value.put("wifi", 1);
        }
        if (x.isAc() == false) {
            value.put("ac", 0);
        } else {
            value.put("ac", 1);
        }
        if (x.isBuffet() == false) {
            value.put("buffet", 0);
        } else {
            value.put("buffet", 1);
        }
        if (x.isMinibar() == false) {
            value.put("minibar", 0);
        } else {
            value.put("minibar", 1);
        }
        if (x.isPool() == false) {
            value.put("pool", 0);
        } else {
            value.put("pool", 1);
        }
        if (x.isParking() == false) {
            value.put("parking", 0);
        } else {
            value.put("parking", 1);
        }
        long a = db.insert("room_tb", null, value);
        return a;
    }

    public long UpdateRoom(Room x) {
        ContentValues value = new ContentValues();
        value.put("fullname", x.getName());
        value.put("category_name", x.getCategory());
        value.put("location", x.getLocation());
        value.put("rate", x.getRate());
        value.put("beds", x.getBeds());
        value.put("note", x.getNote());
        value.put("cost", x.getCost());
        value.put("status", x.getStatus());
        value.put("image", x.getIMG());
        value.put("collaborate_id",x.getCollaborate_id());
        if (x.isWifi() == false) {
            value.put("wifi", 0);
        } else {
            value.put("wifi", 1);
        }
        if (x.isAc() == false) {
            value.put("ac", 0);
        } else {
            value.put("ac", 1);
        }
        if (x.isBuffet() == false) {
            value.put("buffet", 0);
        } else {
            value.put("buffet", 1);
        }
        if (x.isMinibar() == false) {
            value.put("minibar", 0);
        } else {
            value.put("minibar", 1);
        }
        if (x.isPool() == false) {
            value.put("pool", 0);
        } else {
            value.put("pool", 1);
        }
        if (x.isParking() == false) {
            value.put("parking", 0);
        } else {
            value.put("parking", 1);
        }
        return db.update("room_tb", value, "id=?", new String[]{String.valueOf(x.getId())});
    }

    public void DeleteRoom(int ID) {
        db.delete("room_tb", "id=?", new String[]{String.valueOf(ID)});
    }

    public List<order> getOrder(String sql) {
        List<order> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Date ngayNhan = null, ngayTra = null;
            int id = c.getInt(0);
            int user_id = c.getInt(1);
            int number = c.getInt(2);
            try {
                ngayNhan = format.parse(c.getString(3));
                ngayTra = format.parse(c.getString(4));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String gioNhan = c.getString(5);
            String gioTra = c.getString(6);
            int room_id = c.getInt(7);
            String note = c.getString(8);
            int status = c.getInt(9);
            int money = c.getInt(10);
            order x = new order(id, user_id, number, ngayNhan, ngayTra, gioNhan, gioTra, room_id, note, status,money);
            list.add(x);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public order getOrder1(String sql, String... args) {
        List<order> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, args);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Date ngayNhan = null, ngayTra = null;
            int id = c.getInt(0);
            int user_id = c.getInt(1);
            int number = c.getInt(2);
            try {
                ngayNhan = format.parse(c.getString(3));
                ngayTra = format.parse(c.getString(4));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String gioNhan = c.getString(5);
            String gioTra = c.getString(6);
            int room_id = c.getInt(7);
            String note = c.getString(8);
            int status = c.getInt(9);
            int money = c.getInt(10);
            order x = new order(id, user_id, number, ngayNhan, ngayTra, gioNhan, gioTra, room_id, note, status,money);
            list.add(x);
            c.moveToNext();
        }
        c.close();
        return list.get(0);
    }

    public List<order> getNhieuOrder(String sql, String... args) {
        List<order> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, args);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Date ngayNhan = null, ngayTra = null;
            int id = c.getInt(0);
            int user_id = c.getInt(1);
            int number = c.getInt(2);
            try {
                ngayNhan = format.parse(c.getString(3));
                ngayTra = format.parse(c.getString(4));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String gioNhan = c.getString(5);
            String gioTra = c.getString(6);
            int room_id = c.getInt(7);
            String note = c.getString(8);
            int status = c.getInt(9);
            int money = c.getInt(10);
            order x = new order(id, user_id, number, ngayNhan, ngayTra, gioNhan, gioTra, room_id, note, status,money);
            list.add(x);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public long AddOrder(order x) {
        ContentValues value = new ContentValues();
        value.put("user_id", x.getUser_id());
        value.put("number_people", x.getNumber_people());
        value.put("booking_date", format.format(x.getBooking_date()));
        value.put("return_date", format.format(x.getReturn_date()));
        value.put("time_checkin", x.getTime_checkin());
        value.put("time_checkout", x.getTime_checkout());
        value.put("room_id", x.getRoom_id());
        value.put("note", x.getNote());
        value.put("status", x.getStatus());
        value.put("money", x.getMoney());
        long a = db.insert("order_tb", null, value);
        return a;
    }

    public long UpdateOrder(order x) {
        ContentValues value = new ContentValues();
        value.put("user_id", x.getUser_id());
        value.put("number_people", x.getNumber_people());
        value.put("booking_date", format.format(x.getBooking_date()));
        value.put("return_date", format.format(x.getReturn_date()));
        value.put("time_checkin", x.getTime_checkin());
        value.put("time_checkout", x.getTime_checkout());
        value.put("room_id", x.getRoom_id());
        value.put("note", x.getNote());
        value.put("status", x.getStatus());
        value.put("money", x.getMoney());
        return db.update("order_tb", value, "id=?", new String[]{String.valueOf(x.getId())});
    }

    public void DeleteOrder(int ID) {
        db.delete("order_tb", "id=?", new String[]{String.valueOf(ID)});
    }

    //    public user getUser_name1 (String email, String pass) {
//        List<user> list = new ArrayList<>();
//        String sql = "SELECT * FROM user_tb WHERE email=? and password=?";
//        db = appSQL.getReadableDatabase();
//        user x = null;
//        Cursor c = db.rawQuery(sql, new String[]{email, pass});
//        c.moveToFirst();
//        while (!c.isAfterLast()) {
//            int id = c.getInt(0);
//            int ava = c.getInt(1);
    //           String name = c.getString(2);
//            String Email = c.getString(3);
//            String Pass = c.getString(4);
//            int role = c.getInt(5);
//            Date ngay = null;
    //           try {
//                ngay = format.parse(c.getString(6));
//           } catch (ParseException e) {
//               e.printStackTrace();
//            }
//            String phone = c.getString(7);
//           int money = c.getInt(8);
//             x = new user(id, ava, name, Email, Pass, ngay, phone, role, money);
//            list.add(x);
//            c.moveToNext();
//        }
//        c.close();
//        return x;
//        if (cursor.getCount() != 0) {
//            return true;
//        }
//        return false;
//    }
    public long AddDateCurrent(DateCurrent current) {
        ContentValues values = new ContentValues();
        values.put("current", format.format(current.getDate()));
        values.put("checkD", current.getCheck());
        long a = db.insert("date_tb", null, values);
        return a;
    }

    public long UpdateCurrent(DateCurrent current) {
        ContentValues values = new ContentValues();
        values.put("current", format.format(current.getDate()));
        values.put("checkD", current.getCheck());
        long a = db.update("date_tb", values, "id=?", new String[]{String.valueOf(current.getId())});
        return a;
    }

    public List<DateCurrent> getAllCurrent(String sql) {
        List<DateCurrent> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Date dateCurrent = null;
            int id = c.getInt(0);

            try {
                dateCurrent = format.parse(c.getString(1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int check = c.getInt(2);
            DateCurrent current = new DateCurrent();
            current.setId(id);
            current.setDate(dateCurrent);
            current.setCheck(check);
            list.add(current);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public DateCurrent getCurrent(String sql) {
        List<DateCurrent> currents = getAllCurrent(sql);
        return currents.get(0);
    }

    @SuppressLint("Range")
    public int getDoanhThuPhong(String x,String tuNgay, String denNgay) {
        String sqlDoanhThu = "SELECT SUM(money) as doanhThu FROM order_tb a inner JOIN room_tb b on a.room_id = b.id WHERE b.collaborate_id = ? and a.booking_date BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(sqlDoanhThu, new String[]{x,tuNgay, denNgay});
        while (c.moveToNext()) {
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhThu"))));
            } catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }

    public int getDoanhThuCaNhan(String x,String tuNgay, String denNgay) {
        String sqlDoanhThu = "SELECT SUM((money-money*5/100)*1/100) as doanhThu FROM order_tb a inner JOIN room_tb b on a.room_id = b.id WHERE b.collaborate_id = ? and a.booking_date BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(sqlDoanhThu, new String[]{x,tuNgay, denNgay});
        while (c.moveToNext()) {
            try {
                list.add(Integer.parseInt(c.getString(0)));
            } catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }

    public List<NapThe> getNapThe(String sql, String... args) {
        List<NapThe> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, args);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(0);
            int user_id = c.getInt(1);
            int money = c.getInt(2);
            int status = c.getInt(3);
            NapThe x = new NapThe(id, user_id,money,status);
            list.add(x);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public NapThe get1NapThe(String sql, String x) {
        List<NapThe> list = getNapThe(sql,x);
        return list.get(0);
    }

    public long AddNapThe(NapThe money){
        ContentValues value = new ContentValues();
        value.put("user_id",money.getUser_id());
        value.put("money",money.getMoney());
        value.put("status",money.getStatus());
        return db.insert("napthe_tb",null, value);
    }

    public long UpdateNapThe(NapThe x){
        ContentValues value = new ContentValues();
        value.put("id",x.getId());
        value.put("user_id",x.getUser_id());
        value.put("money",x.getMoney());
        value.put("status",x.getStatus());
        return db.update("napthe_tb", value, "id=?", new String[]{String.valueOf(x.getId())});
    }

    public void DeleteNapThe(int ID) {
        db.delete("napthe_tb", "id=?", new String[]{String.valueOf(ID)});
    }
    public long AddRating(rating rating){
        ContentValues values = new ContentValues();
        values.put("user_id",rating.getUser_id());
        values.put("room_id",rating.getRoom_id());

        values.put("rating",rating.getRating());
        values.put("note",rating.getNote());
        long a = db.insert("rating_tb",null,values);
        return a;
    }
    public long UpdateRating(rating rating){
        ContentValues values = new ContentValues();
        values.put("rating",rating.getRating());
        values.put("note",rating.getNote());
        long a = db.update("rating_tb",values,"id=?",new String[]{String.valueOf(rating.getId())});
        return a;
    }
    public void DeleteRating(int id){
        db.delete("rating_tb","id=?",new String[]{String.valueOf(id)});
    }

    public List<rating> GetAllRating(String sql,String...args){
        List<rating> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,args);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(0);
            int user_id = c.getInt(1);
            int room_id = c.getInt(2);
            int rating1 = c.getInt(3);
            String note = c.getString(4);
            rating x = new rating(id,user_id,room_id,rating1,note);
            list.add(x);
            c.moveToNext();
        }
        c.close();
        return list;
    }
}
