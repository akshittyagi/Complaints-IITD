package com.example.akty7.assignmenttwo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.akty7.assignmenttwo.HelperClass.AuthChecker;
import com.example.akty7.assignmenttwo.HomeActivity.Activity_Home;

import java.util.ArrayList;

public class Activity_Login extends AppCompatActivity {
    JSONParser jp;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        jp = new JSONParser(context);
        final Button loginBtn = (Button) findViewById(R.id.loginbtn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
        final EditText passET = (EditText) findViewById(R.id.password);
        passET.setOnEditorActionListener(new EditText.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(passET.getWindowToken(), 0);
                    loginBtn.performClick();
                    return true;
                }
                return false; // pass on to other listeners.
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) findViewById(R.id.userid)).getText().toString();
                String password = ((EditText) findViewById(R.id.password)).getText().toString();
                jp.login(Activity_Login.this,username, password);
            }
        });
    }

    public void loginSuccess(){
        Intent intent = new Intent(context, Activity_Home.class);
        Bundle bundle = new Bundle();
        bundle.putString("userid", ((EditText) findViewById(R.id.userid)).getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
        Activity_Login.this.finish();
    }

    public void loginFailed(){
        Button loginBtn = (Button) findViewById(R.id.loginbtn);
        ((EditText) findViewById(R.id.userid)).setText("");
        ((EditText) findViewById(R.id.password)).setText("");
        findViewById(R.id.userid).requestFocus();
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
        loginBtn.setAnimation(myAnim);
        loginBtn.startAnimation(myAnim);
    }

}