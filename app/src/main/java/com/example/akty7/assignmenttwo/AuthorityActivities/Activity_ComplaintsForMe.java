package com.example.akty7.assignmenttwo.AuthorityActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.akty7.assignmenttwo.HelperClass.Complaint;
import com.example.akty7.assignmenttwo.HomeActivity.ComplaintRecyclerViewAdapter;
import com.example.akty7.assignmenttwo.R;

import java.util.ArrayList;

public class Activity_ComplaintsForMe extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private AuthorityComplaintsRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_for_me);

        mRecyclerView = (RecyclerView) findViewById(R.id.compsforme_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AuthorityComplaintsRecyclerViewAdapter(new ArrayList<Complaint>());
        mRecyclerView.setAdapter(mAdapter);
    }
}