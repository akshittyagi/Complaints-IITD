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
import com.example.akty7.assignmenttwo.HelperClass.Notif;
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
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ct,"Error Signing in",Toast.LENGTH_LONG).show();
            }
        });
        q.add(jsonObjectRequest);

    }

    public void logout()
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
    }

    public boolean passwordReset(String newPass,String oldPass)
    {
        this.oldpass=oldPass;
        this.newpass=newPass;
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

        return ret.get(0).isSuccessful;
    }

    public boolean newComplaint(Complaint c)
    {
        this.complaintlevel = c.complaintlevel;
        this.title = c.title;
        this.description=c.description;
        this.complaintcategory=c.complaintcategory;

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

        return ret.get(0).isSuccessful;
    }

    public ArrayList<Complaint> listOfUserAllComplaints(UserIn user)
    {
        final Context ct=ctx;
        final ArrayList<Complaint> ret = new ArrayList<Complaint>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+allComplaintsOfUser, null, new Response.Listener<JSONObject>() {
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

    public Complaint specificComplaint(String compId)
    {
        this.compId=compId;
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
        return ret.get(0);
    }

    public ArrayList<Comment> loadAllComments(String complaintid)
    {
        this.compId=complaintid;
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

    public boolean addComment(final String description,final String userid, final String createdat,final String complaintID)
    {
        this.compId = complaintID;
        this.description = description;
        final Context ct=ctx;
        final ArrayList<AuthChecker> ret = new ArrayList<AuthChecker>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+addComment, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                        String success = response.getString("successful");
                        AuthChecker a = new AuthChecker();
                        if(!success.equals("true"))
                        {
                            a.isSuccessful = false;
                            Toast.makeText(ct, "Error Adding Comments", Toast.LENGTH_LONG).show();
                            ret.add(a);
                        }
                        else
                        {
                            a.isSuccessful=true;
                            String id = response.getString("id");
                            Comment c = new Comment();
                            c.createdByUserId = userid;
                            c.description = description;
                            c.createdat = createdat;
                            c.commentId = id;
                            ret.add(a);
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

        return ret.get(0).isSuccessful;
    }

    public boolean upvote(String complaintId)
    {
        this.compId = complaintId;
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
        return ret.get(0).isSuccessful;
    }

    public boolean downvote(String complaintId)
    {
        this.compId = complaintId;
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
        return ret.get(0).isSuccessful;
    }

    public boolean addUser(final UserIn user)
    {
        this.hostel = user.hostel;
        this.category = user.category;
        this.password = user.password;
        this.entrynumber = user.entryno;
        this.email = user.email;
        this.kerberosid = user.kerberosid;
        this.lastName = user.lastname;
        this.firstName = user.firstname;

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
                    a.user.firstname=user.firstname;
                    a.user.lastname=user.lastname;
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
        return ret.get(0).isSuccessful;
    }

    public UserIn getUser(String id)
    {
        this.userid = id;
        final Context ct=ctx;
        final ArrayList<UserIn> ret = new ArrayList<UserIn>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+getUser, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    UserIn a = new UserIn();
                    if(!response.getString("successful").equals("true"))
                    {
                        Toast.makeText(ct, "Error Fetching user", Toast.LENGTH_LONG).show();
                    }

                    JSONObject user = response.getJSONObject("user");
                    a.firstname=user.getString("firstname");
                    a.lastname=user.getString("lastname");
                    a.affiliation=user.getString("affiliation");
                    a.category=user.getString("category");
                    a.kerberosid=user.getString("kerberosid");
                    a.password=user.getString("password");
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
        return ret.get(0);
    }

    public ArrayList<String> listOfCategories()
    {
        final Context ct=ctx;
        final ArrayList<String> ret = new ArrayList<String>();
        RequestQueue q = Volley.newRequestQueue(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,main+categoryComplaint, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){

                try {
                    JSONArray categs = response.getJSONArray("categs");
                    for(int i=0;i<categs.length();i++)
                    {
                        JSONObject category = (JSONObject)categs.get(i);
                        ret.add(category.getString("category"));
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

    public void removeupvote(String comp_id) {

    }
}
