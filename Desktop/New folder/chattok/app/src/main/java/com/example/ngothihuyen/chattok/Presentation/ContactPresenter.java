package com.example.ngothihuyen.chattok.Presentation;


import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ngothihuyen.chattok.Model.Friends;
import com.example.ngothihuyen.chattok.Model.User;
import com.example.ngothihuyen.chattok.View.IContactView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactPresenter implements IContactPresenter {



    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dataRf;
    private List listIdUser= new ArrayList();
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private String userId=auth.getCurrentUser().getUid();

    public ContactPresenter(){
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("Friends").child(userId);
        firebaseDatabase  = FirebaseDatabase.getInstance();
        dataRf =  firebaseDatabase.getReference("Users");
    }
     public  void GetListIdUser()
     {
         databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                       Friends friends = dataSnapshot1.getValue(Friends.class);

                       listIdUser.add(friends.getUserID().toString());

                       Log.d("FRIENDS", friends.getUserID().toString());
                   }

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
     }
    @Override
    public void getUser(final IContactView usrView) {

            GetListIdUser();
        dataRf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()){
                    User _usr = data.getValue(User.class);
                      _usr.setIduser(data.getKey());

                      for(int i=0;i<listIdUser.size();i++)
                      {
                          if (data.getKey().equals(listIdUser.get(i).toString()))
                              usrView.getListUser(_usr);
                      }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
