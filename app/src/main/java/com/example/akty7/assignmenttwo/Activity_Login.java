package com.example.akty7.assignmenttwo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.akty7.assignmenttwo.HelperClass.AuthChecker;
import com.example.akty7.assignmenttwo.HomeActivity.Activity_Home;

import java.util.ArrayList;

public class Activity_Login extends AppCompatActivity {
    JSONParser jp = new JSONParser();
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        Button loginBtn = (Button) findViewById(R.id.loginbtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) findViewById(R.id.userid)).getText().toString();
                String password = ((EditText) findViewById(R.id.password)).getText().toString();

              //  ArrayList<AuthChecker> x = jp.login(username, password, context);
                Boolean loginSuccess = true;
                if(loginSuccess){
                    Intent intent = new Intent(context,Activity_Home.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userid",username);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });


    }
}
