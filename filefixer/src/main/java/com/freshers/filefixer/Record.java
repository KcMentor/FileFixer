package com.freshers.filefixer;

/**
 * This class is used to create objects which represent each row in the CSV file
 */
public class Record {
    /**
     * The first and last name of a student
     */
    private String fullName;

    /**
     * The participantID of a student
     */
    private String participantID;

    /**
     * A unique studentID that represents them
     */
    private String studentID;

    /**
     * Record constructor
     * @param participantID
     * @param fullName
     * @param studentID
     */
    public Record(String participantID, String fullName, String studentID) {
        this.participantID = participantID;
        this.fullName = fullName;
        this.studentID = studentID;
    }

    /**
     * Gets the first and last name of a student
     * @return The student's name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Gets the participantID of the student
     * @return The ParticipantID of the student
     */
    public String getParticipantID() {
        return participantID;
    }

    /**
     * Gets the studentID of this student
     * @return this student's ID
     */
    public String getStudentID() {
        return studentID;
    }

}
