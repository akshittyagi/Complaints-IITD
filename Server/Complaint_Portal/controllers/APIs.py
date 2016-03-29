def password_reset():
    if auth.is_logged_in():
        user = db.users(auth.user.id)
        old_pass = str(request.vars["oldPass"])
        new_pass = str(request.vars["newPass"])
        user_pass = user.password
        if user_pass==old_pass :
            user.update_record(password = new_pass)
            return dict(success=True)
        else:
            return dict(success=False, user_pass=user_pass, old_pass=old_pass, new_pass=new_pass)

def make_complaint():
    if auth.is_logged_in():
        user = db.users(auth.user.id)
        complaint_level = int(request.vars["complaintlevel"])
        complaint_title = str(request.vars["title"]).strip()
        complaint_body = str(request.vars["description"]).strip()
        categoryID = int(request.vars["complaintcategory"])
        db.Complaint.insert(UserID=user.id, title=complaint_title, body=complaint_body, ComplaintCategoryID=categoryID, ComplaintLevelID=complaint_level)
        user_hostel = user.Hostel
        #TODO: HANDLE NOTIFICATIONS
        return dict(success=True)

def specific_complaint():
    if auth.is_logged_in():
        complaintID = int(request.vars["compId"])
        complaint = db.Complaint(complaintID)
        return dict(filedByUserId=complaint.UserID, title=complaint.title, description=complaint.body, createdat=complaint.CreatedAt, complaintstatus=complaint.ComplaintStatus, complaintcategory=complaint.ComplaintCategoryID, complaintlevel=complaint.ComplaintLevelID, upvotes=complaint.Upvotes, downvotes=complaint.Downvotes)

def list_all_user_complaints():
    if auth.is_logged_in():
        user = db.users(auth.user.id)
        user_complaints = db(db.Complaint.UserID==auth.user.id).select(orderby=~db.Complaint.CreatedAt)
        return dict(user_complaints = user_complaints)

def list_all_personal_complaints():
    if auth.is_logged_in():
        personal_complaints = db(db.Complaint.ComplaintLevelID==1).select(orderby=~db.Complaint.CreatedAt)
        return dict(personal_complaints = personal_complaints)

def list_all_hostel_complaints():
    if auth.is_logged_in():
        hostel_complaints = db(db.Complaint.ComplaintLevelID==2).select(orderby=~db.Complaint.CreatedAt)
        user_hostel=[]
        for complaint in hostel_complaints:
            user_hostel.append((db.users(complaint.UserID)).Hostel)
        return dict(hostel_complaints=hostel_complaints, user_hostel=user_hostel)

def list_all_institute_complaints():
    if auth.is_logged_in():
        institute_complaints = db(db.Complaint.ComplaintLevelID==3).select(orderby=~db.Complaint.CreatedAt)
        return dict(institute_complaints=institute_complaints)

def get_comments():
    if auth.is_logged_in():
        complaintID = int(request.vars["complaintid"])
        comments = db(db.Comments.ComplaintID==complaintID).select(orderby=~db.Comments.WrittenAt)
        return dict(comments=comments)

def add_comment():
    if auth.is_logged_in():
        comment_body = str(request.vars["description"]).strip()
        complaintID = int(request.vars["complaintid"])
        db.Comments.insert(UserID=auth.user.id, ComplaintID=complaintID, body=comment_body)
        return dict(success=True)

def upvote():
    if auth.is_logged_in():
        complaintID = int(request.vars["complaintId"])
        complaint = db.Complaint(complaintID)
        if complaint:
            complaint.update_record(Upvotes = complaint.Upvotes + 1)
            return dict(success=True)
        else:
            return dict(success=False)

def downvote():
    if auth.is_logged_in():
        complaintID = int(request.vars["complaintId"])
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
            category = int(request.vars["category"])
            hostel = str(request.vars["hostel"]).strip()
            db.users.insert(first_name=firstName, last_name=lastName, username=kerberos, email=Email, entry_no=entryno, password=passWord, type_=category, Hostel=hostel)
            return dict(success=True)
        else:
            return dict(success=False)

def getUser():
    user_id = int(request.vars["id"])
    user = db.users(user_id)
    return dict(firstName=user.first_name, lastName=user.last_name, kerberosID=user.username, password=user.password, category=user.type_, hostel=user.Hostel)

def getListOfCategories():
    list_of_categories = []
    for categories in db().select(db.Complaint_Category.ALL):
        list_of_categories.append(categories.Category_Name)
    return list_of_categories

def set_resolved():
    if auth.is_logged_in:
        admin = db.users(auth.user.id)
        if admin.type_ == 0:
            complaint_id = int(request.vars["complaintid"])
            complaint = db.Complaint(complaint_id)
            if complaint:
                complaint.update_record(ComplaintStatus = True)
                return dict(success=True)
            else:
                return dict(success=False)
        else:
            return dict(success=False)
