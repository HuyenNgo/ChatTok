package com.example.ngothihuyen.chattok.AdapterView;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ngothihuyen.chattok.Model.Conversations;
import com.example.ngothihuyen.chattok.Model.Participant;
import com.example.ngothihuyen.chattok.Model.User;
import com.example.ngothihuyen.chattok.MyParcelable;
import com.example.ngothihuyen.chattok.Presentation.ParticipantPresenter;
import com.example.ngothihuyen.chattok.R;
import com.example.ngothihuyen.chattok.View.FlagmentChat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterContact extends ArrayAdapter<User> implements IParticipant {

    public static final String TITLE = "title";
  private  final  ArrayList<Participant> listPart=new ArrayList<>();
    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference data;
    private FirebaseAuth Auth;
    private int resource;
    private List<User> arrUser;
    private String ConverID;
    private int flag=0;
    private  Participant participant;


    public AdapterContact(Context context, int resource, ArrayList<User> arrUser) {
        super(context, resource, arrUser);
        this.context = context;
        this.resource = resource;
        this.arrUser = arrUser;
        ParticipantPresenter participantPresenter=new ParticipantPresenter();
        participantPresenter.getParticipant(this);

    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_itemlistcontact, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv = (TextView) convertView.findViewById(R.id.name_id);
            viewHolder.online=(CircleImageView)convertView.findViewById(R.id.online);
            viewHolder.offline=(CircleImageView)convertView.findViewById(R.id.offline);
            viewHolder.imageView=(CircleImageView)convertView.findViewById(R.id.imageUser);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
          if(arrUser.size()!=0) {


              final User contact = arrUser.get(position);
              viewHolder.tv.setText(contact.getDisplayname());
              if (contact.getAvatar().equals("default")) {
                  viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
              } else
                  Glide.with(context).load(contact.getAvatar()).into(viewHolder.imageView);


              if (contact.getIsOnline().equals("online")) {
                  viewHolder.online.setVisibility(View.VISIBLE);
                  viewHolder.offline.setVisibility(View.GONE);
              } else {

                  viewHolder.offline.setVisibility(View.VISIBLE);
                  viewHolder.online.setVisibility(View.GONE);
              }
              Auth = FirebaseAuth.getInstance();
              viewHolder.tv.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      //Kiem tra xem da co nhom chat hay chua:
                      CheckRoom(Auth.getCurrentUser().getUid().toString(), contact.getIduser().toString());
                      if (flag == 0) {
                          CreateConverSation(viewHolder.tv.getText().toString());

                          CreateParticipant(ConverID, Auth.getCurrentUser().getUid().toString(), contact.getIduser().toString());
                      }
                      MyParcelable obj = new MyParcelable(ConverID.toString());
                      MyParcelable ID_uer = new MyParcelable(contact.getDisplayname().toString());
                      MyParcelable flag=new MyParcelable("0");
                      Bundle b = new Bundle();
                      b.putParcelable("Conversation", obj);
                      b.putParcelable("User_ID", ID_uer);
                      b.putParcelable("flag",flag);
                      Intent intent = new Intent(context, FlagmentChat.class);
                      intent.putExtras(b);
                      context.startActivity(intent);
                  }
              });

          }
        return convertView;
    }

    private void CheckRoom(String s, String s1) {



        participant=new Participant();

        for(int i=0;i<listPart.size();i++) {
            participant=listPart.get(i);
            if (participant.getUserID1().compareTo(s) == 0 && participant.getUserID2().compareTo(s1) == 0) {
                ConverID = participant.getRoomID().toString();
                flag = 1;
            }
            else if (participant.getUserID1().compareTo(s1)==0 && participant.getUserID2().compareTo(s)==0)
            {
                ConverID = participant.getRoomID().toString();
                flag = 1;
            }
        }



    }


    private void CreateParticipant(String converID, String Userid1,String userID2) {
        Participant participant=new Participant(converID,Userid1,userID2);
        firebaseDatabase=FirebaseDatabase.getInstance();
        data=firebaseDatabase.getReference("Participant");
        String particiId=data.push().getKey();
        data.child(particiId).setValue(participant);
    }

    private void CreateConverSation(String Name) {

        Conversations conversations=new Conversations(Name);
        firebaseDatabase = FirebaseDatabase.getInstance();
        data = firebaseDatabase.getReference("Conversation");
        String converID= data.push().getKey();
        ConverID=converID;
        data.child(converID).setValue(conversations);

    }

    @Override
    public void getListParticipant(Participant participant) {
         listPart.add(participant);

    }

    public class ViewHolder
    {
          TextView tv;
       CircleImageView online;
       CircleImageView offline;
        CircleImageView imageView;
    }
}
