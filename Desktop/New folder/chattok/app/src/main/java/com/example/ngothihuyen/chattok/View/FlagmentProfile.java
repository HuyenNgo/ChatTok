package com.example.ngothihuyen.chattok.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ngothihuyen.chattok.HomeActivity;
import com.example.ngothihuyen.chattok.Login;
import com.example.ngothihuyen.chattok.Model.User;
import com.example.ngothihuyen.chattok.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;


public class FlagmentProfile  extends Fragment {
    private ChildEventListener mChildEventListener;
    private Button btReplaceName;
    private Button btLogout;
    private Button btReplaceProfile;
    private TextView tvName;
    private ImageButton imageBack;
    private CircleImageView circleImageView;
    private FirebaseAuth Auth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    ;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String UserID = auth.getCurrentUser().getUid();
    private String avatarUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.layout_profile, container, false);

        btLogout = view.findViewById(R.id.btLogout);
        btReplaceName = view.findViewById(R.id.btReplaceName);
        btReplaceProfile = view.findViewById(R.id.btReplaceProfile);
        tvName = view.findViewById(R.id.tvName);
        imageBack = view.findViewById(R.id.image_back);
        circleImageView = view.findViewById(R.id.image_user);
        databaseReference = firebaseDatabase.getReference("Users").child(UserID);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                tvName.setText(user.getDisplayname().toString());
                if (user.getAvatar().equals("default")) {
                    circleImageView.setImageResource(R.mipmap.ic_launcher);
                } else
                    Glide.with(getActivity()).load(user.getAvatar()).into(circleImageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child("isOnline").setValue("offline");
                Auth.getInstance().signOut();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);

            }
        });

        btReplaceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ScreenReplaceName.class);
                startActivity(intent);
            }
        });


        btReplaceProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ScreenReplaceProfile.class);
                startActivity(intent);


            }
        });

        return view;

    }
}
