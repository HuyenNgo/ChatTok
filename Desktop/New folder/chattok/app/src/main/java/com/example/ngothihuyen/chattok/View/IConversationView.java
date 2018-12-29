package com.example.ngothihuyen.chattok.View;

import com.example.ngothihuyen.chattok.Model.Conversations;
import com.example.ngothihuyen.chattok.Model.Participant;

public interface IConversationView {
    public void getListConverSation(Conversations conversations);
    public void getListParticipant(Participant participant);
}
