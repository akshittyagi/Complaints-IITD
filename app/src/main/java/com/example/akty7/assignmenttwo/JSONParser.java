package com.example.akty7.assignmenttwo;

import android.app.Activity;
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
import com.example.akty7.assignmenttwo.AdminActivities.Activity_UserManagement;
import com.example.akty7.assignmenttwo.AuthorityActivities.Activity_ComplaintsForMe;
import com.example.akty7.assignmenttwo.HelperClass.Admin;
import com.example.akty7.assignmenttwo.HelperClass.AuthChecker;
import com.example.akty7.assignmenttwo.HelperClass.Comment;
import com.example.akty7.assignmenttwo.HelperClass.Complaint;
import com.example.akty7.assignmenttwo.HelperClass.Notif;
import com.example.akty7.assignmenttwo.HelperClass.UserIn;
import com.example.akty7.assignmenttwo.HomeActivity.Activity_Home;
import com.example.akty7.assignmenttwo.HomeActivity.Fragment_ComplaintsList;
import com.example.akty7.assignmenttwo.HomeChildren.Activity_AddComp;
import com.example.akty7.assignmenttwo.HomeChildren.Activity_Complaint;
import com.example.akty7.assignmenttwo.HomeChildren.Fragment_Comments;
import com.example.akty7.assignmenttwo.HomeChildren.Fragment_ComplaintDetails;

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
    String username;
    String password;
    String main="http://10.192.45.89:8000";
    String login="/Complaint_Portal/default/login.json?userid="+username+"&password="+password;
    String logout="/Complaint_Portal/default/logout.json";
    String oldpass;
    String newpass;
    String passReset="/Complaint_Portal/APIs/password_reset.json?old_pass="+oldpass+"&new_pass="+newpass;
    String complaintlevel;
    String title;
    String description;
    String complaintcategory;
    String newComplaint="/Complaint_Portal/APIs/make_complaint.json?complaint_level="+complaintlevel+"&complaint_title="+title+"&complaint_body="+description+"&categoryID="+complaintcategory;
    String allComplaintsOfUser="/Complaint_Portal/APIs/list_all_user_complaints.json";
    String hostelComplaints="/Complaint_Portal/APIs/list_all_hostel_complaints.json";
    String instituteComplaints="/Complaint_Portal/APIs/list_all_institute_complaints.json";
    String individualComplaints="/Complaint_Portal/APIs/list_all_personal_complaints.json";
    String compId;
    String specificComplaint="/Complaint_Portal/APIs/specific_complaint.json?complaintID="+compId;
    String allComments="/Complaint_Portal/APIs/get_comments.json?complaintID="+compId;
    String addComment="/Complaint_Portal/APIs/add_comment.json?complaintID="+compId+"&comment_body="+description;
    String upvote="/Complaint_Portal/APIs/upvote.json?complaintID="+compId;
    String downvote="/Complaint_Portal/APIs/downvote.json?complaintID="+compId;
    String firstName;
    String lastName;
    String email;
    String entrynumber;
    String category;
    String hostel;
    String kerberosid;
    String addUser="/Complaint_Portal/APIs/addUser.json?firstName="+firstName+"&lastName="+lastName+"&kerberos="+kerberosid+"&Email="+email+"&entryno="+entrynumber+"&passWord="+password+"&category="+category+"&hostel="+hostel;
    String userid;
    String getUser="/Complaint_Portal/APIs/getUser.json?user_id="+userid;
    String categoryComplaint="/Complaint_Portal/APIs/getListOfCategories.json";
    String getNotificationOfComplaint;
    Context ctx;

    public JSONParser(Context ctx)
    {
        this.ctx=ctx;
    }

    public String func(String category)
    {
        if(category.equals("0"))
        {
            return "admin";
        }
        else if(category.equals("1"))
        {
            return "student";
        }
        else if(category.equals("2"))
        {
            return "dean";
        }
        else if(category.equals("3"))
        {
            return "maintenance";
        }
        else if(category.equals("4"))
        {
            return "water";
        }
        else if(category.equals("5"))
        {
            return "electricity";
        }

        return "student";
    }
    public void login(final Activity_Login a,String username,String password)
    {
        this.username=username;
        this.password=password;
        String login="/Complaint_Portal/default/login.json?userid="+username+"&password="+password;
        final Context ct = ctx;
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+login, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    String succ=response.getString("success");
                    if(succ.equals("true"))
                    {
                        a.loginSuccess();
                    }
                    else
                    {
                        a.loginFailed();
                    }


                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Signing in", Toast.LENGTH_LONG).show();
                    a.loginFailed();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error Signing in",Toast.LENGTH_LONG).show();
                a.loginFailed();
            }
        });
        q.add(jsonObjectRequest);

    }

    public void logoutFromHome(final Activity_Home a)
    {
        final Context ct = ctx;
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+logout, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {

                    String succ=response.getString("success");
                    if(succ.equals("true"))
                    {
                        a.logoutCallBack(true);
                    }
                    else
                    {
                        a.logoutCallBack(false);
                    }



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
    }

    public void logoutFromAddComp(final Activity_AddComp a)
    {
        final Context ct = ctx;
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+logout, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {

                    String succ=response.getString("success");
                    if(succ.equals("true"))
                    {
                        a.logoutCallBack(true);
                    }
                    else
                    {
                        a.logoutCallBack(false);
                    }

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
    }

    public void logoutFromCompForMe(final Activity_ComplaintsForMe a)
    {
        final Context ct = ctx;
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+logout, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {

                    String succ=response.getString("success");
                    if(succ.equals("true"))
                    {
                        a.logoutCallBack(true);
                    }
                    else
                    {
                        a.logoutCallBack(false);
                    }

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
    }

    public void logoutFromComplaint(final Activity_Complaint a)
    {
        final Context ct = ctx;
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+logout, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {

                    String succ=response.getString("success");
                    if(succ.equals("true"))
                    {
                        a.logoutCallBack(true);
                    }
                    else
                    {
                        a.logoutCallBack(false);
                    }

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
    }

    public void logoutFromProfile(final Activity_Profile a)
    {
        final Context ct = ctx;
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+logout, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {

                    String succ=response.getString("success");
                    if(succ.equals("true"))
                    {
                        a.logoutCallBack(true);
                    }
                    else
                    {
                        a.logoutCallBack(false);
                    }

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
    }

    public void logoutUserManagement(final Activity_UserManagement a)
    {
        final Context ct = ctx;
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+logout, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {

                    String succ=response.getString("success");
                    if(succ.equals("true"))
                    {
                        a.logoutCallBack(true);
                    }
                    else
                    {
                        a.logoutCallBack(false);
                    }

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
    }


    public void passwordReset(final Activity_Home a,String newPass,String oldPass)
    {
        this.oldpass=oldPass;
        this.newpass=newPass;
        final String passReset="/Complaint_Portal/APIs/password_reset.json?oldPass="+oldPass+"&newPass="+newPass;

        final Context ct = ctx;
        final ArrayList<AuthChecker> ret = new ArrayList<AuthChecker>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+passReset, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {

                    String succ=response.getString("success");
                    if(succ.equals("true"))
                    {
                      a.passChangedCallBack(true);
                    }
                    else
                    {
                        a.passChangedCallBack(false);
                    }

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

    }

    public void newComplaint(final Activity_AddComp a,Complaint c)
    {
        this.complaintlevel = c.complaintlevel;
        this.title = c.title;
        this.description=c.description;
        this.complaintcategory=c.complaintcategory;
        String newComplaint="/Complaint_Portal/APIs/make_complaint.json?complaintlevel="+complaintlevel+"&title="+title+"&description="+description+"&complaintcategory="+complaintcategory;

        final Complaint C=c;
        final Context ct = ctx;
        final ArrayList<AuthChecker> ret = new ArrayList<AuthChecker>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+newComplaint, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {

                        boolean succ = response.getBoolean("success");
                            a.addedCallBack(succ);

                } catch (JSONException e) {
                    Toast.makeText(ctx, "Error Adding Complaint", Toast.LENGTH_LONG).show();
                    a.addedCallBack(false);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx,"Error Adding Complaint",Toast.LENGTH_LONG).show();
                a.addedCallBack(false);
            }
        });
        q.add(jsonObjectRequest);
    }

    public void listOfUserAllComplaints(final Fragment_ComplaintsList a)
    {
        final Context ct=ctx;
        final ArrayList<Complaint> ret = new ArrayList<Complaint>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+allComplaintsOfUser, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    
                    JSONArray complaints = response.getJSONArray("user_complaints");
                    for(int i=0;i<complaints.length();i++)
                    {
                        Complaint c = new Complaint();
                        JSONObject complaint = (JSONObject) complaints.get(i);
                        c.compId=complaint.getString("id");
                        c.filedByUserId=complaint.getString("UserID");
                        c.title=complaint.getString("title");
                        c.description=complaint.getString("body");
                        c.createdat=complaint.getString("CreatedAt");
                        c.complaintstatus=complaint.getBoolean("ComplaintStatus");
                        c.complaintcategory=complaint.getString("ComplaintCategoryID");
                        c.complaintlevel=complaint.getString("ComplaintLevelID");
                        c.upvotes=complaint.getString("Upvotes");
                        c.downvotes=complaint.getString("Downvotes");
                        ret.add(c);
                    }

                    a.getComplaintsCallBack(ret);

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

    }

    public class Pair<Complaint,String>
    {
        public Complaint complaint;
        public String hostel;
    }


    public void listOfHostelComplaints(final Fragment_ComplaintsList fragment_complaintsList)
    {
        final Context ct=ctx;
        final ArrayList<Pair<Complaint,String>> ret = new ArrayList<Pair<Complaint,String>>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+hostelComplaints, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    JSONArray complaints = response.getJSONArray("hostel_complaints");
                    JSONArray hostels = response.getJSONArray("user_hostel");
                    for(int i=0;i<complaints.length();i++)
                    {
                        Complaint c = new Complaint();
                        JSONObject complaint = (JSONObject) complaints.get(i);
                        c.compId=complaint.getString("id");
                        c.filedByUserId=complaint.getString("UserID");
                        c.title=complaint.getString("title");
                        c.description=complaint.getString("body");
                        c.createdat=complaint.getString("CreatedAt");
                        c.complaintstatus=complaint.getBoolean("ComplaintStatus");
                        c.complaintcategory=complaint.getString("ComplaintCategoryID");
                        c.complaintlevel=complaint.getString("ComplaintLevelID");
                        c.upvotes=complaint.getString("Upvotes");
                        c.downvotes=complaint.getString("Downvotes");
                        String hostel = (String)hostels.get(i);
                        Pair<Complaint,String> p = new Pair<Complaint,String>();
                        p.complaint = c;
                        p.hostel = hostel;
                        ret.add(p);
                    }

                    fragment_complaintsList.getHostelComplaintsCallBack(ret);
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

    }

    public void listOfInstituteComplaints(final Fragment_ComplaintsList fragment_complaintsList)
    {
        final Context ct=ctx;
        final ArrayList<Complaint> ret = new ArrayList<Complaint>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+instituteComplaints, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    JSONArray complaints = response.getJSONArray("institute_complaints");
                    for(int i=0;i<complaints.length();i++)
                    {

                        Complaint c = new Complaint();
                        JSONObject complaint = (JSONObject) complaints.get(i);
                        c.compId=complaint.getString("id");
                        c.filedByUserId=complaint.getString("UserID");
                        c.title=complaint.getString("title");
                        c.description=complaint.getString("body");
                        c.createdat=complaint.getString("CreatedAt");
                        c.complaintstatus=complaint.getBoolean("ComplaintStatus");
                        c.complaintcategory=complaint.getString("ComplaintCategoryID");
                        c.complaintlevel=complaint.getString("ComplaintLevelID");
                        c.upvotes=complaint.getString("Upvotes");
                        c.downvotes=complaint.getString("Downvotes");
                        ret.add(c);
                    }

                    fragment_complaintsList.getComplaintsCallBack(ret);
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

    }

    public void listOfPersonalComplaints(final Fragment_ComplaintsList a)
    {
        final Context ct=ctx;
        final ArrayList<Complaint> ret = new ArrayList<Complaint>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+individualComplaints, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    JSONArray complaints = response.getJSONArray("personal_complaints");
                    for(int i=0;i<complaints.length();i++)
                    {

                        Complaint c = new Complaint();
                        JSONObject complaint = (JSONObject) complaints.get(i);
                        c.compId=complaint.getString("id");
                        c.filedByUserId=complaint.getString("UserID");
                        c.title=complaint.getString("title");
                        c.description=complaint.getString("body");
                        c.createdat=complaint.getString("CreatedAt");
                        c.complaintstatus=complaint.getBoolean("ComplaintStatus");
                        c.complaintcategory=complaint.getString("ComplaintCategoryID");
                        c.complaintlevel=complaint.getString("ComplaintLevelID");
                        c.upvotes=complaint.getString("Upvotes");
                        c.downvotes=complaint.getString("Downvotes");
                        ret.add(c);
                    }

                    a.getComplaintsCallBack(ret);

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
    }

    public void specificComplaint(final Fragment_ComplaintDetails a, final String compId)
    {
        this.compId=compId;
        String specificComplaint="/Complaint_Portal/APIs/specific_complaint.json?compId="+compId;
        final Context ct=ctx;
        final ArrayList<Complaint> ret = new ArrayList<Complaint>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+specificComplaint, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject complaint){

                try {
                        Complaint c = new Complaint();
                        c.compId=compId;
                        c.filedByUserId=complaint.getString("filedByUserId");
                        c.title=complaint.getString("title");
                        c.description=complaint.getString("description");
                        c.createdat=complaint.getString("createdat");
                        c.complaintstatus=complaint.getBoolean("complaintstatus");
                        c.complaintcategory=complaint.getString("complaintcategory");
                        c.complaintlevel=complaint.getString("complaintlevel");
                        c.upvotes=complaint.getString("upvotes");
                        c.downvotes=complaint.getString("downvotes");
                        ret.add(c);

                        a.specificComplaintCallBack(true, c);

                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Loading Specific Complaint", Toast.LENGTH_LONG).show();
                    a.specificComplaintCallBack(false, null);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error Loading Institute Specific Complaint",Toast.LENGTH_LONG).show();
                a.specificComplaintCallBack(false, null);
            }
        });
        q.add(jsonObjectRequest);
    }

    public void loadAllComments(final Fragment_Comments a,String complaintid)
    {
        this.compId=complaintid;
        String allComments="/Complaint_Portal/APIs/get_comments.json?complaintid="+complaintid;

        final Context ct=ctx;
        final ArrayList<Comment> ret = new ArrayList<Comment>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+allComments, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    JSONArray comments = response.getJSONArray("comments");
                    for(int i=0;i<comments.length();i++) {
                        Comment c = new Comment();
                        JSONObject comment = (JSONObject) comments.get(i);
                        c.commentId = comment.getString("id");
                        c.createdat = comment.getString("WrittenAt");
                        c.description = comment.getString("body");
                        c.createdByUserId = comment.getString("UserID");
                        ret.add(c);

                    }

                    a.getCommentsCallBack(ret);

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

    }

    public void addComment(final Fragment_Comments a,final String description,final String complaintID)
    {
        this.compId = complaintID;
        this.description = description;
        String addComment="/Complaint_Portal/APIs/add_comment.json?complaintid="+complaintID+"&description="+description;

        final Context ct=ctx;
        final ArrayList<AuthChecker> ret = new ArrayList<AuthChecker>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+addComment, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    Boolean succ = response.getBoolean("success");
                    a.getAddCommentCallBack(succ);
                }
                 catch (JSONException e) {
                    Toast.makeText(ct, "Error Adding Comments", Toast.LENGTH_LONG).show();
                     a.getAddCommentCallBack(false);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error Adding comments",Toast.LENGTH_LONG).show();
                a.getAddCommentCallBack(false);
            }
        });
        q.add(jsonObjectRequest);

    }

    public void upvote(final Fragment_ComplaintDetails a,String complaintId)
    {
        this.compId = complaintId;
        String upvote="/Complaint_Portal/APIs/upvote.json?complaintid="+complaintId;

        final Context ct=ctx;
        final ArrayList<AuthChecker> ret = new ArrayList<AuthChecker>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+upvote, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {

                    boolean succ = response.getBoolean("success");
                    a.upvoteCallBack();
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
    }

    public void downvote(final Fragment_ComplaintDetails a,String complaintId)
    {
        this.compId = complaintId;
        String downvote="/Complaint_Portal/APIs/downvote.json?complaintid="+compId;

        final Context ct=ctx;
        final ArrayList<AuthChecker> ret = new ArrayList<AuthChecker>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+downvote, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                        boolean succ = response.getBoolean("success");
                        a.downVoteCallBack();
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

    }

    public String func1(String category)
    {
         if(category.equals("admin"))
         {
             return "0";
         }
         else if(category.equals("student"))
         {
             return "1";
         }
         else if(category.equals("dean"))
         {
             return "2";
         }
         else if(category.equals("maintenance"))
         {
             return "3";
         }
         else if(category.equals("water"))
         {
             return "4";
         }
         else if(category.equals("electricity"))
         {
             return "5";
         }

            return "1";
    }

    public void addUser(final Activity_UserManagement a,final UserIn user)
    {
        this.hostel = user.hostel;
        this.category = func1(user.category);
        this.password = user.password;
        this.entrynumber = user.entryno;
        this.email = user.email;
        this.kerberosid = user.kerberosid;
        this.lastName = user.lastname;
        this.firstName = user.firstname;
        String addUser="/Complaint_Portal/APIs/addUser.json?firstName="+firstName+"&lastName="+lastName+"&kerberosid="+kerberosid+"&email="+email+"&entrynumber="+entrynumber+"&password="+password+"&category="+category+"&hostel="+hostel;

        final Context ct=ctx;
        final ArrayList<AuthChecker> ret = new ArrayList<AuthChecker>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+addUser, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    boolean succ = response.getBoolean("success");
                    a.userAddedCallBack();
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
      }

    public void getUser(final Activity_UserManagement act,String id)
    {
        this.userid = id;
        String getUser="/Complaint_Portal/APIs/getUser.json?id="+id;

        final Context ct=ctx;
        final ArrayList<UserIn> ret = new ArrayList<UserIn>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+getUser, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    UserIn a = new UserIn();
                    JSONObject user = response.getJSONObject("user");
                    a.firstname=user.getString("firstName");
                    a.lastname=user.getString("lastName");
                    a.category = func(user.getString("category"));
                    a.kerberosid=user.getString("kerberosID");
                    a.password=user.getString("password");
                    act.getUserCallBack(a);


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

    }

    public void getUser(final Activity_Home act,String id)
    {
        this.userid = id;
        String getUser="/Complaint_Portal/APIs/getUser.json?id="+id;

        final Context ct=ctx;
        final ArrayList<UserIn> ret = new ArrayList<UserIn>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+getUser, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    UserIn a = new UserIn();
                    JSONObject user = response.getJSONObject("user");
                    a.firstname=user.getString("firstName");
                    a.lastname=user.getString("lastName");
                    a.category = func(user.getString("category"));
                    a.kerberosid=user.getString("kerberosID");
                    a.password=user.getString("password");
                    act.getUserCallBack(a.category);


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

    }


    public void listOfCategories()
    {
        final Context ct=ctx;
        final ArrayList<String> ret = new ArrayList<String>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+categoryComplaint, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    JSONArray categs = response.getJSONArray("list_of_categories");
                    for(int i=0;i<categs.length();i++)
                    {
                        String category = (String)categs.get(i);
                        ret.add(func(category));
                    }

                    //callback
                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Fetching Categories", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error fetching categories",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

    }

    public ArrayList<Notif> getNotifs()
    {
        final Context ct=ctx;
        final ArrayList<Notif> ret = new ArrayList<Notif>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+getNotificationOfComplaint, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    JSONArray notifs = response.getJSONArray("notifs");
                    for(int i=0;i<notifs.length();i++)
                    {
                        JSONObject category = (JSONObject)notifs.get(i);
                        Notif n = new Notif();
                        n.titleOfComplaint = category.getString("title");
                        n.type = category.getString("type");
                        ret.add(n);
                    }
                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Fetching Categories", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error fetching categories",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

        return ret;
    }

    public void getCurrentUser()
    {
        String getCurrentUser="/Complaint_Portal/APIs/getCurrentUser.json";
        final Context ct=ctx;
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+getCurrentUser, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {

                    UserIn a = new UserIn();
                    JSONObject user = response.getJSONObject("user");
                    a.userid = user.getString("username");
                    a.firstname=user.getString("first_name");
                    a.lastname=user.getString("last_name");
                    a.entryno=user.getString("entry_no");
                    a.category = func(user.getString("type_"));
                    //Callback required
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
    }

    public void removeUser(final Activity_UserManagement a,String userid)
    {
        String remove ="/Complaint_Portal/APIs/removeUser.json?id="+userid;
        final Context ct=ctx;
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+remove, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    boolean succ = response.getBoolean("success");
                    a.userDeletedCallBack();
                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Removinf User", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error removing user",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

    }


    public void setResolved(final Fragment_ComplaintDetails a,String comp_id)
    {
        String setresolved = "/Complaint_Portal/APIs/set_resolved.json?complaintid="+comp_id;
        final Context ct=ctx;
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+setresolved, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    boolean succ = response.getBoolean("success");
                    a.resolvedCallBack();
                } catch (JSONException e) {
                    Toast.makeText(ct, "Error Resolving Complaint", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error resolving complaint",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

    }
}
