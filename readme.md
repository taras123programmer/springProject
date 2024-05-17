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
- User have delault schedule but he can see schedule for other group or teacher
- User can select date(by default is today date) he wants get schedule
- User can go to next or previous day schedule
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

## SYSTEM BEHAVIOUR

- User interact with the system througth web interface

## REST API

### Get schedule
For student:

- GET /schedule/student/week?faculty={faculty_short_name}&?specialty={specialty_name}?corse={number_of_course}?group={number_of_group}:
  Get catalog of schedule for this week(for a student)
- GET /schedule/week: Get schedule catalog on this week for delault user group(if he's authorized)
- GET /schedule/week?next={number_of_weeks} - Get schedule catalog on n-th week after this
- GET /schedule/week?last={number_of_weeks} - Get schedule catalog on n-th week before this
- GET /schedule/week?date={date} - Get schedule catalog which inlcludes selected date
- GET /schedule/day?group={group_id}&date={date}: Get page of schedule for the selected group_id on the selected date
- GET /schedule/day?group={group_id}: Get page of schedule for the selected group_id on this date

For teacher:
- GET /schedule









