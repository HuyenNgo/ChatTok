package com.example.ngothihuyen.chattok.Model;

import android.support.v7.app.AppCompatActivity;
import java.lang.Boolean;
import android.util.Log;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.Date;
@IgnoreExtraProperties
public class User {
    public   String avatar;
    public    String iduser;
    public   String displayname;
    public   String email;
    public  String isOnline;
    public   String status;
    public   long   createdate;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(long createdate) {
        this.createdate = createdate;
    }

    public User()
     {


     }
    public User(String displayname, String email)
    {
        this.avatar="default";
        this.displayname=displayname;
        this.email=email;
        this.status=" ";
        this.isOnline="offline";
        this.createdate=new Date().getTime();
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String  getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String online) {
        isOnline = online;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

}
