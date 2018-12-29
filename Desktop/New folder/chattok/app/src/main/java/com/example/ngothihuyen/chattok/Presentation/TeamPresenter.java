package com.example.ngothihuyen.chattok.Presentation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ngothihuyen.chattok.Model.MemberTeam;
import com.example.ngothihuyen.chattok.Model.Team;
import com.example.ngothihuyen.chattok.View.ITeamView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TeamPresenter implements  ITeamPresenter {
    private ChildEventListener mChildEventListener;
     private FirebaseDatabase firebaseDatabase;
     private DatabaseReference data;
    public TeamPresenter()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        data = firebaseDatabase.getReference("Team");

    }
    @Override
    public void getTeam(final ITeamView teamView) {

        ChildEventListener childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable final String s) {
                Team team = dataSnapshot.getValue(Team.class);

                teamView.getlistTeam(team);

               data.child(dataSnapshot.getKey()).child("people").addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        MemberTeam memberTeam=dataSnapshot.getValue(MemberTeam.class);
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

    }
}
