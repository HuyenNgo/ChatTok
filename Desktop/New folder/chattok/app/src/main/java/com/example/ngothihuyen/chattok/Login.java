package com.example.ngothihuyen.chattok;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ngothihuyen.chattok.View.FlagmentMain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    private Button btSignin;
    private Button btLogin;
    private EditText editmail;
    private EditText editpass;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference("Users");

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_login);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, FlagmentMain.class));
            finish();
        }



        editmail = (EditText) findViewById(R.id.mail);
        editpass = (EditText) findViewById(R.id.password);
        btSignin = (Button) findViewById(R.id.bt_signin);
        btLogin = (Button) findViewById(R.id.btlogin);

        //Xử lý sự kiện sign-in
        btSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, sign_in.class);
                startActivity(intent);
                finish();
            }
        });

       //Xử lý sự kiện login
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                login();
            }
        });

    }
   //Đăng nhập người dùng
    private void login()
        {

            String mail = editmail.getText().toString();
            String password = editpass.getText().toString();
            if (!validateForm()) {
                return;
            }
            auth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful())
                    {
                        Toast toast = Toast.makeText(Login.this, "Dang nhap that bai", Toast.LENGTH_SHORT);
                        toast.show();
                        editmail.setText("");
                        editpass.setText("");
                    }
                    else
                    {
                            databaseReference.child(auth.getCurrentUser().getUid()).child("isOnline").setValue("online");
                        Intent intent=new Intent(Login.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }


    private boolean validateForm() {
        boolean valid = true;

        String email = editmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editmail.setError("Required.");
            valid = false;
        } else {
            editmail.setError(null);
        }

        String password = editpass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            editpass.setError("Required.");
            valid = false;
        } else {
            editpass.setError(null);
        }

        return valid;
    }
}
