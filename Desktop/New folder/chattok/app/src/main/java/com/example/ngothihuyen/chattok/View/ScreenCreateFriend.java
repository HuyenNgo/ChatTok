package com.example.ngothihuyen.chattok.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ngothihuyen.chattok.AdapterView.AdapterFriend;
import com.example.ngothihuyen.chattok.AdapterView.AdapterTeam;
import com.example.ngothihuyen.chattok.Model.User;
import com.example.ngothihuyen.chattok.Presentation.FriendPresenter;
import com.example.ngothihuyen.chattok.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ScreenCreateFriend extends AppCompatActivity implements IContactView {

    private ArrayList<User> listUser=new ArrayList<>();

    private RecyclerView listContact;

    private FirebaseAuth auth=FirebaseAuth.getInstance();

    private AdapterFriend adapterFriend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_addfriend);

        listContact=(RecyclerView)findViewById(R.id.lvFriend);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        listContact.setLayoutManager(linearLayoutManager);
        listContact.setHasFixedSize(true);
        FriendPresenter friendPresenter=new FriendPresenter();
        friendPresenter.getUser(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        adapterFriend=new AdapterFriend(getBaseContext(),listUser);

        listContact.setAdapter(adapterFriend);

        listUser.clear();
    }

    @Override
    public void getListUser(User usr) {

        if(!(usr.getIduser().equals(auth.getCurrentUser().getUid())))
        listUser.add(usr);
        adapterFriend.notifyDataSetChanged();
    }
}
