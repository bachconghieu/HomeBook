package com.example.homebook.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebook.DAO.DAO;
import com.example.homebook.R;
import com.example.homebook.adapter.ListAccountAdapter;
import com.example.homebook.model.user;

import java.util.ArrayList;
import java.util.List;

public class AccountManagerFragment extends Fragment {

    RecyclerView recyclerView;
    List<user> list;
    DAO dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_manager, container, false);

        recyclerView = view.findViewById(R.id.recyclerAccountList);
        dao = new DAO(getActivity());

        list = dao.getUser("select * from user_tb",null);

        LoadData();

        return view;
    }

    public void LoadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ListAccountAdapter adapter = new ListAccountAdapter(getContext(), (ArrayList<user>) list, AccountManagerFragment.this);
        recyclerView.setAdapter(adapter);
    }
}