package com.example.homebook.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebook.DAO.DAO;
import com.example.homebook.R;
import com.example.homebook.adapter.ListMoneyAdapter;
import com.example.homebook.fragment.Fragment3;
import com.example.homebook.model.NapThe;
import com.example.homebook.model.user;

import java.util.ArrayList;
import java.util.List;

public class NapTheAdminFragment extends Fragment {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money_admin, container, false);

        view.findViewById(R.id.thoat4).setOnClickListener(vv -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame,new Fragment3())
                    .commit();
        });

        recyclerView = view.findViewById(R.id.recyclerViewMoneyAdminList);

        LoadData();

        return view;
    }

    public void addTien(user id){
//        DAO dao = new DAO(getActivity());
//
//        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
////        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
//        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.custom_dialog, null);
//        TextView txtTien = view.findViewById(R.id.currentMoneyAdmin);
//        EditText txtThemTien = view.findViewById(R.id.addMoneyAdmin);
//        Button save = view.findViewById(R.id.saveMoneyAdmin);
//        Button cancel = view.findViewById(R.id.cancelMoneyAdmin);
//
//        txtTien.setText(id.getMoney()+"");
//
//        builder.setView(view);
//        builder.setTitle("Thêm tiền");
//        AlertDialog alertDialog = builder.create();
//
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String tienAdd = txtThemTien.getText().toString().trim();
//                int tien = Integer.parseInt(tienAdd);
//
//                if(tienAdd.trim().length() <= 0){
//                    Toast.makeText(getActivity(),"Vui lòng nhập tiền cần thêm.",Toast.LENGTH_SHORT).show();
//                }else if(tien <= 0){
//                    Toast.makeText(getActivity(),"Tiền không được bé hơn hoặc bằng 0.",Toast.LENGTH_SHORT).show();
//                }else{
//                    id.setMoney(id.getMoney() + tien);
//                    dao.UpdateUser(id);
//                    Toast.makeText(getActivity(),tienAdd + " đã được thêm vào tài khoản " + id.getFullname(),Toast.LENGTH_SHORT).show();
//                    Log.d("add", "Thêm thành công");
//                    alertDialog.dismiss();
//                    LoadData();
//                }
//            }
//        });
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.cancel();
//            }
//        });
//
//        alertDialog.show();
//        Log.d("add", "Hiện thành công");
    }

    public void LoadData(){
        DAO dao = new DAO(getActivity());

        List<NapThe> list = dao.getNapThe("SELECT * FROM napthe_tb",null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ListMoneyAdapter adapter = new ListMoneyAdapter(getContext(), (ArrayList<NapThe>) list, NapTheAdminFragment.this);
        recyclerView.setAdapter(adapter);
    }
}