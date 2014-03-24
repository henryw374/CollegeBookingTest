package com.erudine.coursebooking;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Student {

    /** The student id. */
    private int studentId;

    /** The student name. */
    private String studentName;

    /** The courses passed. */
    private Set<Course> coursesPassed;

    /** The courses registered. */
    private Set<Course> coursesRegistered;

    /** The courses wait listed. */
    private Set<Course> coursesWaitListed;

    /**
     * Instantiates a new student.
     */
    Student() {
        coursesPassed = new HashSet<Course>();
        coursesRegistered = new HashSet<Course>();
        coursesWaitListed = new HashSet<Course>();
    }

    /**
     * Instantiates a new student.
     * 
     * @param name
     *            the name
     */
    Student(String name) {
        this();
        this.studentName = name;
    }

    /**
     * Gets the student name.
     * 
     * @return the studentName
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Sets the student name.
     * 
     * @param studentName
     *            the studentName to set
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * Hash code.
     * 
     * @return the int
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        // No need to use fields - coursesPassed, coursesRegistered, coursesWaitListed to validate uniqueness
        return HashCodeBuilder.reflectionHashCode(this, "coursesPassed",
            "coursesRegistered", "coursesWaitListed");
    }

    /**
     * Equals - Two student objects are equal if their id and name match.
     * 
     * @param obj
     *            the obj
     * @return true, if successful
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        // No need to use fields - coursesPassed, coursesRegistered, coursesWaitListed to validate uniqueness
        return EqualsBuilder.reflectionEquals(this, obj, "coursesPassed",
            "coursesRegistered", "coursesWaitListed");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getStudentName();
    }

    /**
     * Gets the student id.
     * 
     * @return the studentId
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Sets the student id.
     * 
     * @param studentId
     *            the studentId to set
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * Gets the courses passed.
     * 
     * @return the coursesPassed
     */
    public Set<Course> getCoursesPassed() {
        return coursesPassed;
    }

    /**
     * Sets the courses passed.
     * 
     * @param coursesPassed
     *            the coursesPassed to set
     */
    public void setCoursesPassed(Set<Course> coursesPassed) {
        this.coursesPassed = coursesPassed;
    }

    /**
     * Gets the courses registered.
     * 
     * @return the coursesRegistered
     */
    public Set<Course> getCoursesRegistered() {
        return coursesRegistered;
    }

    /**
     * Sets the courses registered.
     * 
     * @param coursesRegistered
     *            the coursesRegistered to set
     */
    public void setCoursesRegistered(Set<Course> coursesRegistered) {
        this.coursesRegistered = coursesRegistered;
    }

    /**
     * Gets the courses wait listed.
     * 
     * @return the coursesWaitListed
     */
    public Set<Course> getCoursesWaitListed() {
        return coursesWaitListed;
    }

    /**
     * Sets the courses wait listed.
     * 
     * @param coursesWaitListed
     *            the coursesWaitListed to set
     */
    public void setCoursesWaitListed(Set<Course> coursesWaitListed) {
        this.coursesWaitListed = coursesWaitListed;
    }
}
