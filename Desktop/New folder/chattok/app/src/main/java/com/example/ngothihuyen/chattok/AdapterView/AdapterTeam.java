package com.example.ngothihuyen.chattok.AdapterView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ngothihuyen.chattok.Model.Conversations;
import com.example.ngothihuyen.chattok.Model.Team;
import com.example.ngothihuyen.chattok.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterTeam  extends  RecyclerView.Adapter<AdapterTeam.ViewHolder>{

    private List<Team> arrTeam;
    private Context context;
    private Map<String,String> people;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public AdapterTeam(Context context, ArrayList<Team> arrTeam)
    {
        this.context=context;
        this.arrTeam=arrTeam;
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Conversation");

    }
    @NonNull
    @Override
    public AdapterTeam.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.layout_item_team,viewGroup,false);
        return new AdapterTeam.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterTeam.ViewHolder viewHolder, int position) {

        //nạp dữ liệu vào item team

             Team team=arrTeam.get(position);
        databaseReference.child(team.getRoomID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Conversations conversations=dataSnapshot.getValue(Conversations.class);

                viewHolder.tvNameTeam.setText(conversations.getname().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return arrTeam.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView imageTeam;
        TextView tvNameTeam;
       public  ViewHolder(View view)
       {
           super(view);

           tvNameTeam=view.findViewById(R.id.tvNameTeam);
           imageTeam=view.findViewById(R.id.imTeam);
       }

    }
}
