package com.example.homebook.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.homebook.DAO.DAO;
import com.example.homebook.R;
import com.example.homebook.adapter.ListMenuAdapter;
import com.example.homebook.menu.AccountManagerFragment;
import com.example.homebook.menu.AdminHistoryFragment;
import com.example.homebook.menu.ChangePassFragment;
import com.example.homebook.menu.FragmentTaiKhoan;
import com.example.homebook.menu.LSDatActivity;
import com.example.homebook.menu.LienHeActivity;
import com.example.homebook.menu.NapTheAdminFragment;
import com.example.homebook.menu.ThongTin;
import com.example.homebook.model.ListModelMenu;
import com.example.homebook.model.admin;
import com.example.homebook.model.user;

import java.util.ArrayList;

public class Fragment3 extends Fragment {

    String name;
    TextView textView,moneyTxt;
    ImageView avatar;

    public Fragment3() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_3, container, false);

        ListView listViewUser = v.findViewById(R.id.lvmenuUser);
        ListView listViewAdmin = v.findViewById(R.id.lvmenuAdmin);

        textView = v.findViewById(R.id.txtUsername1);
        avatar = v.findViewById(R.id.avatar);
        moneyTxt = v.findViewById(R.id.txtMoney1);

        v.findViewById(R.id.regis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.frame, new RegisterFragment())
                        .commit();
            }

        });
        v.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.frame, new LoginFragment())
                        .commit();
            }
        });

        listViewUser.setVisibility(View.GONE);
        listViewAdmin.setVisibility(View.GONE);
        moneyTxt.setVisibility(View.GONE);

        ArrayList<ListModelMenu> list1 = new ArrayList<>();
        ArrayList<ListModelMenu> list2 = new ArrayList<>();
        SharedPreferences sP = getActivity().getSharedPreferences("User_File", MODE_PRIVATE);
        String email = sP.getString("Email", "");
        String pass = sP.getString("Password", "");
        String userAd = sP.getString("UserAdmin", "");
        String passAd = sP.getString("PassAdmin", "");

        DAO dao = new DAO(getContext());
        if (dao.checkAdmin(userAd, passAd)) {
            listViewAdmin.setVisibility(View.VISIBLE);
            v.findViewById(R.id.regis).setVisibility(View.GONE);
            v.findViewById(R.id.login).setVisibility(View.GONE);


            admin x = dao.get1Admin(userAd, passAd);
            name = x.getUsername();
            textView.setText(name);
            avatar.setImageResource(R.drawable.usermanage);

        }

        if (dao.checkLogin(email, pass)) {
            listViewUser.setVisibility(View.VISIBLE);
            moneyTxt.setVisibility(View.VISIBLE);
            v.findViewById(R.id.regis).setVisibility(View.GONE);
            v.findViewById(R.id.login).setVisibility(View.GONE);


            user x = dao.get1User("select * from user_tb where email = ?", email);
            name = x.getFullname();
            textView.setText(name);
            moneyTxt.setText("Bạn đang có: " + x.getMoney()+"đ");
            if (x.getAvatar() == 0) {
                avatar.setImageResource(R.drawable.usermanage);
            } else {
                avatar.setImageResource(x.getAvatar());
            }
        }

        //add vao listview
        list1.add(new ListModelMenu(R.drawable.setting_item, "Account Management"));
        list1.add(new ListModelMenu(R.drawable.add_money, "Deposit"));
        list1.add(new ListModelMenu(R.drawable.changepass_item, "Change Password"));
        list1.add(new ListModelMenu(R.drawable.history, "Booking History"));
        list1.add(new ListModelMenu(R.drawable.contact, "Contact"));
        list1.add(new ListModelMenu(R.drawable.in4, "information"));
        list1.add(new ListModelMenu(R.drawable.exit, "Log out"));

        ListMenuAdapter adapter = new ListMenuAdapter(getContext(), R.layout.item_menu, list1);
        listViewUser.setAdapter(adapter);

        listViewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.frame, new FragmentTaiKhoan())
                            .commit();
                }
                if (i == 2) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.frame, new ChangePassFragment())
                            .commit();
                }
                if (i == 3) {
                    startActivity(new Intent(getContext(), LSDatActivity.class));
                }
                if (i == 4) {
                    startActivity(new Intent(getContext(), LienHeActivity.class));
                }
                if (i == 5) {
                    startActivity(new Intent(getContext(), ThongTin.class));
                }
                if (i == 1) {
//                    startActivity(new Intent(getContext(), ChangeLanguage.class));
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.frame, new Fragment2())
                            .commit();
                }
                if (i == 6) {
                    SharedPreferences.Editor edit = sP.edit();
                    edit.putString("Email", "");
                    edit.putString("Password", "");
                    edit.commit();
                    getActivity().finish();
                }


            }
        });

        //listview admin

        list2.add(new ListModelMenu(R.drawable.add_money, "Deposit Confirm"));
        list2.add(new ListModelMenu(R.drawable.ic_accounts, "Account Manage"));
        list2.add(new ListModelMenu(R.drawable.graph_black, "Statistic"));
        list2.add(new ListModelMenu(R.drawable.contact, "Contact"));
        list2.add(new ListModelMenu(R.drawable.in4, "Information"));
        list2.add(new ListModelMenu(R.drawable.exit, "Log out"));

        ListMenuAdapter adapter2 = new ListMenuAdapter(getContext(), R.layout.item_menu, list2);
        listViewAdmin.setAdapter(adapter2);

        listViewAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:{
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.frame, new NapTheAdminFragment())
                                .commit();
                        break;
                    }
                    case 1:{
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.frame, new AccountManagerFragment())
                                .commit();
                        break;
                    }
                    case 2:{
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.frame, new AdminHistoryFragment())
                                .commit();
                        break;
                    }
                    case 3:startActivity(new Intent(getContext(), LienHeActivity.class));break;
                    case 4:startActivity(new Intent(getContext(), ThongTin.class));break;
                    case 5:{
                        SharedPreferences.Editor edit = sP.edit();
                        edit.putString("UserAdmin", "");
                        edit.putString("PassAdmin", "");
                        edit.commit();
                        getActivity().finish();
                    }
                }
            }
        });

        return v;
    }
}