package com.example.akty7.assignmenttwo.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akty7.assignmenttwo.HelperClass.Complaint;
import com.example.akty7.assignmenttwo.HomeChildren.Activity_Complaint;
import com.example.akty7.assignmenttwo.JSONParser;
import com.example.akty7.assignmenttwo.R;

import java.util.ArrayList;

public class ComplaintRecyclerViewAdapter extends RecyclerView.Adapter<ComplaintRecyclerViewAdapter.DataObjectHolder>{

    ArrayList<Complaint> mDataset;
    JSONParser jp;
    Context context;
    public ComplaintRecyclerViewAdapter(ArrayList<Complaint> usercomps) {
        jp = new JSONParser(context);
        mDataset = usercomps;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_complaint, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        final Complaint comp = mDataset.get(position);

        holder.creator.setText(jp.getUser(comp.filedByUserId).name);
        if(comp.complaintstatus.equals("true")) holder.status.setImageResource(R.drawable.tick);
        else holder.status.setImageResource(R.drawable.cross);
        holder.date.setText(comp.createdat);
        holder.vote.setHint("[" + comp.upvotes + "/" + comp.downvotes + "]");
        holder.v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Activity_Complaint.class);
                Bundle bundle = new Bundle();
                bundle.putString("complaintid",comp.compId);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addItem(Complaint dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        View v;
        TextView title;
        TextView creator;
        TextView date;
        ImageView status;
        TextView vote;
        public DataObjectHolder(View itemView) {
            super(itemView);
            v= itemView;
            title = (TextView) itemView.findViewById(R.id.comp_card_title);
            creator = (TextView) itemView.findViewById(R.id.comp_card_creator);
            date = (TextView) itemView.findViewById(R.id.comp_card_date);
            status = (ImageView) itemView.findViewById(R.id.comp_card_image);
            vote = (TextView) itemView.findViewById(R.id.comp_card_updown);

        }
    }
}
