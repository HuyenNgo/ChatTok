package com.example.ngothihuyen.chattok.Model;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private  String userID;
    private  String teamID;
public  Team()
{

}

public Team (String userID)
{
    this.userID=userID;
}
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }
}
