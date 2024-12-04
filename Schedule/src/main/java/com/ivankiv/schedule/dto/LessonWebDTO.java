package com.ivankiv.schedule.dto;

public class LessonWebDTO{
        public int number;
        public String subject;
        public String teacher_faculty;
        public String teacher_name;
        public String teacher_surname;
        public int teacher_id;
        public int corps;
        public int cabinet;
        public String type;

        public LessonWebDTO(int number, String subject, String teacher_faculty,
                            String teacher_name, String teacher_surname, int teacher_id,
                            int corps, int cabinet, String type){

                this.number = number;
                this.subject = subject;
                this.teacher_faculty = teacher_faculty;
                this.teacher_surname = teacher_surname;
                this.teacher_name = teacher_name;
                this.teacher_id = teacher_id;
                this.corps = corps;
                this.cabinet = cabinet;
                this.type = type;

        }

        public int number() {
                 return this.number;
        }

        public String subject(){
                return this.subject;
        }

        public String teacher_faculty(){
                return this.teacher_faculty;
        }

        public String teacher_name(){
                return this.teacher_name;
        }

        public String teacher_surname(){
                return this.teacher_surname;
        }

        public int cabinet(){
                return this.cabinet;
        }

        public String type(){
                return this.type;
        }

        public int corps(){
                return this.corps;
        }

        public void setSubject(String subject) {
                this.subject = subject;
        }

        public void setTeacher_faculty(String teacher_faculty) {
                this.teacher_faculty = teacher_faculty;
        }

        public void setTeacher_name(String teacher_name) {
                this.teacher_name = teacher_name;
        }

        public void setTeacher_surname(String teacher_surname) {
                this.teacher_surname = teacher_surname;
        }

        public void setNumber(int number) {
                this.number = number;
        }

        public void setCorps(int corps) {
                this.corps = corps;
        }

        public void setCabinet(int cabinet) {
                this.cabinet = cabinet;
        }

        public void setType(String type) {
                this.type = type;
        }

        public LessonWebDTO(){};

}
