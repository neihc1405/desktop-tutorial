package com.example.alarm_miniproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.alarm_miniproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BamgioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BamgioFragment extends Fragment {

    Button btnStop,btnStart, btnPause,btnReset;
    TextView txtTimer,text;
    long lStartTime, lPauseTime, lSystemTime = 0L;
    Handler handler = new Handler();
    boolean isRun;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            lSystemTime = SystemClock.uptimeMillis() - lStartTime;
            long lUpdateTime = lPauseTime + lSystemTime;
            long secs = (long)(lUpdateTime/1000);
            long mins= secs/60;
            secs = secs %60;
            long milliseconds = (long)(lUpdateTime%1000);
            txtTimer.setText(""+mins+":" + String.format("%02d",secs) + ":" + String.format("%03d",milliseconds));
            handler.postDelayed(this,0);
        }
    };
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BamgioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BamgioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BamgioFragment newInstance(String param1, String param2) {
        BamgioFragment fragment = new BamgioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_bamgio, container, false );
        btnStart = view.findViewById(R.id.btnStart);
        //btnStop = (Button)findViewById(R.id.btnStop);
        btnPause = view.findViewById(R.id.btnPause);
        btnReset = view.findViewById(R.id.btnReset);
        txtTimer = view.findViewById(R.id.txtTimer);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        clickStart();
//        clickStop();
        clickPause();
        clickReset();
    }

   public void clickStart()
    {

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRun)
                    return;
                isRun = true;
                lStartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                btnReset.setEnabled(false);
                //btnStop.setEnabled(true);
            }

        });


    }



  public  void clickPause()
    {

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRun)
                    return;
                isRun = false;
                lPauseTime += lSystemTime;
                handler.removeCallbacks(runnable);
                btnReset.setEnabled(true);
                //btnStop.setEnabled(true);

            }
        });
    }
    public void clickReset(){
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lPauseTime = 0;
                isRun = false;
                txtTimer.setText("00:00:00");
            }
        });
    }
}