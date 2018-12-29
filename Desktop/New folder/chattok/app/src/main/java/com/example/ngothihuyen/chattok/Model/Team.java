package com.example.ngothihuyen.chattok.Model;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private  String idTeam;
    private  long   member;
    private String roomID;
public  Team()
{

}


    public  Team(String RoomID)
    {
        this.member=3;
        this.roomID=RoomID;
    }


    public String getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(String idTeam) {
        this.idTeam = idTeam;
    }

    public long getMember() {
        return member;
    }

    public void setMember(long member) {
        this.member = member;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }
}
