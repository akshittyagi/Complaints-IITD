package com.example.akty7.assignmenttwo.AdminActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.akty7.assignmenttwo.Activity_Login;
import com.example.akty7.assignmenttwo.HelperClass.UserIn;
import com.example.akty7.assignmenttwo.R;

public class Activity_UserManagement extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionsearch, menu);
        MenuItem item = menu.findItem(R.id.action_search);

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
        alertDialogBuilder.setTitle(user.firstname+ " "+ user.lastname);
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
