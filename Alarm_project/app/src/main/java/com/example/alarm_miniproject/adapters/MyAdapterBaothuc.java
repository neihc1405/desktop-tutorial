package com.example.alarm_miniproject.adapters;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarm_miniproject.Database.AlarmData;
import com.example.alarm_miniproject.Database.DataManager;
import com.example.alarm_miniproject.R;
import com.example.alarm_miniproject.receiver.BaothucReceiver;
import com.example.alarm_miniproject.views.MyViewhoder;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyAdapterBaothuc extends RecyclerView.Adapter<MyViewhoder> {


    private static final String TAG = "MyAdapter";
    public List<AlarmData> datalist = new ArrayList<>();

    public DataManager dataManager;
    public AlarmManager alarmManager;
    public PendingIntent pendingIntent;
    public Context context;
    SwitchMaterial swit;
    TextView tinhtrang;
    @NonNull
    @Override
    public MyViewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_baothuc, parent, false);


        swit = view.findViewById(R.id.battat);
        tinhtrang=view.findViewById(R.id.tinhtrang);

        alarmManager = (AlarmManager) parent.getContext().getSystemService(Context.ALARM_SERVICE);
        context=view.getContext();

        dataManager = DataManager.getInstance(view.getContext());
        return new MyViewhoder(view);

    }

    public MyAdapterBaothuc(Context context) {
        dataManager = DataManager.getInstance(context);
        // datalist.add(new AlarmData("10:10", "Chua dat gio", false));
        datalist = dataManager.getAlarmDao().getAll();


    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewhoder holder, @SuppressLint("RecyclerView") int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), SuaBaothuc.class);
//                view.getContext().startActivity(intent);

                Calendar calendar = Calendar.getInstance();
                int gio = calendar.get(Calendar.HOUR_OF_DAY);
                int phut = calendar.get(Calendar.MINUTE);



                final Intent intent = new Intent(view.getContext(), BaothucReceiver.class);
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        //calendar.set(0, 0, 0, i, i1);

                        calendar.set(Calendar.HOUR_OF_DAY,i);
                        calendar.set(Calendar.MINUTE,i1);
                        AlarmData alarmData = datalist.get(position);
                        alarmData.time = simpleDateFormat.format(calendar.getTime());
                        dataManager.getAlarmDao().update(alarmData);
                        Toast.makeText(view.getContext(), "Đã sửa báo thức", Toast.LENGTH_SHORT).show();
                        updateData();
                        alarmData.battat=holder.getBattat().isChecked();
                        Log.d( TAG,"check sua:"+alarmData.battat );
                        LocalTime time=LocalTime.now();
                        if(alarmData.battat==true) {


                                long id = alarmData.id;
                                Intent intent1 = new Intent( view.getContext(), BaothucReceiver.class );
                                intent1.putExtra( "pos", alarmData.id );
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                    pendingIntent = PendingIntent.getBroadcast( view.getContext(), (int) id, intent1, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_IMMUTABLE );
                                } else {
                                    pendingIntent = PendingIntent.getBroadcast( view.getContext(), (int) id, intent1, PendingIntent.FLAG_IMMUTABLE );
                                }
                                alarmManager.setExact( AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent );
                            }

                    }
                }, gio, phut, true);
                timePickerDialog.show();

            }
        });

        holder.getBattat().setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlarmData alarmData = datalist.get(position);
                //holder.getBattat().setChecked( true );
                if( holder.getBattat().isChecked()==true) {
                    Log.d( TAG,"checked" );

                    holder.setTinhtrangText("Đã đặt báo thức");
                    String itemGio=String.valueOf( holder.getGiobaothuc().getText());
                    int gio=Integer.valueOf( itemGio.substring( 0,2 ));
                    int phut=Integer.valueOf( itemGio.substring( 3 ));
                    LocalTime time=LocalTime.now();
                    Calendar calendar = Calendar.getInstance();
                    Log.d(TAG,"gio:"+gio+" "+"gio now"+time.getHour());

                   // if(gio==time.getHour()&&phut==time.getMinute()) {
                        calendar.set( Calendar.HOUR_OF_DAY, gio );
                        calendar.set( Calendar.MINUTE, phut );

                        long id=alarmData.id;
                        Intent intent1 = new Intent( context, BaothucReceiver.class );
                        intent1.putExtra( "pos", id );

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            pendingIntent= PendingIntent.getBroadcast(view.getContext(),(int) id, intent1, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_IMMUTABLE);
                        } else {
                            pendingIntent= PendingIntent.getBroadcast(view.getContext(),(int) id, intent1, PendingIntent.FLAG_IMMUTABLE);
                        }
                        alarmManager.setExact( AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent );

                 //   }
                }else{
                    holder.setTinhtrangText("Đã tắt báo thức");
                    //holder.getBattat().setChecked( false );

                    String itemGio=String.valueOf( holder.getGiobaothuc().getText());
                    Log.d( "gio",itemGio );
                }
                alarmData.battat = holder.getBattat().isChecked();
                dataManager.getAlarmDao().update(alarmData);
            }
        } );

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlarmData alarmData = datalist.get(position);
                dataManager.getAlarmDao().delete(alarmData);
                Toast.makeText(view.getContext(), "Đã xóa báo thức", Toast.LENGTH_SHORT).show();
                updateData();
                return false;
            }
        });
        holder.bindView(datalist.get(position));

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public void removeItem(int pos) {
        Log.d(TAG, "removeItem " + pos);
        notifyItemRemoved(pos);
        datalist.remove(pos);
    }


    public AlarmData getDataAt(int pos) {
        return datalist.get(pos);
    }

    public void add(int pos, AlarmData data) {
        datalist.add(pos, data);
        notifyItemInserted(pos);
    }


    public void updateData() {
        datalist = dataManager.getAlarmDao().getAll();
        notifyDataSetChanged();
    }
}
