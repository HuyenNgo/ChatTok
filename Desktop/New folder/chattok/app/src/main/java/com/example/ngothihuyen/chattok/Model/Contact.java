package com.example.ngothihuyen.chattok.Model;

public class Contact {

    private String NameUser;
    private String IsOnline;

    public Contact(String name,String isOnline)
    {
            this.NameUser=name;
            this.IsOnline=isOnline;

    }
    public String getNameUser()
    {
        return NameUser;
    }
    public String getIsOnline()
    {
        return IsOnline;
    }

    public void setIsOnline(String isOnline) {
        IsOnline = isOnline;
    }

    public void setNameUser(String nameUser) {
        NameUser = nameUser;
    }
}
