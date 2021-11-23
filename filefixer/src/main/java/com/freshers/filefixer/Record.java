package com.freshers.filefixer;

public class Record {
    private String fullName;
    private String participantID;
    private String studentID;


    public Record(String participantID, String fullName, String studentID) {
        this.participantID = participantID;
        this.fullName = fullName;
        this.studentID = studentID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getParticipantID() {
        return participantID;
    }

    public String getStudentID() {
        return studentID;
    }

}
