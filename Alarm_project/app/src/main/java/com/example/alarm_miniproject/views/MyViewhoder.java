package com.example.alarm_miniproject.views;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarm_miniproject.Database.AlarmData;
import com.example.alarm_miniproject.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class MyViewhoder extends RecyclerView.ViewHolder {
    private TextView giobaothuc;
    private SwitchMaterial battat;
    private TextView tinhtrang;
    public Context context;


    public MyViewhoder(@NonNull View itemView) {
        super(itemView);
        giobaothuc = itemView.findViewById( R.id.itemGio);
        tinhtrang = itemView.findViewById(R.id.tinhtrang);
        context=itemView.getContext();

        battat = itemView.findViewById(R.id.battat);


//        battat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (battat.isChecked()) {
//                    //nut bat bao thuc
//                    Toast.makeText(battat.getContext(), "Da bat", Toast.LENGTH_SHORT).show();
//                    tinhtrang.setText("Đã bật báo thức");
//                }
//                if(battat.isChecked()==false){
//                    tinhtrang.setText("Đã tắt báo thức");
//                }
//            }
//        });
    }

    public void datbaothuc() {
        Date timenow = Calendar.getInstance().getTime();
        String s = timenow.toString();

        if (s.equals(giobaothuc)) {

        }
    }

    public void bindView(AlarmData item) {
        giobaothuc.setText(item.time);
        tinhtrang.setText(item.tinhtrang);
        battat.setEnabled(true);
        if (item.battat) {
            battat.setChecked(true);
            tinhtrang.setText("Đã đặt báo thức");
        } else {
            battat.setChecked(false);
            tinhtrang.setText("Đã tắt báo thức");
        }
    }


    public boolean check(AlarmData item) {
        if (battat.isChecked()) {
           return item.battat=true;
        } else return item.battat=false;
    }
    public void setTinhtrangText(String message) {
        this.tinhtrang.setText( message );
    }

    public TextView getGiobaothuc() {
        return giobaothuc;
    }

    public SwitchMaterial getBattat() {
        return battat;
    }

    public TextView getTinhtrang() {
        return tinhtrang;
    }

    public void setBattat(SwitchMaterial battat) {
        this.battat = battat;
    }
}
