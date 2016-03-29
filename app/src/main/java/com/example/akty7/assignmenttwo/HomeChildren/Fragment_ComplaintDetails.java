package com.example.akty7.assignmenttwo.HomeChildren;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.akty7.assignmenttwo.HelperClass.Complaint;
import com.example.akty7.assignmenttwo.JSONParser;
import com.example.akty7.assignmenttwo.R;
import com.github.clans.fab.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_ComplaintDetails extends Fragment {
    JSONParser jp;
    String compid;
    View rootView;

    public Fragment_ComplaintDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_complaint_details,container,false);
        jp = new JSONParser(getActivity().getApplicationContext());
        compid = this.getArguments().getString("complaintid");
        jp.specificComplaint(Fragment_ComplaintDetails.this,compid);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.comp_upvote_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jp.upvote(Fragment_ComplaintDetails.this,compid);
            }
        });

        FloatingActionButton fab1 = (FloatingActionButton) rootView.findViewById(R.id.comp_downvote_fab);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jp.downvote(Fragment_ComplaintDetails.this,compid);
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) rootView.findViewById(R.id.comp_resolve_fab);
        if(false) fab2.setVisibility(View.GONE);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jp.setResolved(Fragment_ComplaintDetails.this,compid);
            }
        });

        return rootView;
    }

    public void specificComplaintCallBack(boolean success, Complaint c){
        if(!success){
            Snackbar.make(rootView.findViewById(R.id.comp_details_title),"Sorry.. There was some problem",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Action", null).show();
        }
        else{
            ((TextView) rootView.findViewById(R.id.comp_details_title)).setText(c.title);
            ((TextView) rootView.findViewById(R.id.comp_details_desc)).setText(c.description);
            ((TextView) rootView.findViewById(R.id.comp_details_creator)).setText(c.filedByUserId);
            ((TextView) rootView.findViewById(R.id.comp_details_categ)).setText(c.complaintcategory);
            ((TextView) rootView.findViewById(R.id.comp_details_date)).setText(c.createdat);
            ((TextView) rootView.findViewById(R.id.comp_details_level)).setText(c.complaintlevel);
        }
    }

    public void upvoteCallBack(){
        ((FloatingActionButton) rootView.findViewById(R.id.comp_upvote_fab)).setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_star));
    }
    public void downVoteCallBack(){
        ((FloatingActionButton) rootView.findViewById(R.id.comp_upvote_fab)).setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_image_timer_auto));
        ((FloatingActionButton) rootView.findViewById(R.id.comp_downvote_fab)).setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_close));
    }
    public void resolvedCallBack(){
        ((FloatingActionButton) rootView.findViewById(R.id.comp_resolve_fab)).setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_action_done));
    }



}
