package com.example.ngothihuyen.chattok.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ngothihuyen.chattok.HomeActivity;
import com.example.ngothihuyen.chattok.Login;
import com.example.ngothihuyen.chattok.R;
import com.example.ngothihuyen.chattok.sign_in;
import com.google.firebase.auth.FirebaseAuth;

public class FlagmentMain extends AppCompatActivity {
    public  String userID;
    FirebaseAuth auth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_screen_start);


        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
           userID= auth.getCurrentUser().getUid();
            Intent intent=new Intent(FlagmentMain.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
         else
        {
            Intent intent=new Intent(FlagmentMain.this,Login.class);
            startActivity(intent);
            finish();
        }

    }
}
