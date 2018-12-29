package com.example.ngothihuyen.chattok.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.ngothihuyen.chattok.AdapterView.AdapterConversation;
import com.example.ngothihuyen.chattok.Model.Conversations;
import com.example.ngothihuyen.chattok.Model.Participant;
import com.example.ngothihuyen.chattok.Presentation.ConversationPresenter;
import com.example.ngothihuyen.chattok.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class FlagmentMessage extends Fragment implements IConversationView{

         private RecyclerView recyclerView;
         private AdapterConversation adapterConversation;
         private ArrayList<Conversations>  listConver=new ArrayList<>();
         private FirebaseAuth Auth=FirebaseAuth.getInstance();
         private  String UserID=Auth.getCurrentUser().getUid();
    private ArrayList<Participant>  listPart=new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_message,container,false);
        recyclerView=view.findViewById(R.id.lvConversation);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        adapterConversation=new AdapterConversation(getContext(),listConver);
         recyclerView.setAdapter(adapterConversation);

        listPart.clear();
        listConver.clear();

        ConversationPresenter conversationPresenter=new ConversationPresenter();
        conversationPresenter.getParticipant(this);
        conversationPresenter.getConversation(this);

    }


    @Override
    public void getListParticipant(Participant participant) {
        if(participant.getUserID2().compareTo(UserID)==0 || participant.getUserID1().compareTo(UserID)==0)
        {
            listPart.add(participant);
            adapterConversation.notifyDataSetChanged();
        }
    }
    @Override
    public void getListConverSation(Conversations conversations) {

         for(int i=0;i<listPart.size();i++) {
             Participant participant=listPart.get(i);

             if(conversations.get_conversationKey().compareTo(participant.getRoomID())==0)

             {
                 listConver.add(conversations);


             }
             adapterConversation.notifyDataSetChanged();

         }
    }
    }

