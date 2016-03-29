package com.example.akty7.assignmenttwo.AuthorityActivities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.akty7.assignmenttwo.Activity_Login;
import com.example.akty7.assignmenttwo.HelperClass.Complaint;
import com.example.akty7.assignmenttwo.HomeActivity.ComplaintRecyclerViewAdapter;
import com.example.akty7.assignmenttwo.JSONParser;
import com.example.akty7.assignmenttwo.R;

import java.util.ArrayList;

public class Activity_ComplaintsForMe extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private AuthorityComplaintsRecyclerViewAdapter mAdapter;
    JSONParser jp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_for_me);
        jp = new JSONParser(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.compsforme_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AuthorityComplaintsRecyclerViewAdapter(new ArrayList<Complaint>());
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_complaint, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            jp.logoutFromCompForMe(Activity_ComplaintsForMe.this);
        }
        return super.onOptionsItemSelected(item);
    }
    public void logoutCallBack(boolean logoutSuccess) {
        if (logoutSuccess) {
            startActivity(new Intent(Activity_ComplaintsForMe.this, Activity_Login.class));
            Activity_ComplaintsForMe.this.finish();
        } else {
          //  Snackbar.make(findViewById(R.id.), "Logout Failed", Snackbar.LENGTH_SHORT)
          //          .setAction("Action", null).show();
        }
    }
}
