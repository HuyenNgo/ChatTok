package com.example.ngothihuyen.chattok.Model;

public class Friends {

    private String userID;

    public  Friends()
            {
                this.userID="A";
            }
     public Friends(String userID)
     {
         this.userID=userID;
     }
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
