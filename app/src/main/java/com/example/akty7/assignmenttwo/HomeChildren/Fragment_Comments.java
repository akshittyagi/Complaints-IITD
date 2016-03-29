package com.example.akty7.assignmenttwo.HomeChildren;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.akty7.assignmenttwo.HelperClass.Comment;
import com.example.akty7.assignmenttwo.JSONParser;
import com.example.akty7.assignmenttwo.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Comments extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View rootView;
    JSONParser jp;
    String compid;

    public Fragment_Comments() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_comments, container, false);
        jp = new JSONParser(getContext());

        compid = this.getArguments().getString("complaintid");

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.comment_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add Comment");
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jp.addComment(Fragment_Comments.class,input.getText().toString(), compid);

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

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.comment_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        jp.loadAllComments(compid);
        return rootView;
    }

    public void getCommentsCallBack(ArrayList<Comment> arr){
        mAdapter = new CommentRecyclerViewAdapter(arr);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void getAddCommentCallBack(boolean success){
        if(!success) {
            Snackbar.make(rootView.findViewById(R.id.comment_recycler_view), "Sorry! Could not add Comment", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }else{
            Snackbar.make(rootView.findViewById(R.id.comment_recycler_view), "Done! Added Comment", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            jp.loadAllComments(compid);
        }

    }

}
