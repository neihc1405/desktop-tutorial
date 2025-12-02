package com.example.alarm_miniproject.fragments;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alarm_miniproject.receiver.BaothucReceiver;
import com.example.alarm_miniproject.Database.AlarmData;
import com.example.alarm_miniproject.Database.DataManager;
import com.example.alarm_miniproject.views.ItemBaothuc;
import com.example.alarm_miniproject.adapters.MyAdapterBaothuc;
import com.example.alarm_miniproject.views.MyViewhoder;
import com.example.alarm_miniproject.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaothucFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaothucFragment extends Fragment {


    SwitchMaterial swit;
    TextView tinhtrang, gio;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList <ItemBaothuc> datalist = new ArrayList <>();
    private MyViewhoder holder;


    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    ImageButton btadd;
    public final String TAG="BaothucFragment";

    DataManager dataManager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BaothucFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BaothucFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaothucFragment newInstance(String param1, String param2) {
        BaothucFragment fragment = new BaothucFragment();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_baothuc, container, false );
        RecyclerView recyclerView = view.findViewById( R.id.recycleview );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( view.getContext() );
        linearLayoutManager.setOrientation( linearLayoutManager.VERTICAL );
        recyclerView.setLayoutManager( linearLayoutManager );

        MyAdapterBaothuc myAdapter = new MyAdapterBaothuc( getContext() );
        recyclerView.setAdapter( myAdapter );

        swit = view.findViewById( R.id.battat );
        tinhtrang = view.findViewById( R.id.tinhtrang );

        alarmManager = (AlarmManager) getContext().getSystemService( Context.ALARM_SERVICE );


        dataManager = DataManager.getInstance( view.getContext() );
        btadd = view.findViewById( R.id.btAdd );


        btadd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int gio = calendar.get( Calendar.HOUR_OF_DAY );
                int phut = calendar.get( Calendar.MINUTE );


                TimePickerDialog timePickerDialog = new TimePickerDialog( view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "HH:mm" );

                        calendar.set( Calendar.HOUR_OF_DAY, i );
                        calendar.set( Calendar.MINUTE, i1 );

                        long id = calendar.getTimeInMillis();
                        AlarmData alarmData = new AlarmData( id, simpleDateFormat.format( calendar.getTime() ), "Đã đặt báo thức", true );
                        myAdapter.dataManager.getAlarmDao().insertAll( alarmData );

                        Intent intent = new Intent( view.getContext(), BaothucReceiver.class );
                        intent.putExtra( "pos", id );
                        Log.d( TAG," id "+ id );
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            pendingIntent= PendingIntent.getBroadcast(view.getContext(),(int) id, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_IMMUTABLE);
                        } else {
                            pendingIntent= PendingIntent.getBroadcast(view.getContext(),(int) id, intent, PendingIntent.FLAG_IMMUTABLE);
                        }

                        alarmManager.setExact( AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent );

                        myAdapter.updateData();
                        Toast.makeText( view.getContext(), "Da them bao thuc", Toast.LENGTH_SHORT ).show();

                    }
                }, gio, phut, true );
                timePickerDialog.show();

            }
        } );


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback( 0, ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if (direction == ItemTouchHelper.RIGHT) {
                    int pos = viewHolder.getAdapterPosition();
//                    ItemBaothuc dataItem = myAdapter.getDataAt(pos);
                    AlarmData alarmData = myAdapter.getDataAt( pos );
                    myAdapter.removeItem( pos );
                    Snackbar.make( recyclerView, "Deleted Alarm", Snackbar.LENGTH_LONG ).setAction( "Undo", view -> myAdapter.add( pos, alarmData ) ).show();

                }
            }
        };


        return view;
    }


}