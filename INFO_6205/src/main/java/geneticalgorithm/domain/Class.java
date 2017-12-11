/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.domain;

/**
 *
 * @author Niranjanii
 */
public class Class {

    private int id;
    private Department dept;
    private Course course;
    private Professor professor;
    private MeetingTime meetingTime;
    private Room room;

    public Class(int id, Department dept, Course course) {
        this.id = id;
        this.dept = dept;
        this.course = course;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setMeetingTime(MeetingTime meetingTime) {
        this.meetingTime = meetingTime;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public Department getDept() {
        return dept;
    }

    public Course getCourse() {
        return course;
    }

    public Professor getProfessor() {
        return professor;
    }

    public MeetingTime getMeetingTime() {
        return meetingTime;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "[" + dept.getName() + "," + course.getNumber() + "," + professor.getId() + "," + meetingTime.getId() + "," + room.getNumber() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return ((this.id == ((Class) obj).id) && (this.dept.equals(((Class) obj).getDept())) && (this.course .equals(((Class) obj).getCourse())));
    }
}
