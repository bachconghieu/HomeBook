package com.example.homebook.database;

import static com.example.homebook.database.SQLInsert.adminstrator_Values;
import static com.example.homebook.database.SQLInsert.favourite_Values;
import static com.example.homebook.database.SQLInsert.user_Values;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AppSQL extends SQLiteOpenHelper {
    final String AdminstratorTable = "CREATE TABLE adminstrator_tb(username text primary key NOT NULL, password text NOT NULL, money_reciever integer NOT NULL)";
    final String UserTable = "CREATE TABLE user_tb(id integer primary key autoincrement, avatar integer NOT NULL, fullname text NOT NULL, email text NOT NULL,password text NOT NULL, role integer NOT NULL, birthday date NOT NULL, phonenumber nvarchar(11) NOT NULL, money integer NOT NULL)";

    final String RoomTable = "create table room_tb(id integer primary key autoincrement,fullname text NOT NULL, category_name text NOT NULL, location text NOT NULL, rate integer NOT NULL, beds integer NOT NULL,wifi integer NOT NULL,ac integer NOT NULL,parking integer NOT NULL, minibar integer NOT NULL,pool integer NOT NULL,buffet integer NOT NULL, cost integer NOT NULL, status integer NOT NULL,image BlOB, note text, collaborate_id integer NOT NULL)";
    final String RoomFavouriteTable = "create table room_favourite_tb(id integer primary key autoincrement,room_id integer NOT NULL, user_id integer NOT NULL)";

    final String OrderTable = "create table order_tb(id integer primary key autoincrement, user_id integer references user_tb(id) NOT NULL, number_people integer NOT NULL, booking_date date NOT NULL, return_date date, time_checkin text NOT NULL, time_checkout text NOT NULL, room_id integer references room_tb(id) NOT NULL, note text,status integer NOT NULL,money integer NOT NULL)";
    final String DateTable = "create table date_tb(id integer primary key autoincrement, current date not null, checkD integer not null)";

    final String NapTheTable = "create table napthe_tb(id integer primary key autoincrement, user_id integer references user_tb(id) NOT NULL, money int NOT NULL, status int NOT NULL)";
    final String RatingTable = "create table rating_tb(id integer primary key autoincrement, user_id integer references user_tb(id) NOT NULL, room_id integer references room_tb(id) NOT NULL, rating integer NOT NULL,note text NOT NULL)";

    Context context;
    SQLiteDatabase database;

    public AppSQL(@Nullable Context context) {
        super(context, "db_name", null, 1);
        this.context = context;
        database = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AdminstratorTable);
        db.execSQL(UserTable);
        db.execSQL(OrderTable);
        db.execSQL(RoomTable);
        db.execSQL(RoomFavouriteTable);
        db.execSQL(DateTable);
        db.execSQL(NapTheTable);
        db.execSQL(RatingTable);

        db.execSQL(adminstrator_Values);
        db.execSQL(user_Values);
//        db.execSQL(room_Values);
        db.execSQL(favourite_Values);
//        db.execSQL(rating_Values);
       
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("drop table if exists adminstrator_tb");
            db.execSQL("drop table if exists user_tb");
            db.execSQL("drop table if exists order_tb");
            db.execSQL("drop table if exists room_tb");
            db.execSQL("drop table if exists room_favourite_tb");

            onCreate(db);
        }
    }
}
