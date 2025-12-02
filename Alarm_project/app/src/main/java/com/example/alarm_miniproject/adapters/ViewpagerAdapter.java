package com.example.alarm_miniproject.adapters;

import com.example.alarm_miniproject.fragments.BamgioFragment;
import com.example.alarm_miniproject.fragments.BaothucFragment;
import com.example.alarm_miniproject.fragments.GiotgFragment;
import com.example.alarm_miniproject.fragments.HengioFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewpagerAdapter extends FragmentStatePagerAdapter {

    public ViewpagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public Fragment getItem(int position) {

       switch(position)
        {
            case 0:
                return new BaothucFragment();
            case 1:

                return new BamgioFragment();
            case 2:
                return new HengioFragment();
            default:
                return new BaothucFragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String tittle="";
        switch(position)
        {
            case 0:
                tittle="Báo thức";
                break;
            case 1:

                tittle="Bấm giờ";
                break;
            case 2:
                tittle="Hẹn giờ";
                break;
        }

        return tittle;
    }
}
