package com.example.akty7.assignmenttwo;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Activity_Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
    public void logoutCallBack(boolean logoutSuccess) {
        if (logoutSuccess) {
            startActivity(new Intent(Activity_Profile.this, Activity_Login.class));
            Activity_Profile.this.finish();
        } else {
        //    Snackbar.make(findViewById(R.id.drawer_layout_home), "Logout Failed", Snackbar.LENGTH_SHORT)
         //           .setAction("Action", null).show();
        }
    }
}
