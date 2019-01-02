package com.example.ngothihuyen.chattok.AdapterView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ngothihuyen.chattok.Model.Message;
import com.example.ngothihuyen.chattok.Model.User;
import com.example.ngothihuyen.chattok.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterMessage extends RecyclerView.Adapter<AdapterMessage.ViewHolder>

{
    public static  final int MS_RIGHT=0;
    public static  final  int MS_LEFT=1;
    private Context context;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference data;
    private FirebaseAuth Auth=FirebaseAuth.getInstance();
    private List<Message> arrMessage;
    private int flag=0;

   public AdapterMessage(Context context,ArrayList<Message> arrMessage)
    {

        this.context = context;
        this.arrMessage= arrMessage;
    }

    @NonNull
    @Override
    public AdapterMessage.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        if(viewType==MS_RIGHT)
        {
             if(flag==0) {
                 View view = LayoutInflater.from(context).inflate(R.layout.layout_itemright, viewGroup, false);
                 return new AdapterMessage.ViewHolder(view);
             }
             else
             {
                 View view = LayoutInflater.from(context).inflate(R.layout.layout_imageright, viewGroup, false);
                 return new AdapterMessage.ViewHolder(view);
             }
        }
        else
            {
                if (flag == 0)
                {
                    View view = LayoutInflater.from(context).inflate(R.layout.layout_item_left, viewGroup, false);
                    return new AdapterMessage.ViewHolder(view);
                }
                else
                    {
                    View view = LayoutInflater.from(context).inflate(R.layout.layout_imageleft, viewGroup, false);
                    return new AdapterMessage.ViewHolder(view);
                }
            }

    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterMessage.ViewHolder viewHolder, int position) {

if(position<arrMessage.size()) {
    Message message = arrMessage.get(position);

    data = firebaseDatabase.getReference("Users").child(message.getuser_id());

    data.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            User user = dataSnapshot.getValue(User.class);
            if (user.getAvatar().equals("default")) {
                viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
            } else
                Glide.with(context).load(user.getAvatar()).into(viewHolder.imageView);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    if (flag == 0) {
        viewHolder.tvContext.setText(message.getContext());
    } else if (flag == 1) {
        Glide.with(context).load(message.getContext()).into(viewHolder.imageSent);

    }
    //viewHolder.tvTime.setText(android.text.format.DateFormat.format("(HH:mm:ss))",message.getTime()));
    flag = 0;
}
    }

    @Override
    public int getItemCount() {
        return arrMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
       CircleImageView imageView;
       TextView  tvContext;
       TextView tvTime;
       ImageButton imageSent;

        public ViewHolder(@NonNull View itemView) {
           super(itemView);
              imageView=itemView.findViewById(R.id.image_user);
              tvContext=itemView.findViewById(R.id.tvBody);
              tvTime=itemView.findViewById(R.id.tvTime);
              imageSent=itemView.findViewById(R.id.imageSent);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if( arrMessage.get(position).getType()==0)
            flag=0;
        else flag=1;

        if (arrMessage.get(position).getuser_id().compareTo(Auth.getCurrentUser().getUid()) == 0 ) {

            return MS_RIGHT;
        }
        else
        {
            return MS_LEFT;
        }
    }
}
