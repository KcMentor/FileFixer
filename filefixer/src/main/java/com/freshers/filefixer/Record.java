package com.freshers.filefixer;

public class Record {
    private String identifier;
    private String fullName;
    private String participantID;
    private String studentID;
    private String emailAddress;
    private int maxGrade;
    private boolean gradeStatus;
    private String lastModified;
    private String comments;

    public Record(String identifier, String fullName, String participantID, String emailAddress) {
        this.identifier = identifier;
        this.fullName = fullName;
        this.participantID = participantID;
        this.emailAddress = emailAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public String getComments() {
        return comments;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getLastModified() {
        return lastModified;
    }

    public int getMaxGrade() {
        return maxGrade;
    }

    public String getParticipantID() {
        return participantID;
    }

    public String getStudentID() {
        return studentID;
    }

    public boolean isGradeStatus() {
        return gradeStatus;
    }

}
