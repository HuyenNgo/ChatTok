package com.example.ngothihuyen.chattok.AdapterView;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



import com.example.ngothihuyen.chattok.View.FlagmentMessage;
import com.example.ngothihuyen.chattok.View.FlagmentContact;
import com.example.ngothihuyen.chattok.View.FlagmentProfile;
import com.example.ngothihuyen.chattok.View.FlagmentTeam;

import java.util.ArrayList;
import java.util.List;
public class AdapterViewHome extends  FragmentStatePagerAdapter{

   List list;

     FlagmentContact flagContact;
    FlagmentMessage flagmentMessage;
    FlagmentTeam flagTeam;
    FlagmentProfile flagProfile;

    public  AdapterViewHome( FragmentManager fm)
    {
        super(fm);
       list=new ArrayList();


        flagContact = new FlagmentContact();
        flagmentMessage =new FlagmentMessage();
        flagTeam= new FlagmentTeam();
        flagProfile=new FlagmentProfile();
        list.add(flagmentMessage);
        list.add(flagContact);
        list.add(flagTeam);
        list.add(flagProfile);


    }

    @Override
    public Fragment getItem(int i) {
        if(i<list.size())
            return  (Fragment) list.get(i);
         else return (Fragment) list.get(0);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
