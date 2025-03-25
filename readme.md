# University schedule project

This project is web-site of University schedule implemented on Spring Java framework. He enables check schedule of lessons for students and teachers online and edit schedule for admin.

## FUNCTIONAL REQUIREMENTS

This project includes the following functional requirements

### Users Managment
# University schedule project

This project is web-site of University schedule implemented on Spring Java framework. He enables check schedule of lessons for students and teachers online and edit schedule for admin.

## FUNCTIONAL REQUIREMENTS

This project includes the following functional requirements

### Users Managment
- User can be sign up on the site using his university email, password, full name
- User must specify your faculty, couse, specialty and group if you are student and faculty if are teacher
- User must log in to use schedule using email and password
- User can restore password using email if it's lost
- User can review your profile information
- User can log out

### Using schedule
- User can log out

### Using schedule
- User have delault schedule but he can see schedule for other group or teacher
- User can select date(by default is today date) he wants get schedule
- User can go to next or previous day schedule
- User can go to next or previous week schedule

### administration
- User can go to next or previous week schedule

### administration
- Admin can log in on the site using email
- Admin can restore password
- Admin can log out
- Main admin can add and remove admin account
- Admin can add page of schedule or edit existing
- Admin can add and remove new groups, specialties, faculties, lessons and teachers
- Admin can change a time schedule(times of start lessons)
- Admin can duplicate a schedule for the next weeks

## SYSTEM BEHAVIOUR AND REST API

### Get schedule
- Admin can duplicate a schedule for the next weeks

## SYSTEM BEHAVIOUR AND REST API

### Get schedule
For student:
- GET /group?faculty={faculty_short_name}&specialty={specialty_name}&course={number_of_course}&group={number_of_group}:
  Select the student group by faculty, specialty, course and number_of_group and get group id
- GET /group?faculty={faculty_short_name}&specialty={specialty_name}&course={number_of_course}&group={number_of_group}:
  Select the student group by faculty, specialty, course and number_of_group and get group id
- GET /schedule/student/week: Get schedule catalog on this week for delault user group(if he's authorized)
- GET /schedule/student/week?group={group_id}: Get schedule catalog on this week for specified group
- GET /schedule/student/week?date={date} - Get schedule catalog for week which inlcludes selected date
- GET /schedule/student/week/next(?group_id={group_id})(&n={number_of_week}) - Get schedule catalog on the next week(or on the n-th week after this, optional)
- GET /schedule/student/week/last(?group_id={group_id})(&n={number_of_week}) - Get schedule catalog on the last week(or on the n-th week before this, optional)
- GET /schedule/student/day(?group={group_id}&date={date}): Get page of schedule for the selected group_id on the selected date(by defalult is today date and default user group)

For teacher:
- GET /schedule/student/day(?group={group_id}&date={date}): Get page of schedule for the selected group_id on the selected date(by defalult is today date and default user group)

For teacher:
- GET /teacher?name={name}&surname={surname}: Find teacher by name and surname and get teacher_id
- GET /schedule/teacher - Get schedule of teacher on today(Only if user is teacher)
- GET /schedule/teacher?id={teacher_id} - Get teacher schedule by teacher id
- GET /schedule/teacher/week: Get schedule catalog on this week(Only if user is teacher)
- GET /schedule/teacher/week?id={teacher_id}: Get schedule catalog on this week for specified teacher
- GET /schedule/teacher/week?date={date} - Get schedule catalog for week which includes selected date
- GET /schedule/teacher/week/next(?id={teacher_id})(&n={number_of_week}) - Get schedule catalog on the next week(or on the n-th week after this, optional)
- GET /schedule/teacher/week/last(?id={teacher_id})(&n={number_of_week}) - Get schedule catalog on the last week(or on the n-th week before this, optional)
- GET /schedule/teacher/day(?id={teacher_id}&date={date}): Get page of schedule for the selected teacher_id on the selected date(by defalult is today date)

Account:
- GET /schedule/teacher/day(?id={teacher_id}&date={date}): Get page of schedule for the selected teacher_id on the selected date(by defalult is today date)

Account:
- POST /sign_up - Registration new user(student or teacher)
- POST /log_in - Authorization in the system as simple user
- GET /profile - View your user profile
- GET /log_out - Exit from system
- POST /restore - Restore password using your email
- GET /restore/confirmation?code={secret_code} - Confirm the user via secret code which had come to the mail
- POST /restore/new_password - Set new password after successed confirmation

Administration:
- POST /restore/new_password - Set new password after successed confirmation

Administration:
- POST /admin/log_in - Authorization in the size as admin
- GET /admin/log_out - Exit from system
- POST /admin/restore - Restore password using your email
- GET /admin/restore/confirmation?code={secret_code} - Confirm the user via secret code which had come to the mail
- POST /admin/restore/new_password - Set new password after successed confirmation
- POST /admin/schedule - Add a new schedule page
- POST /admin/groups - Add a new student group
- POST /admin/teacher - Add a new teacher
- DELETE /admin/schedule?group_id={}&date={} - Delete the page of schedule
- DELETE /admin/schedule/teacher?teacher_id={}&date={} - Delete the page of teacher schedule
- DELETE /admin/teacher?id={} - Delete the teacher
- DELETE /admin/groups?id={} - Delete of the student group
- PUTCH /admin/schedule - Edit a existing schedule page
- PUTCH /admin/groups - Edit a existing student group


## ER-diagram
![ER-diagram](https://github.com/taras123programmer/springProject/ERD.png)