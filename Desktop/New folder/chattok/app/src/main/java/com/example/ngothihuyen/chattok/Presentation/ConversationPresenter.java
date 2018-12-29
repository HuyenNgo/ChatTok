package com.example.ngothihuyen.chattok.Presentation;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ngothihuyen.chattok.AdapterView.IParticipant;
import com.example.ngothihuyen.chattok.Model.Conversations;
import com.example.ngothihuyen.chattok.Model.Participant;
import com.example.ngothihuyen.chattok.View.IConversationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConversationPresenter implements IConversationPresenter  {


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dataRf;
    private FirebaseDatabase firebaseDatabase1;
    private DatabaseReference dataRf1;
    public ConversationPresenter()
    {

        firebaseDatabase  = FirebaseDatabase.getInstance();
        dataRf =  firebaseDatabase.getReference("Conversation");
        firebaseDatabase1  = FirebaseDatabase.getInstance();
        dataRf1 =  firebaseDatabase.getReference("Participant");
    }


    @Override
    public void getParticipant(final IConversationView IConversation) {
        dataRf1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Participant participant=new Participant();
                    participant = data.getValue(Participant.class);
                    IConversation.getListParticipant(participant);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void getConversation(final IConversationView IConversation) {
        dataRf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){

                    Conversations _conver = data.getValue(Conversations.class);
                    _conver.set_conversationKey(data.getKey());
                    IConversation.getListConverSation(_conver);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
