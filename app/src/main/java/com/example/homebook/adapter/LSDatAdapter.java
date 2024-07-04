package com.example.homebook.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.homebook.DAO.DAO;
import com.example.homebook.R;
import com.example.homebook.model.Room;
import com.example.homebook.model.order;

import java.text.SimpleDateFormat;
import java.util.List;

public class LSDatAdapter extends BaseAdapter {

    Context context;
    List<order> list;

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    int tienHoaDon;

    public LSDatAdapter(Context context, List<order> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        TextView tvName, tvLocation, tvCategory, tvBeds,tvbegin,tvend,tvcost;
        ImageView imageView;
        RatingBar tvrate;

        Button delete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_lsdat, null);

        if(view!=null){
            holder = new ViewHolder();

            holder.tvName = view.findViewById(R.id.tv_name_homebook_ls);
            holder.imageView = view.findViewById(R.id.history_img);
            holder.tvLocation = view.findViewById(R.id.tv_location_homebook_ls);
            holder.tvCategory = view.findViewById(R.id.tv_category_ls);
            holder.tvBeds = view.findViewById(R.id.tv_beds_ls);
            holder.tvrate = view.findViewById(R.id.number_stars);
            holder.tvbegin = view.findViewById(R.id.tv_datecheckin);
            holder.tvend = view.findViewById(R.id.tv_datecheckout);
            holder.tvcost= view.findViewById(R.id.tv_money);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        DAO dao = new DAO(context);
        int id = list.get(i).getRoom_id();


            Room roomList = dao.get1Room("select * from room_tb where id = ?", String.valueOf(id));
            if (roomList != null) {
//                byte[] hinhanh = roomList.getIMG();
//                Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
//        holder.imageView.setImageBitmap(bitmap);
//                 holder.imageView.setImageBitmap(bitmap);
//            holder.tvCategory.setText(roomList.getLocation());
                holder.tvName.setText(roomList.getName());
                holder.tvBeds.setText(roomList.getBeds() + "");
                switch (roomList.getBeds()) {
                    case 0:holder.tvBeds.setText("Single room");break;
                    case 1:holder.tvBeds.setText("Twin room");break;
                    case 2:holder.tvBeds.setText("Double room");break;
                    case 3:holder.tvBeds.setText("Triple room");break;
                    case 4:holder.tvBeds.setText("Quad room");break;
                }
                holder.tvCategory.setText(roomList.getCategory());
                holder.tvLocation.setText(roomList.getLocation());
                holder.tvrate.setRating(roomList.getRate());
                byte[] hinhanh = roomList.getIMG();
                Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);

                holder.imageView.setImageBitmap(bitmap);
                holder.tvbegin.setText(format.format(list.get(i).getBooking_date()));
                holder.tvend.setText(format.format(list.get(i).getReturn_date()));
                holder.tvcost.setText(roomList.getCost()+"");

            }





            return view;
    }



}
