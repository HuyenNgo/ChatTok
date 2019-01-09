package com.example.ngothihuyen.chattok.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ngothihuyen.chattok.MyParcelable;
import com.example.ngothihuyen.chattok.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScreenReplaceName extends AppCompatActivity {

    private EditText editName;
    private ImageButton imageButton;
    private Button btSave;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference("Users");
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private  String User_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_replacename);

        imageButton=(ImageButton)findViewById(R.id.image_back);
        editName=(EditText)findViewById(R.id.edit_name);
        btSave=(Button)findViewById(R.id.btsave);
         User_id=auth.getCurrentUser().getUid();
         btSave.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                     databaseReference.child(User_id).child("displayname").setValue(editName.getText().toString());


             }
         });

          imageButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  finish();

              }
          });
    }
}
