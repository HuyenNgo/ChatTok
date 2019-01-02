package com.example.ngothihuyen.chattok.AdapterView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ngothihuyen.chattok.Model.Conversations;
import com.example.ngothihuyen.chattok.Model.Message;
import com.example.ngothihuyen.chattok.Model.Participant;
import com.example.ngothihuyen.chattok.Model.Team;
import com.example.ngothihuyen.chattok.Model.User;
import com.example.ngothihuyen.chattok.MyParcelable;
import com.example.ngothihuyen.chattok.Presentation.ParticipantPresenter;
import com.example.ngothihuyen.chattok.Presentation.TeamPresenter;
import com.example.ngothihuyen.chattok.R;
import com.example.ngothihuyen.chattok.View.FlagmentChat;
import com.example.ngothihuyen.chattok.View.ITeamView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterConversation extends RecyclerView.Adapter<AdapterConversation.RecyclerViewHolder> implements IParticipant,ITeamView {

    private List<Conversations> arrConversation;
    private Context context;
    private FirebaseAuth Auth=FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;

    private  final  ArrayList<Participant> listPart=new ArrayList<>();
    private String UserID=Auth.getCurrentUser().getUid();
    private  final  ArrayList<Team> listTeam=new ArrayList<>();
    private  int flag=1;

    public AdapterConversation(Context context, ArrayList<Conversations> arrConversation)
    {
        this.context=context;
        this.arrConversation=arrConversation;
        ParticipantPresenter participantPresenter=new ParticipantPresenter();
        participantPresenter.getParticipant(this);
        TeamPresenter teamPresenter=new TeamPresenter();
        teamPresenter.getTeam(this);

    }
    @Override
    public int getItemCount() {
        return arrConversation.size();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.layout_item_message,viewGroup,false);
        return new RecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder viewHolder, int position) {

        final Conversations _conver=arrConversation.get(position);
        if (_conver.getlastMessage().equals("Hello"))
        {
            viewHolder.tvContext.setText("Hello");
        }
        else {
            databaseReference = firebaseDatabase.getReference("Messages");
            databaseReference.child(_conver.getlastMessage().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Message message = dataSnapshot.getValue(Message.class);

              /*  if (message.getType() == 0)
                {
                    viewHolder.tvContext.setText("Bạn đã gửi một hình ảnh");
                }*/

                    if (message.getContext().length() > 40) {
                        String s = message.getContext().substring(0, 30);
                        s = s.concat("...");
                        viewHolder.tvContext.setText(s);

                    } else {
                        viewHolder.tvContext.setText(message.getContext().toString());
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
                viewHolder.tvTime.setText(android.text.format.DateFormat.format("(HH:mm:ss))",_conver.getlasttimeUpdate()));


             for (int i = 0; i < listPart.size(); i++) {
                 Participant participant = new Participant();
                 participant = listPart.get(i);
                 if (_conver.get_conversationKey().compareTo(participant.getRoomID()) == 0) {

                     flag=1;
                     String User1 = participant.getUserID1().toString();
                     String User2 = participant.getUserID2().toString();
                     if (participant.getUserID2().compareTo(UserID) == 0) {

                         databaseReference = firebaseDatabase.getReference("Users");
                         databaseReference.child(User1).addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 User user = dataSnapshot.getValue(com.example.ngothihuyen.chattok.Model.User.class);

                                 viewHolder.tvNameUser.setText(user.getDisplayname().toString());
                                 if (user.getAvatar().equals("default")) {
                                     viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
                                 } else
                                     Glide.with(context).load(user.getAvatar()).into(viewHolder.imageView);

                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });

                     } else {
                         databaseReference = firebaseDatabase.getReference("Users");
                         databaseReference.child(User2).addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 User user = dataSnapshot.getValue(com.example.ngothihuyen.chattok.Model.User.class);

                                 viewHolder.tvNameUser.setText(user.getDisplayname().toString());
                                 if (user.getAvatar().equals("default")) {
                                     viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
                                 } else
                                     Glide.with(context).load(user.getAvatar()).into(viewHolder.imageView);

                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });
                     }

                 }
             }



                 for(int j=0;j<listTeam.size();j++)
                 {
                     if(_conver.get_conversationKey().equals(listTeam.get(j).getTeamID()))
                     {
                         viewHolder.tvNameUser.setText(_conver.getname().toString());

                         Log.d("NameTeam", listTeam.get(j).getTeamID().toString());
                         viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
                         flag=0;
                     }
                 }



             //goi màn hình nhắn tin
                       if(flag==1) {
                           viewHolder.setItemClickListener(new ItemClickListener() {
                               @Override
                               public void onClick(View view, int position, boolean isLongClick) {
                                   MyParcelable obj = new MyParcelable(_conver.get_conversationKey().toString());
                                   MyParcelable flag = new MyParcelable("1");
                                   MyParcelable ID_uer = new MyParcelable(viewHolder.tvNameUser.getText().toString());
                                   Intent intent = new Intent(context, FlagmentChat.class);
                                   Bundle b = new Bundle();

                                   b.putParcelable("Conversation", obj);
                                   b.putParcelable("User_ID", ID_uer);
                                   b.putParcelable("flag", flag);
                                   intent.putExtras(b);
                                   context.startActivity(intent);
                               }
                           });
                       }
                       else {

                            viewHolder.setItemClickListener(new ItemClickListener() {
                                @Override
                                public void onClick(View view, int position, boolean isLongClick) {
                                    MyParcelable obj = new MyParcelable(_conver.get_conversationKey().toString());
                                    MyParcelable name = new MyParcelable(_conver.getname());
                                    MyParcelable flag=new MyParcelable("0");
                                    Bundle b = new Bundle();
                                    b.putParcelable("Conversation", obj);
                                    b.putParcelable("flag",flag);
                                    b.putParcelable("nameTeam",name);
                                    Intent intent = new Intent(context, FlagmentChat.class);
                                    intent.putExtras(b);
                                    context.startActivity(intent);
                                }
                            });
                       }

    }

    @Override
    public void getListParticipant(Participant participant) {
        if(participant.getUserID1().compareTo(UserID)==0 || participant.getUserID2().compareTo(UserID)==0) {
            listPart.add(participant);
        }

    }

    @Override
    public void getlistTeam(Team team) {
         listTeam.add(team);
    }

    public interface ItemClickListener {
        void onClick(View view, int position,boolean isLongClick);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
    {

        private ItemClickListener itemClickListener;
        CircleImageView imageView;
        TextView tvContext;
        TextView tvTime;
        TextView tvNameUser;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_users);
            tvContext=itemView.findViewById(R.id.tvMessage);
            tvTime=itemView.findViewById(R.id.tvTime);
            tvNameUser=itemView.findViewById(R.id.tvNameUser);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {

            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }

}
