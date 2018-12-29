package com.example.ngothihuyen.chattok.Presentation;


import android.support.annotation.NonNull;

import com.example.ngothihuyen.chattok.Model.User;
import com.example.ngothihuyen.chattok.View.IContactView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactPresenter implements IContactPresenter {




    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dataRf;

    public ContactPresenter(){
        firebaseDatabase  = FirebaseDatabase.getInstance();
        dataRf =  firebaseDatabase.getReference("Users");
    }

    @Override
    public void getUser(final IContactView usrView) {


        dataRf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()){
                    User _usr = data.getValue(User.class);
                      _usr.setIduser(data.getKey());
                    usrView.getListUser(_usr);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
