package com.example.ngothihuyen.chattok.Presentation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ngothihuyen.chattok.Model.Message;
import com.example.ngothihuyen.chattok.View.IChatView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatPresenter implements IChatPresenter {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dataRf;
    private ChildEventListener mChildEventListener;
    private List<String> mCommentIds = new ArrayList<>();

    public  ChatPresenter()
    {

        firebaseDatabase  = FirebaseDatabase.getInstance();
        dataRf =  firebaseDatabase.getReference("Messages");
    }
    @Override
    public void getMessage(final IChatView iChatView) {

        ChildEventListener childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message _message = dataSnapshot.getValue(Message.class);
                _message.setIdMessage(dataSnapshot.getKey());
                mCommentIds.add(dataSnapshot.getKey());
                iChatView.getListMessage(_message);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message _messageNew = dataSnapshot.getValue(Message.class);
                _messageNew.setIdMessage(dataSnapshot.getKey());
              iChatView.getListMessage(_messageNew);
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
        dataRf.addChildEventListener(childEventListener);
        mChildEventListener=childEventListener;
    }
}
