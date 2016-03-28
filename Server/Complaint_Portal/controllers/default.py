def index():
    database = db(db.Complaint_Level).select()
    return locals()

def input_Complaint_Level():
    requested_level = request.args(0)
    level = db.Complaint_Level(Level_Name==requested_level)
    if not level:
        session.flash='page not found'
        redirect(URL('index'))
    return level

def login():
    userid = request.vars.userid
    password = request.vars.password
    user = auth.login_bare(userid, password)
    return dict(success=False if not user else True, user=user)

def logged_in():
    return dict(success=auth.is_logged_in(), user=auth.user)

def logout():
    return dict(success=True, loggedout=auth.logout())

@auth.requires_login()
def make_complaint():
    level = input_Complaint_Level()
    db.Complaint.ComplaintLevelID.default = level.id
    form = SQLFORM(db.Complaint)
    if form.process().accepted:
        response.flash = "Your Complaint has been submitted"
        #TODO:Redirect
    return dict(level=level, form=form)

@auth.requires_login()
def edit_complaint():
    ComplaintID = request.args(0, cast=int)
    form = SQLFORM(db.Complaint, ComplaintID)
    if form.process().accepted:
        response.flash = "Your Complaint has been submitted"
        #TODO:Redirect
    return dict(ID=ComplaintID, form=form)

def complaints_by_level():
    level = input_Complaint_Level()
    complaints = db(db.Complaint.ComplaintLevelID == level.id).select(orderby=~CreatedAt)
    return dict(level=level, complaints=complaints)

def complaints_by_votes():
    return locals()

def complaints_made_by_user():
    if auth.user:
        userid = auth.user.id
        user = db(db.Users.id == userid).select()
        complaints = db(db.Complaint.UserID == userid).select(orderby=~WrittenAt)
        return dict(user=user, complaints=complaints)

def view_complaint():
    complaintID = request.args(0, cast=int)
    complaint = db.Complaint(ComplaintID)
    Comments = db(db.Comments.ComplaintID == complaintID).select(orderby=~db.Comments.WrittenAt)
    if not auth.user:
        form=A("Login to comment",_href=URL('user/login',vars=dict(_next=URL(args=request.args))))
    else:
        db.Comments.ComplaintID.default = complaintID
        form = SQLFORM(db.Comments).process()
    return locals()

def vote_complaint():
    return locals()

def change_complaint_status():
    return locals()

@request.restful()
def api():
    response.view = 'generic.' + request.extension
    def GET(*args, **vars):
        patterns = 'auto'
        parser = db.parse_as_rest(patterns, args, vars)
        if parser.status == 200 :
            return dict(content=parser.response)
        else :
            raise HTTP(parser.status, parser.error)
    def POST(table_name, **vars):
        return db[table_name].validate_and_insert(**vars)
    def PUT(table_name, record_id, **vars):
        return db(db[table_name]._id == record_id).update(**vars)
    def DELETE(table_name, record_id):
        return db(db[table_name]._id == record_id).delete()
    return dict(GET=GET, POST=POST, PUT=PUT, DELETE=DELETE)

def user():
    """
    exposes:
    http://..../[app]/default/user/login
    http://..../[app]/default/user/logout
    http://..../[app]/default/user/register
    http://..../[app]/default/user/profile
    http://..../[app]/default/user/retrieve_password
    http://..../[app]/default/user/change_password
    http://..../[app]/default/user/bulk_register
    use @auth.requires_login()
        @auth.requires_membership('group name')
        @auth.requires_permission('read','table name',record_id)
    to decorate functions that need access control
    also notice there is http://..../[app]/appadmin/manage/auth to allow administrator to manage users
    """
    return dict(form=auth())


@cache.action()
def download():
    """
    allows downloading of uploaded files
    http://..../[app]/default/download/[filename]
    """
    return response.download(request, db)


def call():
    """
    exposes services. for example:
    http://..../[app]/default/call/jsonrpc
    decorate with @services.jsonrpc the functions to expose
    supports xml, json, xmlrpc, jsonrpc, amfrpc, rss, csv
    """
    return service()
