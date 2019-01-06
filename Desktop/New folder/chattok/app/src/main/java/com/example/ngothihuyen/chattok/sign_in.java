package com.example.ngothihuyen.chattok;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;

import com.example.ngothihuyen.chattok.Model.Friends;
import com.example.ngothihuyen.chattok.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import android.widget.TextView;
import android.widget.Toast;

public  class sign_in extends AppCompatActivity  {


    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mDatabase;
    private DatabaseReference databaseReference;
    protected FirebaseAuth mFirebaseAuth ;
    private static final String TAG="TipCalculatorActivity";
    private EditText editName;
    private EditText editMail;
    private EditText editPass;
    private Button   btSignin;
    private TextView btLogin;
    private String userID;
    private  FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sign_in);


        mFirebaseAuth= FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        editMail=(EditText)findViewById(R.id.email_id);
        editName=(EditText)findViewById(R.id.displayname_id);
        editPass=(EditText)findViewById(R.id.password_id);
        btSignin=(Button)findViewById(R.id.signin_id);
        btLogin=(TextView) findViewById(R.id.login_id);

        btSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Signin(editName.getText().toString(),editMail.getText().toString(),editPass.getText().toString());
                }

        });
         btLogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 moveLogin();
             }
         });
    }

    private void moveLogin() {
        Intent intent=new Intent(sign_in.this,Login.class);
        startActivity(intent);

    }

    //Them User vao realtime Database
    private void createInforUser(String UserID,String displayName,String Email) {

        User user=new User(displayName,Email);
        mDatabase=FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.child(UserID).setValue(user);
        addUserChangeListener();

    }

    private void addUserChangeListener() {
        mDatabase.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(com.example.ngothihuyen.chattok.Model.User.class);
                if (user == null)
                {
                    return;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    // kiem tra thong tin dang nhap co bị trống hay không
    private boolean validateForm() {
        boolean valid = true;

        String email = editMail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editMail.setError("Required.");
            valid = false;
        } else {
            editMail.setError(null);
        }

        String password = editPass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            editPass.setError("Required.");
            valid = false;
        } else {
            editPass.setError(null);
        }

        return valid;
    }
//ham tao moi tai khoan
    private void Signin(final String name, final String Email, String PassWord)
    {
        if (!validateForm()) {
            return;
        }

        mFirebaseAuth.createUserWithEmailAndPassword(Email,PassWord).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    userID=FirebaseAuth.getInstance().getCurrentUser().getUid();

                    Toast toast=Toast.makeText(sign_in.this,"Đăng ký thành công",Toast.LENGTH_SHORT);
                    toast.show();
                    createInforUser(userID,name,Email);

                    databaseReference= mFirebaseInstance.getReference("Users").child(userID);
                    databaseReference.child("isOnline").setValue("online");

                    createFriends(userID);


                }
                else {

                    Toast toast=Toast.makeText(sign_in.this,"Đăng ký thất bại",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

    private void createFriends(String userID) {


        mDatabase=mFirebaseInstance.getReference("Friends");


          Friends friends=new Friends();
          friends.setUserID(userID);
          String key="thanhvien1";
          mDatabase.child(userID).child(key).setValue(friends);

        Intent intent=new Intent(sign_in.this,HomeActivity.class);
        startActivity(intent);
        finish();

    }


}
