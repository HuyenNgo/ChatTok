package com.example.ngothihuyen.chattok.AdapterView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ngothihuyen.chattok.Model.Friends;
import com.example.ngothihuyen.chattok.R;


import java.util.ArrayList;
import java.util.List;
import com.example.ngothihuyen.chattok.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterFriend extends RecyclerView.Adapter<AdapterFriend.ViewHolder>  {

    private  Context context;
    private List<User> arrayUser=new ArrayList<>();
    private ViewHolder viewHolder;

    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private DatabaseReference databaseReference;


    public AdapterFriend(Context context, ArrayList<User> arrUser) {
        this.context = context;
        this.arrayUser = arrUser;
        databaseReference=firebaseDatabase.getReference("Friends").child(auth.getCurrentUser().getUid());

    }

    @NonNull
    @Override
    public AdapterFriend.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.layout_itemfriend,viewGroup,false);
        return new AdapterFriend.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return arrayUser.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterFriend.ViewHolder viewHolder, int position) {

         final User user=arrayUser.get(position);
        viewHolder.tvName.setText(user.getDisplayname().toString());
        if (user.getAvatar().equals("default")) {
            viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        } else
            Glide.with(context).load(user.getAvatar()).into(viewHolder.imageView);

        viewHolder.nofriend.setVisibility(View.VISIBLE);
        viewHolder.isfriend.setVisibility(View.GONE);

        viewHolder.nofriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Friends friends=new Friends(user.getIduser());

                String key=databaseReference.push().getKey();
                databaseReference.child(key).setValue(friends);

                viewHolder.nofriend.setVisibility(View.GONE);
                viewHolder.isfriend.setVisibility(View.VISIBLE);
            }
        });

    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName;
        CircleImageView isfriend;
        CircleImageView  nofriend;
        CircleImageView imageView;
        public  ViewHolder(View view)
        {
            super(view);

            tvName=view.findViewById(R.id.tvNameFriend);
            isfriend=view.findViewById(R.id.friend);
            nofriend=view.findViewById(R.id.NoFriend);
            imageView=view.findViewById(R.id.imageUser);
        }

    }
}
