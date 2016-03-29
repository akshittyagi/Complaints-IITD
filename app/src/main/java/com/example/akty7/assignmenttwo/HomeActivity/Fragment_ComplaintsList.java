package com.example.akty7.assignmenttwo.HomeActivity;

import android.content.Context;
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_myactivity, container, false);
        domain = this.getArguments().getString("level");

        myac_jp = new JSONParser(getActivity().getApplicationContext());

        if(domain == "personal"){
            myac_jp.listOfUserAllComplaints(Fragment_ComplaintsList.this);
        }
        else if(domain == "hostel"){
            myac_jp.listOfHostelComplaints(Fragment_ComplaintsList.this);
        }
        else if (domain == "insti"){
            myac_jp.listOfInstituteComplaints(Fragment_ComplaintsList.this);
        }

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.myactivity_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        return rootView;

    }
  /*  @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        ctx = view.getContext();
    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("level", domain);
    }

    public void getComplaintsCallBack(ArrayList<Complaint> arr){
        mAdapter = new ComplaintRecyclerViewAdapter(arr);
        mRecyclerView.setAdapter(mAdapter);
    }

}