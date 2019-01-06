package com.example.ngothihuyen.chattok.Presentation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ngothihuyen.chattok.Model.Conversations;
import com.example.ngothihuyen.chattok.Model.Friends;
import com.example.ngothihuyen.chattok.Model.MemberTeam;
import com.example.ngothihuyen.chattok.Model.Team;
import com.example.ngothihuyen.chattok.View.ITeamView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeamPresenter implements  ITeamPresenter {
    private ChildEventListener mChildEventListener;
     private FirebaseDatabase firebaseDatabase;
     private DatabaseReference data;
     private FirebaseAuth auth=FirebaseAuth.getInstance();
     private String userID=auth.getCurrentUser().getUid();
     private List listMemberTeam=new ArrayList();
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

                Team team=dataSnapshot.getValue(Team.class);
                team.setTeamID(dataSnapshot.getKey());
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Friends friends = dataSnapshot1.getValue(Friends.class);
                    listMemberTeam.add(friends.getUserID());

                }
                for (int i=0;i<listMemberTeam.size();i++)
                {
                    if (userID.equals(listMemberTeam.get(i)))
                    {
                        teamView.getlistTeam(team);
                    }
                }
                listMemberTeam.clear();

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
             data.addChildEventListener(childEventListener);
             mChildEventListener=childEventListener;
    }
}
