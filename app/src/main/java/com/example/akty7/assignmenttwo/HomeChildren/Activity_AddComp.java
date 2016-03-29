package com.example.akty7.assignmenttwo.HomeChildren;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.akty7.assignmenttwo.Activity_Login;
import com.example.akty7.assignmenttwo.HelperClass.Complaint;
import com.example.akty7.assignmenttwo.JSONParser;
import com.example.akty7.assignmenttwo.R;

public class Activity_AddComp extends AppCompatActivity {

    JSONParser jp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcomp);
        jp = new JSONParser(this);

        (findViewById(R.id.addcomp_fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Complaint complaint = new Complaint();
                complaint.title = ((EditText) findViewById(R.id.addcomp_title)).getText().toString();
                complaint.complaintcategory = String.valueOf(((Spinner) findViewById(R.id.addcomp_categ)).getSelectedItemPosition());
                complaint.complaintlevel = String.valueOf(((Spinner) findViewById(R.id.addcomp_level)).getSelectedItemPosition());
                complaint.description = ((EditText) findViewById(R.id.addcomp_desc)).getText().toString();
                jp.newComplaint(Activity_AddComp.this,complaint);
            }
        });


    }

    public void logoutCallBack(boolean logoutSuccess) {
        if (logoutSuccess) {
            startActivity(new Intent(Activity_AddComp.this, Activity_Login.class));
            Activity_AddComp.this.finish();
        } else {
            Snackbar.make(findViewById(R.id.drawer_layout_home), "Logout Failed", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
    }
}
