package com.example.ngothihuyen.chattok.Presentation;

import com.example.ngothihuyen.chattok.AdapterView.IParticipant;
import com.example.ngothihuyen.chattok.View.IConversationView;

public interface IConversationPresenter {
    public void getConversation(IConversationView IConversation);
    public void getParticipant(IConversationView Iparticipant);
}
