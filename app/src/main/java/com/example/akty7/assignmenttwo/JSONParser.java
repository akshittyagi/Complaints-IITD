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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by akty7 on 28-Mar-16.
 */
public class JSONParser {

    ArrayList<Admin> retAdmins;
    ArrayList<Comment> retComments;
    ArrayList<Complaint> retComplaints;
    ArrayList<UserIn> retUsers;
    String main;
    String login;
    String logout;
    String passReset;
    String newComplaint;
    String allComplaints;
    String hostelComplaints;
    String instituteComplaints;
    String individualComplaints;
    String specificComplaint;
    String allComments;
    String addComment;
    String upvote;
    String downvote;
    String addUser;
    String getUser;
    Context ctx;

    public JSONParser(Context ctx)
    {
        this.ctx=ctx;
    }


    public ArrayList<AuthChecker> login(String username,String password)
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

    public ArrayList<AuthChecker> logout()
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

    public ArrayList<AuthChecker> passwordReset(String newPass,String oldPass)
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

    public ArrayList<AuthChecker> newComplaint(Complaint c)
    {
        final Complaint C=c;
        final Context ct = ctx;
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

    public ArrayList<Complaint> listOfAllComplaints()
    {
        final Context ct=ctx;
        final ArrayList<Complaint> ret = new ArrayList<Complaint>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+allComplaints, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    JSONArray complaints = response.getJSONArray("complaints");
                    for(int i=0;i<complaints.length();i++)
                    {
                        Complaint c = new Complaint();
                        JSONObject complaint = (JSONObject) complaints.get(i);
                        c.compId=complaint.getString("compId");
                        c.filedByUserId=complaint.getString("filedByUserId");
                        c.title=complaint.getString("title");
                        c.description=complaint.getString("description");
                        c.createdat=complaint.getString("createdat");
                        c.complaintstatus=complaint.getString("complaintstatus");
                        c.complaintcategory=complaint.getString("complaintcategory");
                        c.complaintlevel=complaint.getString("complaintlevel");
                        c.upvotes=complaint.getString("upvotes");
                        c.downvotes=complaint.getString("downvotes");
                        ret.add(c);
                    }
                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Loading Complaint", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error Loading Complaint",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);
        return ret;
    }

    public ArrayList<Complaint> listOfHostelComplaints()
    {
        final Context ct=ctx;
        final ArrayList<Complaint> ret = new ArrayList<Complaint>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+hostelComplaints, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    JSONArray complaints = response.getJSONArray("complaints");
                    for(int i=0;i<complaints.length();i++)
                    {
                        Complaint c = new Complaint();
                        JSONObject complaint = (JSONObject) complaints.get(i);
                        c.compId=complaint.getString("compId");
                        c.filedByUserId=complaint.getString("filedByUserId");
                        c.title=complaint.getString("title");
                        c.description=complaint.getString("description");
                        c.createdat=complaint.getString("createdat");
                        c.complaintstatus=complaint.getString("complaintstatus");
                        c.complaintcategory=complaint.getString("complaintcategory");
                        c.complaintlevel=complaint.getString("complaintlevel");
                        c.upvotes=complaint.getString("upvotes");
                        c.downvotes=complaint.getString("downvotes");
                        ret.add(c);
                    }
                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Loading Hostel Complaint", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error Loading Hostel Complaint",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);
        return ret;
    }

    public ArrayList<Complaint> listOfInstituteComplaints()
    {
        final Context ct=ctx;
        final ArrayList<Complaint> ret = new ArrayList<Complaint>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+instituteComplaints, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    JSONArray complaints = response.getJSONArray("complaints");
                    for(int i=0;i<complaints.length();i++)
                    {
                        Complaint c = new Complaint();
                        JSONObject complaint = (JSONObject) complaints.get(i);
                        c.compId=complaint.getString("compId");
                        c.filedByUserId=complaint.getString("filedByUserId");
                        c.title=complaint.getString("title");
                        c.description=complaint.getString("description");
                        c.createdat=complaint.getString("createdat");
                        c.complaintstatus=complaint.getString("complaintstatus");
                        c.complaintcategory=complaint.getString("complaintcategory");
                        c.complaintlevel=complaint.getString("complaintlevel");
                        c.upvotes=complaint.getString("upvotes");
                        c.downvotes=complaint.getString("downvotes");
                        ret.add(c);
                    }
                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Loading Institute Complaint", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error Loading Institute Complaint",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);
        return ret;
    }

    public ArrayList<Complaint> listOfPersonalComplaints()
    {
        final Context ct=ctx;
        final ArrayList<Complaint> ret = new ArrayList<Complaint>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+individualComplaints, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    JSONArray complaints = response.getJSONArray("complaints");
                    for(int i=0;i<complaints.length();i++)
                    {
                        Complaint c = new Complaint();
                        JSONObject complaint = (JSONObject) complaints.get(i);
                        c.compId=complaint.getString("compId");
                        c.filedByUserId=complaint.getString("filedByUserId");
                        c.title=complaint.getString("title");
                        c.description=complaint.getString("description");
                        c.createdat=complaint.getString("createdat");
                        c.complaintstatus=complaint.getString("complaintstatus");
                        c.complaintcategory=complaint.getString("complaintcategory");
                        c.complaintlevel=complaint.getString("complaintlevel");
                        c.upvotes=complaint.getString("upvotes");
                        c.downvotes=complaint.getString("downvotes");
                        ret.add(c);
                    }
                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Loading Personal Complaint", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error Loading Personal Complaint",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);
        return ret;
    }

    public ArrayList<Complaint> specificComplaint(String compId)
    {
        final Context ct=ctx;
        final ArrayList<Complaint> ret = new ArrayList<Complaint>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+specificComplaint, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                        Complaint c = new Complaint();
                        JSONObject complaint = response.getJSONObject("complaint");
                        c.compId=complaint.getString("compId");
                        c.filedByUserId=complaint.getString("filedByUserId");
                        c.title=complaint.getString("title");
                        c.description=complaint.getString("description");
                        c.createdat=complaint.getString("createdat");
                        c.complaintstatus=complaint.getString("complaintstatus");
                        c.complaintcategory=complaint.getString("complaintcategory");
                        c.complaintlevel=complaint.getString("complaintlevel");
                        c.upvotes=complaint.getString("upvotes");
                        c.downvotes=complaint.getString("downvotes");
                        ret.add(c);

                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Loading Specific Complaint", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error Loading Institute Specific Complaint",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);
        return ret;
    }

    public ArrayList<Comment> loadAllComments(String complaintid)
    {
        final Context ct=ctx;
        final ArrayList<Comment> ret = new ArrayList<Comment>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+allComments, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    JSONArray comments = response.getJSONArray("comments");
                    for(int i=0;i<comments.length();i++)
                    {
                        Comment c = new Comment();
                        JSONObject comment = (JSONObject) comments.get(i);
                        c.commentId = comment.getString("commentId");
                        c.createdat = comment.getString("createdat");
                        c.description = comment.getString("description");
                        c.createdByUserId = comment.getString("createduserid");
                        ret.add(c);

                    }
                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Loading All Comments", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error Loading All comments",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

        return ret;
    }

    public ArrayList<Comment> addComment(final String description,final String userid, final String createdat)
    {
        final Context ct=ctx;
        final ArrayList<Comment> ret = new ArrayList<Comment>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+addComment, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                        String success = response.getString("successful");
                        if(!success.equals("true"))
                        {
                            Toast.makeText(ct, "Error Adding Comments", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            String id = response.getString("id");
                            Comment c = new Comment();
                            c.createdByUserId = userid;
                            c.description = description;
                            c.createdat = createdat;
                            c.commentId = id;
                            ret.add(c);
                        }

                    }
                 catch (JSONException e) {
                    Toast.makeText(ct, "Error Adding Comments", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error Adding comments",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

        return ret;
    }

    public ArrayList<AuthChecker> upvote(String complaintId)
    {
        final Context ct=ctx;
        final ArrayList<AuthChecker> ret = new ArrayList<AuthChecker>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+upvote, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    AuthChecker a = new AuthChecker();
                    if(response.getString("successful").equals("true"))
                    {
                        a.isSuccessful = true;
                    }
                    {
                        a.isSuccessful = false;
                    }

                    ret.add(a);
                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Upvoting", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error upvoting",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);
        return ret;
    }

    public ArrayList<AuthChecker> downvote(String complaintId)
    {
        final Context ct=ctx;
        final ArrayList<AuthChecker> ret = new ArrayList<AuthChecker>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+downvote, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    AuthChecker a = new AuthChecker();
                    if(response.getString("successful").equals("true"))
                    {
                        a.isSuccessful = true;
                    }
                    {
                        a.isSuccessful = false;
                    }

                    ret.add(a);
                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Downvoting", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error downvoting",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);
        return ret;
    }

    public ArrayList<AuthChecker> addUser(final UserIn user)
    {
        final Context ct=ctx;
        final ArrayList<AuthChecker> ret = new ArrayList<AuthChecker>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+addUser, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    AuthChecker a = new AuthChecker();
                    if(response.getString("successful").equals("true"))
                    {
                        a.isSuccessful = true;
                    }
                    {
                        a.isSuccessful = false;
                    }

                    a.type="User";
                    a.user.name=user.name;
                    a.user.affiliation=user.affiliation;
                    a.user.category=user.category;
                    a.user.kerberosid=user.kerberosid;
                    a.user.password=user.password;

                    ret.add(a);
                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Adding user", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error adding user",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);
        return ret;
    }

    public ArrayList<AuthChecker> getUser(String id)
    {
        final Context ct=ctx;
        final ArrayList<AuthChecker> ret = new ArrayList<AuthChecker>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+getUser, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    AuthChecker a = new AuthChecker();
                    if(response.getString("successful").equals("true"))
                    {
                        a.isSuccessful = true;
                    }
                    {
                        a.isSuccessful = false;
                    }

                    JSONObject user = response.getJSONObject("user");
                    a.type="User";
                    a.user.name=user.getString("name");
                    a.user.affiliation=user.getString("affiliation");
                    a.user.category=user.getString("category");
                    a.user.kerberosid=user.getString("kerberosid");
                    a.user.password=user.getString("password");

                    ret.add(a);
                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Fetching user", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error fetching user",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);
        return ret;
    }
}
