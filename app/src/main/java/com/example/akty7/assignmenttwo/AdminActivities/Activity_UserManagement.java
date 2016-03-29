package com.example.akty7.assignmenttwo.AdminActivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.example.akty7.assignmenttwo.Activity_Login;
import com.example.akty7.assignmenttwo.HelperClass.UserIn;
import com.example.akty7.assignmenttwo.JSONParser;
import com.example.akty7.assignmenttwo.R;

public class Activity_UserManagement extends AppCompatActivity {
Context context;
    JSONParser jp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        context = this;
        jp = new JSONParser(this);

        findViewById(R.id.user_manage_query_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Enter User ID");
                final EditText userid = new EditText(context);
                userid.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(userid);
                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jp.getUser(Activity_UserManagement.this, userid.getText().toString());
                        dialog.dismiss();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });


        (findViewById(R.id.user_manage_remove)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Enter User ID");
                final EditText userid = new EditText(context);
                userid.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(userid);
                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jp.removeUser(Activity_UserManagement.this, userid.getText().toString());
                        dialog.dismiss();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        (findViewById(R.id.user_manage_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Enter Details");
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText id = new EditText(context);
                final EditText firstname = new EditText(context);
                final EditText lastname = new EditText(context);
                final EditText category = new EditText(context);
                final EditText hostel = new EditText(context);
                final EditText password = new EditText(context);
                id.setHint("Kerberos ID");
                firstname.setHint("First Name");
                lastname.setHint("Last Name");
                category.setHint("Category");
                password.setHint("Password");
                hostel.setHint("Hostel");
                id.setInputType(InputType.TYPE_CLASS_TEXT);
                firstname.setInputType(InputType.TYPE_CLASS_TEXT);
                lastname.setInputType(InputType.TYPE_CLASS_TEXT);
                category.setInputType(InputType.TYPE_CLASS_TEXT);
                hostel.setInputType(InputType.TYPE_CLASS_TEXT);
                password.setInputType(InputType.TYPE_CLASS_TEXT);
                layout.addView(id);
                layout.addView(firstname);
                layout.addView(lastname);
                layout.addView(category);
                layout.addView(hostel);
                layout.addView(password);
                builder.setView(layout);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserIn user = new UserIn();
                        user.category = category.getText().toString();
                        user.kerberosid = id.getText().toString();
                        user.lastname = lastname.getText().toString();
                        user.affiliation = user.category;
                        user.firstname = firstname.getText().toString();
                        user.email = user.kerberosid + "@iitd.ac.in";
                        user.entryno = user.kerberosid;
                        user.hostel = hostel.getText().toString();
                        user.password = password.getText().toString();

                        jp.addUser(Activity_UserManagement.this, user);
                        dialog.dismiss();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_complaint, menu);
        return true;
    }

    public void logoutCallBack(boolean logoutSuccess) {
        if (logoutSuccess) {
            startActivity(new Intent(Activity_UserManagement.this, Activity_Login.class));
            Activity_UserManagement.this.finish();
        } else {
          //  Snackbar.make(findViewById(R.id.drawer_layout_home), "Logout Failed", Snackbar.LENGTH_SHORT)
          //          .setAction("Action", null).show();
        }
    }

    public void userAddedCallBack(){
         Snackbar.make(findViewById(R.id.user_manage_rel), "User Added", Snackbar.LENGTH_LONG)
                 .setAction("Action", null).show();
    }
    public void userDeletedCallBack(){
        Snackbar.make(findViewById(R.id.user_manage_rel), "User Deleted", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
    public void getUserCallBack(UserIn user){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Category: "+user.category+"\nKerberos ID: "+user.kerberosid);
        alertDialogBuilder.setTitle(user.firstname + " " + user.lastname);
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
