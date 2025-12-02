package com.example.alarm_miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.alarm_miniproject.adapters.ViewpagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

  private BottomNavigationView bottomNavigationView;
    private ViewPager viewpager;
    ImageButton btcaidat;
    Menu menu;
    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottomnavigation);
        viewpager=findViewById(R.id.viewpager);
        btcaidat=findViewById(R.id.imgcaidat);


        ViewpagerAdapter viewpagerAdapter=new ViewpagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewpager.setAdapter(viewpagerAdapter);



        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.mnBaothuc).setChecked(true);
                        break;

                    case 1:

                        bottomNavigationView.getMenu().findItem(R.id.mnBamgio).setChecked(true);
                        break;

                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.mnHengio).setChecked(true);
                        break;
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.mnBaothuc:
                        viewpager.setCurrentItem(0);
                        break;



                    case R.id.mnBamgio:
                        viewpager.setCurrentItem(1);
                        break;

                    case R.id.mnHengio:
                        viewpager.setCurrentItem(2);
                        break;
                        

                }

                return false;
            }
        });



        btcaidat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu();

            }
        });

    }
    //thanh
    @Override
    protected void onStop() {
        super.onStop();

    }
    private void showMenu()
    {
        PopupMenu popupMenu=new PopupMenu( this,btcaidat );
        popupMenu.getMenuInflater().inflate( R.menu.menutoobar,popupMenu.getMenu() );
        popupMenu.show();
    }
}