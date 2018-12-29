package com.example.ngothihuyen.chattok.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ngothihuyen.chattok.AdapterView.AdapterTeam;
import com.example.ngothihuyen.chattok.Model.Team;
import com.example.ngothihuyen.chattok.Presentation.TeamPresenter;
import com.example.ngothihuyen.chattok.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FlagmentTeam extends Fragment implements ITeamView{

    private static int spanCount=2;
    private int orientation;
    private RecyclerView listTeam;
    private ArrayList<Team> lsTeam=new ArrayList<>();
    private AdapterTeam adapterTeam;
    private CircleImageView circleImageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_team,container,false);

         listTeam=(RecyclerView)view.findViewById(R.id.recycle_team);
         circleImageView=(CircleImageView)view.findViewById(R.id.imageGroup);
        orientation=GridLayoutManager.VERTICAL;
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(orientation);
        listTeam.setLayoutManager(gridLayoutManager);



        DisplayTeam();

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddGroup();
            }
        });
        return view;
    }

    private void AddGroup() {

        Intent intent=new Intent(getContext(),ScreenCreateGroup.class);
        startActivity(intent);
    }

    public  void DisplayTeam()
     {
         TeamPresenter teamPresenter=new TeamPresenter();
         teamPresenter.getTeam(this);

         adapterTeam=new AdapterTeam(getContext(),lsTeam);
         listTeam.setAdapter(adapterTeam);
        lsTeam.clear();


     }
    @Override
    public void getlistTeam(Team team) {
        lsTeam.add(team);

        adapterTeam.notifyDataSetChanged();
    }
}