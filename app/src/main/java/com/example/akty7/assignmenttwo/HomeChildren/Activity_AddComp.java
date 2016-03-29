package com.example.akty7.assignmenttwo.HomeChildren;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

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
        Complaint complaint = new Complaint();
        complaint.title = ((EditText) findViewById(R.id.addcomp_title)).getText().toString();
        complaint.complaintcategory = String.valueOf(((Spinner) findViewById(R.id.addcomp_categ)).getSelectedItemPosition());
        complaint.complaintlevel = String.valueOf(((Spinner) findViewById(R.id.addcomp_level)).getSelectedItemPosition());
        complaint.description = ((EditText) findViewById(R.id.addcomp_desc)).getText().toString();
        boolean success = jp.newComplaint(complaint);
        if(success){

        }
    }
}
