package com.example.ngothihuyen.chattok.Model;

public class Participant {
    private  String RoomID;
    private String UserID1;
    private String UserID2;

    public Participant()
    {}
    public Participant(String Conversation,String  User_id1,String User_id2)
    {

        this.RoomID=Conversation;
        this.UserID1=User_id1;
        this.UserID2=User_id2;
    }

    public String getRoomID() {
        return RoomID;
    }

    public String getUserID1() {
        return UserID1;
    }

    public void setRoomID(String conversation) {
        RoomID = conversation;
    }

    public void setUserID1(String user_ID) {
        UserID1 = user_ID;
    }

    public String getUserID2() {
        return UserID2;
    }

    public void setUserID2(String userID2) {
        UserID2 = userID2;
    }
}
