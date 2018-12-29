package com.example.ngothihuyen.chattok.Model;

import com.google.firebase.database.IgnoreExtraProperties;
import java.util.Date;
@IgnoreExtraProperties
public class Conversations {
  private String _conversationKey;
   private String name;
   private long   CreateDate;
   private String  lastMessage;
   private long  lasttimeUpdate;

   public Conversations()
   {}
   public  Conversations(String Name)
   {

      this.name=Name;
      this.CreateDate=new Date().getTime();
      this.lasttimeUpdate=new Date().getTime();
      this.lastMessage="Hello";
   }

   public void setCreateDate(long createDate) {
      CreateDate = createDate;
   }





   public void setlasttimeUpdate(long lastTimeUpdate) {
      lasttimeUpdate = lastTimeUpdate;
   }

   public long getCreateDate() {
      return CreateDate;
   }

   public long getlasttimeUpdate() {
      return lasttimeUpdate;
   }



   public String getlastMessage()
   {
       return lastMessage;
   }

    public void setlastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
   public String getname()
   {
       return  name;
   }
   public  void setname(String name)
   {
       this.name=name;
   }
    public String get_conversationKey() {
      return _conversationKey;
   }

   public void set_conversationKey(String _conversationKey) {
      this._conversationKey = _conversationKey;
   }
}


