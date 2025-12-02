package com.example.alarm_miniproject.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarm_miniproject.R;

import java.util.Locale;

public class HengioFragment extends Fragment {

    private EditText hours,minutes,seconds;
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HengioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HengioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HengioFragment newInstance(String param1, String param2) {
        HengioFragment fragment = new HengioFragment();
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
        View view = inflater.inflate( R.layout.fragment_hengio, container, false );
        hours = view.findViewById(R.id.edit_text_input1);
        minutes = view.findViewById(R.id.edit_text_input2);
        seconds = view.findViewById(R.id.edit_text_input3);
        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        mButtonSet = view.findViewById(R.id.button_set);
        mButtonStartPause =view.findViewById(R.id.button_start_pause);
        mButtonReset =view.findViewById(R.id.button_reset);
        mButtonSet.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String hour = hours.getText().toString();
                String minute = hours.getText().toString();
                String second = hours.getText().toString();
                if(hour.length()==0 || minute.length()==0 || second.length()==0){
                    Toast.makeText(view.getContext(),"Field can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                int hour1 = Integer.valueOf(hours.getText().toString())*3600*1000;
                int minute1 =Integer.valueOf(minutes.getText().toString())*60*1000;
                int second1 = Integer.valueOf(seconds.getText().toString())*1000;
                long millisInput = hour1+minute1+second1;
                if(millisInput==0){
                    Toast.makeText(view.getContext(),"Please enter a positive number",Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
                hours.setText("");
                minutes.setText("");
                seconds.setText("");
            }
        });
        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimerRunning){
                    pauseTimer();
                }
                else {
                    startTimer();
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        return view;
    }
    private void setTime(long milliseconds){
        milliseconds = milliseconds;
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyBoard();
    }
    private void startTimer(){
        mEndTime = System.currentTimeMillis()+mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateWatchInterface();
            }
        }.start();
        mTimerRunning = true;
        updateWatchInterface();
    }
    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning =false;
        updateWatchInterface();
    }
    private void resetTimer(){
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }
    private void updateCountDownText(){
        int hours = (int) (mTimeLeftInMillis/1000)/3600;
        int minutes =(int)((mTimeLeftInMillis/1000)%3600)/60;
        int seconds = (int) (mTimeLeftInMillis/1000)%60;
        String timeLeftFormatted;
        if(hours>0){
            timeLeftFormatted = String.format( Locale.getDefault(),"%d:%02d:%02d",hours,minutes,seconds);
        }else {
            timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        }
        mTextViewCountDown.setText(timeLeftFormatted);
    }
    private void updateWatchInterface(){
        if(mTimerRunning){
            hours.setVisibility(View.INVISIBLE);
            minutes.setVisibility(View.INVISIBLE);
            seconds.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
        }
        else {
            hours.setVisibility(View.VISIBLE);
            minutes.setVisibility(View.VISIBLE);
            seconds.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonStartPause.setText("Start");
            if(mTimeLeftInMillis<1000){
                mButtonStartPause.setVisibility(View.INVISIBLE);
            }
            else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }
            if(mTimeLeftInMillis<mStartTimeInMillis){
                mButtonReset.setVisibility(View.VISIBLE);
            }
            else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }
    private void closeKeyBoard(){
        View view = this.getActivity().getCurrentFocus();
        if(view!=null){
            InputMethodManager imn = (InputMethodManager) getContext().getSystemService( Context.INPUT_METHOD_SERVICE);
            imn.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = getContext().getSharedPreferences( "prefs", Context.MODE_PRIVATE );
        mStartTimeInMillis = prefs.getLong("startTimeInMillis",600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft",mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning",false);
        updateCountDownText();
        updateWatchInterface();
        if(mTimerRunning){
            mEndTime = prefs.getLong("endTime",0);
            mTimeLeftInMillis = mEndTime-System.currentTimeMillis();
            if(mTimeLeftInMillis<0){
                mTimeLeftInMillis = 0;
                mTimerRunning =false;
                updateCountDownText();
                updateWatchInterface();
            }
            else {
                startTimer();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences prefs = getContext().getSharedPreferences( "prefs", Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("startTimeInMillis",mStartTimeInMillis);
        editor.putLong("millisLeft",mTimeLeftInMillis);
        editor.putBoolean("timerRunning",mTimerRunning);
        editor.putLong("endTime",mEndTime);
        editor.apply();
        if(mCountDownTimer!=null){
            mCountDownTimer.cancel();
        }
    }
}