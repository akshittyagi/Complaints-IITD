package com.example.akty7.assignmenttwo.HomeActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akty7.assignmenttwo.DividerItemDecoration;
import com.example.akty7.assignmenttwo.HelperClass.Complaint;
import com.example.akty7.assignmenttwo.JSONParser;
import com.example.akty7.assignmenttwo.R;

import java.util.ArrayList;

public class Fragment_MyActivity extends Fragment {

    JSONParser myac_jp;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    ComplaintRecyclerViewAdapter mAdapter;

    public Fragment_MyActivity() {
        // Required empty public constructor
        myac_jp = new JSONParser(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_myactivity, container, false);
        ArrayList<Complaint> usercomps = myac_jp.listOfPersonalComplaints();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.myactivity_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ComplaintRecyclerViewAdapter(usercomps);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;

    }

}