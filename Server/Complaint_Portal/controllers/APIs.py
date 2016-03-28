def make_complaint():
    if auth.is_logged_in():
        user = db.users(auth.user.id)
        complaint_level = int(request.vars["complaintlevel"]).strip()
        complaint_title = str(request.vars["title"]).strip()
        complaint_body = str(request.vars["description"]).strip()
        categoryID = int(request.vars["complaintcategory"]).strip()
        db.Complaint.insert(UserID=auth.user.id, title=complaint_title, body=complaint_body, ComplaintCategoryID=categoryID, ComplaintLevelID=complaint_level)
        user_hostel = user.Hostel
        #
        return dict(success=True)

def specific_complaint():
    if auth.is_logged_in():
        complaintID = int(request.vars["compId"]).strip()
        complaint = db.Complaint(complaintID)
        return dict(filedByUserId=complaint.UserID, title=complaint.title, description=complaint.body, createdat=complaint.CreatedAt, complaintstatus=complaint.ComplaintStatus, complaintcategory=complaint.ComplaintCategoryID, complaintlevel=complaint.ComplaintLevelID, upvotes=complaint.Upvotes, downvotes=complaint.Downvotes)

def list_all_user_complaints():
    if auth.is_logged_in():
        user = db.users(auth.user.id)
        user_complaints = db(db.Complaint.UserID==auth.user.id).select(orderby=~db.Complaint.CreatedAt)
