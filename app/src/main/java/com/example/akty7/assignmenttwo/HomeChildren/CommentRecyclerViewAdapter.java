package com.example.akty7.assignmenttwo.HomeChildren;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.akty7.assignmenttwo.HelperClass.Comment;
import com.example.akty7.assignmenttwo.JSONParser;
import com.example.akty7.assignmenttwo.R;

import java.util.ArrayList;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.DataObjectHolder>{

    ArrayList<Comment> mDataset;
    JSONParser jp;
    Context context;
    public CommentRecyclerViewAdapter(ArrayList<Comment> comments) {
        jp = new JSONParser(context);
        mDataset = comments;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_comment, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        final Comment comm = mDataset.get(position);
        if(comm !=null) {
            //  holder.creator.setText(jp.getUser(comp.filedByUserId).name);
            holder.creator.setText(comm.createdByUserId);
            holder.desc.setText(comm.description);
            holder.date.setText(comm.createdat);
            holder.thumb.setText(comm.createdByUserId.charAt(0));

            holder.v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage(comm.description);
                    alertDialogBuilder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addItem(Comment dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        View v;
        TextView desc;
        TextView creator;
        TextView date;
        TextView thumb;
        public DataObjectHolder(View itemView) {
            super(itemView);
            v= itemView;
            creator = (TextView) itemView.findViewById(R.id.comp_card_creator);
            date = (TextView) itemView.findViewById(R.id.comp_card_date);
            thumb = (TextView) itemView.findViewById(R.id.thumb_comment);
            desc = (TextView) itemView.findViewById(R.id.comm_card_desc);

        }
    }
}
