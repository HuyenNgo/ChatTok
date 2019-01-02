package com.example.ngothihuyen.chattok.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ngothihuyen.chattok.AdapterView.AdapterMemberTeam;
import com.example.ngothihuyen.chattok.Model.Conversations;
import com.example.ngothihuyen.chattok.Model.Team;
import com.example.ngothihuyen.chattok.Model.User;
import com.example.ngothihuyen.chattok.Presentation.ContactPresenter;
import com.example.ngothihuyen.chattok.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScreenCreateGroup extends AppCompatActivity implements IContactView {

    private Toolbar toolbar;
    private ArrayList<User> listUser = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterMemberTeam adapterMemberTeam;
    private Button btCreateGroup;
    private EditText nameGroup;
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private  String userID=auth.getCurrentUser().getUid();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_create_group);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycleMember);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        btCreateGroup = (Button) findViewById(R.id.btCreate);
        nameGroup = (EditText) findViewById(R.id.editName);
        adapterMemberTeam = new AdapterMemberTeam(getBaseContext(), listUser);
        //recyclerView.setAdapter(adapterMemberTeam);
        ContactPresenter contactPresenter = new ContactPresenter();
        contactPresenter.getUser(this);

        btCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateGroup();
            }
        });

    }

    private void CreateGroup() {

        if (!validateForm()) {
            return;
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Conversation");
        Conversations conversations = new Conversations(nameGroup.getText().toString());
        String converID = databaseReference.push().getKey();

        databaseReference.child(converID).setValue(conversations);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Team");
        Team team = new Team(userID);
        String key=databaseReference.child(converID).push().getKey();
        databaseReference.child(converID).child(key).setValue(team);


    }


    private boolean validateForm() {
        boolean valid = true;

        String name = nameGroup.getText().toString();
        if (TextUtils.isEmpty(name)) {
            nameGroup.setError("Required.");
            valid = false;
        } else {
            nameGroup.setError(null);
        }

        return valid;
    }

    @Override
    public void getListUser(User usr) {
        listUser.add(usr);
        adapterMemberTeam.notifyDataSetChanged();

    }
}
