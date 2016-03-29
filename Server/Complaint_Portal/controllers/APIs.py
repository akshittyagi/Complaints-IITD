def password_reset():
    if auth.is_logged_in():
        user = db.users(auth.user.id)
        old_pass = str(request.vars["oldPass"]).strip()
        new_pass = str(request.vars["newPass"]).strip()
        user_pass = auth.user.password
        if user_pass==old_pass :
            auth.user.password = new_pass
            return dict(success=True)
        else:
            return dict(success=False)

def make_complaint():
    if auth.is_logged_in():
        user = db.users(auth.user.id)
        complaint_level = int(request.vars["complaintlevel"]).strip()
        complaint_title = str(request.vars["title"]).strip()
        complaint_body = str(request.vars["description"]).strip()
        categoryID = int(request.vars["complaintcategory"]).strip()
        db.Complaint.insert(UserID=auth.user.id, title=complaint_title, body=complaint_body, ComplaintCategoryID=categoryID, ComplaintLevelID=complaint_level)
        user_hostel = user.Hostel
        #TODO: HANDLE NOTIFICATIONS
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
        complaint_id=[]
        complaint_title=[]
        complaint_body=[]
        complaint_time=[]
        complaint_upvotes=[]
        complaint_downvotes=[]
        complaint_level=[]
        complaint_status=[]
        complaint_category=[]
        for complaint in user_complaints:
            complaint_id.append(complaint.id)
            complaint_title.append(complaint.title)
            complaint_body.append(complaint.body)
            complaint_time.append(complaint.CreatedAt)
            complaint_upvotes.append(complaint.Upvotes)
            complaint_downvotes.append(complaint.Downvotes)
            complaint_level.append(complaint.ComplaintLevelID)
            complaint_status.append(complaint.ComplaintStatus)
            complaint_category.append(complaint.ComplaintCategoryID)
        return dict(compId=complaint_id, filedByUserId=auth.user.id, title=complaint_title, description=complaint_body, createdat=complaint_time, complaintstatus=complaint_status, complaintcategory=complaint_category, complaintlevel=complaint_level, upvotes=complaint_upvotes, downvotes=complaint_downvotes)

def list_all_personal_complaints():
    if auth.is_logged_in():
        personal_complaints = db(db.Complaint.ComplaintLevelID==0).select(orderby=~db.Complaint.CreatedAt)
        complaint_id=[]
        complaint_user=[]
        complaint_title=[]
        complaint_body=[]
        complaint_time=[]
        complaint_upvotes=[]
        complaint_downvotes=[]
        complaint_status=[]
        complaint_category=[]
        for complaint in personal_complaints:
            complaint_id.append(complaint.id)
            complaint_user.append(complaint.UserID)
            complaint_title.append(complaint.title)
            complaint_body.append(complaint.body)
            complaint_time.append(complaint.CreatedAt)
            complaint_upvotes.append(complaint.Upvotes)
            complaint_downvotes.append(complaint.Downvotes)
            complaint_status.append(complaint.ComplaintStatus)
            complaint_category.append(complaint.ComplaintCategoryID)
        return dict(compId=complaint_id, filedByUserId=complaint_user, title=complaint_title, description=complaint_body, createdat=complaint_time, complaintstatus=complaint_status, complaintcategory=complaint_category, complaintlevel=0, upvotes=complaint_upvotes, downvotes=complaint_downvotes)

def list_all_hostel_complaints():
    if auth.is_logged_in():
        hostel_complaints = db(db.Complaint.ComplaintLevelID==1).select(orderby=~db.Complaint.CreatedAt)
        complaint_id=[]
        complaint_user=[]
        complaint_title=[]
        complaint_body=[]
        complaint_time=[]
        complaint_upvotes=[]
        complaint_downvotes=[]
        complaint_status=[]
        complaint_category=[]
        user_hostel=[]
        for complaint in hostel_complaints:
            complaint_id.append(complaint.id)
            complaint_user.append(complaint.UserID)
            user_hostel.append((db.users(UserID)).Hostel)
            complaint_title.append(complaint.title)
            complaint_body.append(complaint.body)
            complaint_time.append(complaint.CreatedAt)
            complaint_upvotes.append(complaint.Upvotes)
            complaint_downvotes.append(complaint.Downvotes)
            complaint_status.append(complaint.ComplaintStatus)
            complaint_category.append(complaint.ComplaintCategoryID)
        return dict(compId=complaint_id, filedByUserId=complaint_user, title=complaint_title, description=complaint_body, createdat=complaint_time, complaintstatus=complaint_status, complaintcategory=complaint_category, complaintlevel=0, upvotes=complaint_upvotes, downvotes=complaint_downvotes, hostels=user_hostel)

def list_all_institute_complaints():
    if auth.is_logged_in():
        institute_complaints = db(db.Complaint.ComplaintLevelID==2).select(orderby=~db.Complaint.CreatedAt)
        complaint_id=[]
        complaint_user=[]
        complaint_title=[]
        complaint_body=[]
        complaint_time=[]
        complaint_upvotes=[]
        complaint_downvotes=[]
        complaint_status=[]
        complaint_category=[]
        for complaint in institute_complaints:
            complaint_id.append(complaint.id)
            complaint_user.append(complaint.UserID)
            complaint_title.append(complaint.title)
            complaint_body.append(complaint.body)
            complaint_time.append(complaint.CreatedAt)
            complaint_upvotes.append(complaint.Upvotes)
            complaint_downvotes.append(complaint.Downvotes)
            complaint_status.append(complaint.ComplaintStatus)
            complaint_category.append(complaint.ComplaintCategoryID)
        return dict(compId=complaint_id, filedByUserId=complaint_user, title=complaint_title, description=complaint_body, createdat=complaint_time, complaintstatus=complaint_status, complaintcategory=complaint_category, complaintlevel=0, upvotes=complaint_upvotes, downvotes=complaint_downvotes)

def get_comments():
    if auth.is_logged_in():
        complaintID = int(request.vars["complaintid"]).strip()
        comments = db(db.Comments.ComplaintID==complaintID).select(orderby=~db.Comments.WrittenAt)
        comment_body = []
        times_readable = []
        comment_users =[]
        for comment in comments:
            comment_body.append(comment.body)
            times_readable.append(comment.WrittenAt)
            comment_users.append(comment.UserID)
        return dict(description=comment_body, createduserid=comment_users, createdat=times_readable)

def add_comment():
    if auth.is_logged_in():
        comment_body = str(request.vars["description"]).strip()
        complaintID = int(request.vars["complaintid"]).strip()
        db.Comments.insert(UserID=auth.user.id, ComplaintID=complaintID, body=comment_body)
        return dict(success=True)

def upvote():
    if auth.is_logged_in():
        complaintID = int(request.vars["complaintId"]).strip()
        complaint = db.Complaint(complaintID)
        if complaint:
            complaint.update_record(Upvotes = complaint.Upvotes + 1)
            return dict(success=True)
        else:
            return dict(success=False)

def downvote():
    if auth.is_logged_in():
        complaintID = int(request.vars["complaintId"]).strip()
        complaint = db.Complaint(complaintID)
        if complaint:
            complaint.update_record(Downvotes = complaint.Downvotes + 1)
            return dict(success=True)
        else:
            return dict(success=False)

def addUser():
    if auth.is_logged_in:
        admin = db.users(auth.user.id)
        if admin.type_ == 0:
            firstName = str(request.vars["firstName"]).strip()
            lastName = str(request.vars["lastName"]).strip()
            kerberos = str(request.vars["kerberosid"]).strip()
            Email = str(request.vars["email"]).strip()
            entryno = str(request.vars["entrynumber"]).strip()
            passWord = str(request.vars["password"]).strip()
            category = int(request.vars["category"]).strip()
            hostel = str(request.vars["hostel"]).strip()
            db.users.insert(first_name=firstName, last_name=lastName, username=kerberos, email=Email, entry_no=entryno, password=passWord, type_=category, Hostel=hostel)
            return dict(success=True)
        else:
            return dict(success=False)

def getUser():
    user_id = int(request.vars["id"]).strip()
    user = db.users(user_id)
    return dict(firstName=user.first_name, lastName=user.last_name, kerberosID=user.username, password=user.password, category=user.type_, hostel=user.Hostel)

def getListOfCategories():
    list_of_categories = []
    for categories in db().select(db.Complaint_Category.ALL):
        list_of_categories.append(categories.Category_Name)
    return list_of_categories

#def getNotifications():
#    return dict()
