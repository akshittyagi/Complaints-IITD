package com.example.akty7.assignmenttwo.AdminActivities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.akty7.assignmenttwo.Activity_Login;
import com.example.akty7.assignmenttwo.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class Activity_UserManagement extends AppCompatActivity {

    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        searchView = (MaterialSearchView) findViewById(R.id.usermanage_search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionsearch, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
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
}
