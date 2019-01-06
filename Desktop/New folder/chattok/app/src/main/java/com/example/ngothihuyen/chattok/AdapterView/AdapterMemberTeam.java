package com.example.ngothihuyen.chattok.AdapterView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ngothihuyen.chattok.Model.User;
import com.example.ngothihuyen.chattok.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterMemberTeam extends RecyclerView.Adapter<AdapterMemberTeam.ViewHolder> {

    private Context context;
    private ArrayList<User> arruser=new ArrayList<>();
    public  AdapterMemberTeam(Context context, ArrayList<User> arruser)
    {
        this.context=context;
        this.arruser=arruser;

    }
    @NonNull
    @Override
    public AdapterMemberTeam.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(context).inflate(R.layout.layout_item_creategroup,viewGroup,false);

        return new AdapterMemberTeam.ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterMemberTeam.ViewHolder viewHolder, int position) {
        final User user=arruser.get(position);
        viewHolder.tvName.setText(user.getDisplayname());
        if (user.getAvatar().equals("default")) {
            viewHolder.circleImageView.setImageResource(R.mipmap.ic_launcher);
        } else
            Glide.with(context).load(user.getAvatar()).into(viewHolder.circleImageView);


              viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if(!viewHolder.checkBox.isChecked()) {

                          viewHolder.checkBox.setChecked(true);
                      }
                      if(onItemClickedListener!=null)
                      {
                          onItemClickedListener.onItemClick(user.getIduser().toString());
                      }
                  }
              });

    }


    @Override
    public int getItemCount() {
        return arruser.size();
    }
    public  class  ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName;
        CircleImageView circleImageView;
        CheckBox checkBox;

        public ViewHolder(View view)
        {

            super( view);
            tvName=view.findViewById(R.id.tvNameUser);
            circleImageView=view.findViewById(R.id.imageUser);
            checkBox=view.findViewById(R.id.checkbox);
    }
    }
    public interface OnItemClickedListener {
        void onItemClick(String userID);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
