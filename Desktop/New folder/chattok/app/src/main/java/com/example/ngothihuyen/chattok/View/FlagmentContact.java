package com.example.ngothihuyen.chattok.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ngothihuyen.chattok.Model.User;
import com.example.ngothihuyen.chattok.Presentation.ContactPresenter;
import com.example.ngothihuyen.chattok.R;
import com.example.ngothihuyen.chattok.AdapterView.AdapterContact;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class FlagmentContact extends Fragment implements IContactView {


    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private  DatabaseReference data;

    private Context context;
    private ListView lv;

    public final  ArrayList<User>listItems  = new ArrayList<User>();

    public AdapterContact adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_contact,container,false);
        lv=(ListView)view.findViewById(R.id.lv_user);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        adapter = new AdapterContact(getContext(),R.layout.layout_itemlistcontact,
                listItems);

        lv.setAdapter(adapter);
          adapter.clear();
          listItems.clear();
        ContactPresenter userPresenter = new ContactPresenter();
        userPresenter.getUser(this);



    }

    @Override
    public void getListUser(User usr) {

            listItems.add(usr);
        adapter.notifyDataSetChanged();

    }
}


