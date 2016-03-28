# -*- coding: utf-8 -*-
from datetime import datetime

db.define_table('User_Category',
   Field('CategoryID', db.users),
   Field('CategoryName'),
   Field('ComplaintCategoryList'))

db.define_table('Complaint_Level',
   Field('Level_Name'))

db.define_table('Complaint_Category',
   Field('Category_Name'))

db.define_table('Complaint',
   Field('UserID', db.users),
   Field('title'),
   Field('body', 'text'),
   Field('CreatedAt', 'datetime', default = datetime.now),
   Field('Upvotes', 'integer', default = 0),
   Field('Downvotes', 'integer', default = 0),
   Field('ComplaintCategoryID', 'reference Complaint_Category'),
   Field('ComplaintStatus', 'boolean', default=False),
   Field('ComplaintLevelID', 'reference Complaint_Level'))

db.define_table('Comments',
   Field('UserID', db.users),
   Field('ComplaintID', 'reference Complaint'),
   Field('body', 'text'),
   Field('WrittenAt', 'datetime', default = datetime.now))

def author(id):
    if id is None:
        return "Unknown"
    else:
        user=db.auth_user(id)
        return A('%(first_name)s %(last_name)s' %user,_href=URL('complaints_by_user',args=user.id))
