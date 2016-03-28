package com.example.akty7.assignmenttwo;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.akty7.assignmenttwo.HelperClass.Admin;
import com.example.akty7.assignmenttwo.HelperClass.AuthChecker;
import com.example.akty7.assignmenttwo.HelperClass.Comment;
import com.example.akty7.assignmenttwo.HelperClass.Complaint;
import com.example.akty7.assignmenttwo.HelperClass.UserIn;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by akty7 on 28-Mar-16.
 */
public class JSONParser {

    ArrayList<Admin> retAdmins;
    ArrayList<Comment> retComments;
    ArrayList<Complaint> retComplaints;
    ArrayList<UserIn> retUsers;
    public String main;
    public String login;
    public String logout;
    public String passReset;
    public String newComplaint;
    public String allComplaints;
    public String hostelComplaints;
    public String instituteComplaints;
    public String individualComplaints;
    public String specificComplaint;
    public String allComments;
    public String addComment;
    public String upvote;
    public String downvote;
    public String addUser;

    public ArrayList<AuthChecker> login(String username,String password,Context ctx)
    {
        final ArrayList<AuthChecker> ret=new ArrayList<AuthChecker>();
        final Context ct = ctx;
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+login, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {

                    AuthChecker a=new AuthChecker();
                    String succ=response.getString("success");
                    if(succ.equals("true"))
                    {
                        a.isSuccessful=true;
                    }
                    else
                    {
                        a.isSuccessful=false;
                    }

                    ret.add(a);

                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Signing in", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error Signing in",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

        return ret;
    }

    public ArrayList<AuthChecker> logout(Context ctx)
    {
        final ArrayList<AuthChecker> ret=new ArrayList<AuthChecker>();
        final Context ct = ctx;
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+logout, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {

                    AuthChecker a=new AuthChecker();
                    String succ=response.getString("success");
                    if(succ.equals("true"))
                    {
                        a.isSuccessful=true;
                    }
                    else
                    {
                        a.isSuccessful=false;
                    }

                    ret.add(a);

                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Signing out", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error Signing out",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

        return ret;
    }

    public ArrayList<AuthChecker> passwordReset(String newPass,String oldPass,Context ctx)
    {
        final Context ct = ctx;
        final ArrayList<AuthChecker> ret = new ArrayList<AuthChecker>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+passReset, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    AuthChecker a=new AuthChecker();
                    String succ=response.getString("success");
                    if(succ.equals("true"))
                    {
                        a.isSuccessful=true;
                    }
                    else
                    {
                        a.isSuccessful=false;
                    }
                    ret.add(a);
                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Resetting password", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error Resetting password",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

        return ret;
    }

    public ArrayList<AuthChecker> newComplaint(Complaint c,Context ct)
    {
        final Complaint C=c;
        final Context ctx = ct;
        final ArrayList<AuthChecker> ret = new ArrayList<AuthChecker>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+newComplaint, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    AuthChecker a=new AuthChecker();
                    String succ=response.getString("success");
                    if(succ.equals("true"))
                    {
                        a.isSuccessful=true;
                    }
                    else
                    {
                        a.isSuccessful=false;
                    }
                    a.type="Complaint";
                    a.complaint.compId=C.compId;
                    a.complaint.filedByUserId=C.filedByUserId;
                    a.complaint.title=C.title;
                    a.complaint.description=C.description;
                    a.complaint.createdat=C.createdat;
                    a.complaint.complaintstatus=C.complaintstatus;
                    a.complaint.complaintcategory=C.complaintcategory;
                    a.complaint.complaintlevel=C.complaintlevel;
                    a.complaint.upvotes=C.upvotes;
                    a.complaint.downvotes=C.downvotes;
                    ret.add(a);


                } catch (JSONException e) {
                    Toast.makeText(ctx, "Error Adding Complaint", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx,"Error Adding Complaint",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

        return ret;
    }


}
