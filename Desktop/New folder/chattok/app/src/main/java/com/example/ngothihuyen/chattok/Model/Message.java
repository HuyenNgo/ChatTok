package com.example.ngothihuyen.chattok.Model;

import java.util.Date;

public class Message {

     private String IdMessage;
    private  String Conversation;
    private  String context;
    private  float  imageHeight;
    private float   imageWidth;
    private long    time;
    private  int    type;
    private  String user_id ;

    public Message()
    {

    }
    public Message(String context,String user_id)

    {

       this.context=context;
       this.imageHeight=0;
       this.imageWidth=0;
       this.user_id=user_id;
       this.type=0;
       this.time=new Date().getTime();

    }
    public void MessageImage(String context,String user_id)
    {
        this.context=context;
        this.imageHeight=200;
        this.imageWidth=250;
        this.user_id=user_id;
        this.type=1;
        this.time=new Date().getTime();
    }

    public String getIdMessage() {
        return IdMessage;
    }

    public void setIdMessage(String idMessage) {
        IdMessage = idMessage;
    }

    public void setConversation(String conversation) {
        Conversation = conversation;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setImageWidth(float imageWidth) {
        this.imageWidth = imageWidth;
    }

    public void setImageHeight(float imageHeight) {
        this.imageHeight = imageHeight;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setuser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getConversation() {
        return Conversation;
    }

    public float getImageHeight() {
        return imageHeight;
    }

    public float getImageWidth() {
        return imageWidth;
    }

    public int getType() {
        return type;
    }

    public long getTime() {
        return time;
    }

    public String getContext() {
        return context;
    }

    public String getuser_id() {
        return user_id;
    }
}
