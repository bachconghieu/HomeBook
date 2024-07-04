package com.example.homebook.menu;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.homebook.DAO.DAO;
import com.example.homebook.R;
import com.example.homebook.fragment.Fragment3;
import com.example.homebook.model.user;
import com.google.android.material.textfield.TextInputEditText;

public class ChangePassFragment extends Fragment {
    TextInputEditText edtpassold, edtpass, edrepass;
    Button btnsave, btncancel;
    DAO dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change_pass, container, false);
        v.findViewById(R.id.thoat4).setOnClickListener(vv -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame,new Fragment3())
                    .commit();
        });

        dao = new DAO(getActivity());
        edtpass = v.findViewById(R.id.passnew);
        edtpassold = v.findViewById(R.id.passold);
        edrepass = v.findViewById(R.id.repass);
        btnsave = v.findViewById(R.id.btnSavepass);
        btncancel = v.findViewById(R.id.cancelsave);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtpassold.setText("");
                edtpass.setText("");
                edrepass.setText("");
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sP = getActivity().getSharedPreferences("User_File", MODE_PRIVATE);
                String email = sP.getString("Email", "");
                user x = dao.get1User("select * from user_tb where email = ?", email);
                if (validate() > 0) {
                    x.setPassword(edtpass.getText().toString());
                    if (dao.UpdateUser(x) > 0) {
                        Toast.makeText(getActivity(), "Change password successfully", Toast.LENGTH_SHORT).show();
                        edtpassold.setText("");
                        edtpass.setText("");
                        edrepass.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Change password failed", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        return v;
    }

    public int validate() {
        int check = 1;
        if (edtpassold.getText().length() == 0 || edtpass.getText().length() == 0 || edrepass.getText().length() == 0) {
            Toast.makeText(getContext(), " not enough information", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            String pass = edtpass.getText().toString();
            String repass = edrepass.getText().toString();

            SharedPreferences sP = getActivity().getSharedPreferences("User_File", MODE_PRIVATE);
            String email = sP.getString("Email", "");
            user x = dao.get1User("select * from user_tb where email = ?", email);
            String old = x.getPassword();
            Log.d("passs", old);
            if (!old.equals(edtpassold.getText().toString())) {
                Toast.makeText(getContext(), "The current password is false", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(repass)) {
                Toast.makeText(getContext(), "Password is not similar", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}