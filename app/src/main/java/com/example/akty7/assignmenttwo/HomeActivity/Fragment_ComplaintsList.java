package com.example.akty7.assignmenttwo.HomeActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akty7.assignmenttwo.HelperClass.Complaint;
import com.example.akty7.assignmenttwo.JSONParser;
import com.example.akty7.assignmenttwo.R;

import java.util.ArrayList;

public class Fragment_ComplaintsList extends Fragment {

    JSONParser myac_jp;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    ComplaintRecyclerViewAdapter mAdapter;
    String domain;

    public Fragment_ComplaintsList() {
        // Required empty public constructor
        myac_jp = new JSONParser(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_myactivity, container, false);
        domain = this.getArguments().getString("level");
        ArrayList<Complaint> usercomps = new ArrayList<>();
        Complaint c1 = new Complaint();
        c1.complaintstatus = "true";
        c1.downvotes = "12";
        c1.upvotes = "33";
        c1.compId = "42";
        c1.complaintcategory="0";
        c1.complaintlevel="0";
        c1.createdat="morning";
        c1.filedByUserId="tera baap";
        c1.description="dessss";
        c1.title="This is hostel complaint.. panhe nahi hain bc";
        usercomps.add(0,c1);
        //        ArrayList<Complaint> usercomps = myac_jp.listOfPersonalComplaints();
        if(domain.equals("personal")) {}
        else if (domain.equals("hostel")) {}
        else {}


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.myactivity_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ComplaintRecyclerViewAdapter(usercomps);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("level", domain);
    }

}