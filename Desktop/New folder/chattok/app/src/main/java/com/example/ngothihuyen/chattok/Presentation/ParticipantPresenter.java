package com.example.ngothihuyen.chattok.Presentation;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ngothihuyen.chattok.AdapterView.IParticipant;
import com.example.ngothihuyen.chattok.Model.Participant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParticipantPresenter implements IParticipantPresenter {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference data;

    public ParticipantPresenter()
    {

        firebaseDatabase = FirebaseDatabase.getInstance();
        data = firebaseDatabase.getReference("Participant");
    }

    @Override
    public void getParticipant(final IParticipant iParticipant) {
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    Participant participant = data.getValue(Participant.class);
                   iParticipant.getListParticipant(participant);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
