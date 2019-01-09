package com.example.ngothihuyen.chattok;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.ngothihuyen.chattok.AdapterView.AdapterViewHome;
import com.example.ngothihuyen.chattok.View.FlagmentProfile;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    AdapterViewHome adapterViewPagerHome;
    ViewPager viewPagerHome;
    RadioButton rdContact;
    RadioButton rdMessage;
    RadioButton rdTeam;
    RadioButton rdProfile;
    RadioGroup group;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_home);


        rdContact=(RadioButton)findViewById(R.id.rdContact);
        rdMessage=(RadioButton)findViewById(R.id.rdMessage);
        rdTeam=(RadioButton)findViewById(R.id.rdTeam);
        rdProfile=(RadioButton) findViewById(R.id.rdProfile);

        group=(RadioGroup)findViewById(R.id.group_radio);

        viewPagerHome = findViewById(R.id.viewpager_home);

        adapterViewPagerHome = new AdapterViewHome(getSupportFragmentManager());

        viewPagerHome.setAdapter(adapterViewPagerHome);

        viewPagerHome.addOnPageChangeListener(this);

        group.setOnCheckedChangeListener(this);


        rdMessage.setButtonDrawable( R.drawable.message_active_24px);
        rdContact.setButtonDrawable( R.drawable.friend_24);
        rdTeam.setButtonDrawable( R.drawable.group_24);
        rdProfile.setButtonDrawable(R.drawable.setting_24);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        rdMessage.setButtonDrawable( R.drawable.message_24px);
        rdContact.setButtonDrawable( R.drawable.friend_24);
        rdTeam.setButtonDrawable( R.drawable.group_24);
        rdProfile.setButtonDrawable(R.drawable.setting_24);
       switch (position) {
            case 0:
                rdMessage.setChecked(true);
                rdMessage.setButtonDrawable( R.drawable.message_active_24px);
                break;
            case 1:
                rdContact.setChecked(true);
                rdContact.setButtonDrawable( R.drawable.friend_active_24);
                break;
            case 2:
                rdTeam.setChecked(true);
                rdTeam.setButtonDrawable( R.drawable.group_active_24);
                break;
           case 3:
               rdProfile.setChecked(true);
               rdProfile.setButtonDrawable(R.drawable.setting_active_24);
               break;

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rdMessage:
                viewPagerHome.setCurrentItem(0);
                break;
            case R.id.rdContact:
                viewPagerHome.setCurrentItem(1);
                break;
            case R.id.rdTeam:
                viewPagerHome.setCurrentItem(2);
                break;
            case R.id.rdProfile:
                viewPagerHome.setCurrentItem(3);
                break;

        }
    }
}
