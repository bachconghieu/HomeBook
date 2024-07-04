package com.example.homebook.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebook.DAO.DAO;
import com.example.homebook.R;
import com.example.homebook.adapter.OrderAdapter;
import com.example.homebook.model.Room;
import com.example.homebook.model.order;
import com.example.homebook.model.user;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    DAO dao;
    Button button;
    List<order> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_cart, container, false);


        recyclerView = v.findViewById(R.id.ds_orders);
        dao = new DAO(getActivity());
        SharedPreferences sP = getActivity().getSharedPreferences("User_File",MODE_PRIVATE);
        String email = sP.getString("Email","");
        String pass = sP.getString("Password","");
        if(!email.equals("")&&!pass.equals("")){
            if (dao.checkLogin(email, pass)) {
                user x = dao.get1User("select * from user_tb where email = ?", email);
                list = dao.getOrder("SELECT * FROM order_tb where user_id = "+x.getId()+"");
                loadData(list);
            }
        }else {
            Dialog();
        }
        return v;
    }
    public void loadData(List<order> list){
        dao = new DAO(getActivity());
//        list = dao.getOrder("select * from order_tb");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        OrderAdapter homeBookApdater = new OrderAdapter(getContext(), (ArrayList<order>) list,CartFragment.this);
        recyclerView.setAdapter(homeBookApdater);
    }
    private void Dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Please login!");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    fragmentManager
//                            .beginTransaction()
//                            .replace(R.id.frame,new Fragment3())
//                            .commit();
            }
        });
//            builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                }
//            });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void xoaDon(order x, int tien){
        DAO dao = new DAO(getActivity());
        dao.DeleteOrder(x.getId());
        user user = dao.get1User("select * from user_tb where id = ?",x.getUser_id()+"");
        int tienThemLai = tien - (tien*5/100);
        user.setMoney(user.getMoney() + tienThemLai);
        dao.UpdateUser(user);
        list = dao.getOrder("SELECT * FROM order_tb where user_id = "+x.getId()+"");
        loadData(list);
    }

    public void congThemTien(order x, int tien){
        DAO dao = new DAO(getActivity());
        user user = dao.get1User("select * from user_tb where id = ?",x.getUser_id()+"");
        int tienThem = tien - (tien*5/100);
        user.setMoney(user.getMoney() - tienThem);
        dao.UpdateUser(user);
        Room room = dao.get1Room("select * from room_tb where id = ?",x.getRoom_id()+"");
        user ctv = dao.get1User("select * from user_tb where id = ?",room.getCollaborate_id() + "");
        ctv.setMoney(ctv.getMoney() + (tienThem*1/100));
        dao.UpdateUser(ctv);
    }
}