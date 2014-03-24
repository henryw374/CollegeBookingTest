package com.erudine.coursebooking;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Course {

    /** The course id. */
    private int courseId;

    /** The name. */
    private String name;

    /** The pre requisites. */
    private Set<Course> preRequisites;

    /** The course capacity. */
    private int courseCapacity;

    /** The schedule. */
    private CourseSchedule schedule;

    /**
     * Instantiates a new course.
     */
    Course() {
        schedule = new CourseSchedule(this);
        preRequisites = new HashSet<Course>();
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the pre requisites.
     * 
     * @return the preRequisites
     */
    public Set<Course> getPreRequisites() {
        return preRequisites;
    }

    /**
     * Sets the pre requisites.
     * 
     * @param preRequisites
     *            the preRequisites to set
     */
    public void setPreRequisites(Set<Course> preRequisites) {
        this.preRequisites = preRequisites;
    }

    /**
     * Gets the course capacity.
     * 
     * @return the courseCapacity
     */
    public int getCourseCapacity() {
        return courseCapacity;
    }

    /**
     * Sets the course capacity.
     * 
     * @param courseCapacity
     *            the courseCapacity to set
     */
    public void setCourseCapacity(int courseCapacity) {
        this.courseCapacity = courseCapacity;
    }

    /**
     * Hash code.
     * 
     * @return the int
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        // No need to use fields - courseCapacity, preRequisites,schedule to validate uniqueness
        return HashCodeBuilder.reflectionHashCode(this, "courseCapacity",
            "preRequisites", "schedule");
    }

    /**
     * Equals.
     * 
     * @param obj
     *            the obj
     * @return true, if successful
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        // No need to use fields - courseCapacity, preRequisites,schedule to validate uniqueness
        return EqualsBuilder.reflectionEquals(this, obj, "courseCapacity",
            "preRequisites", "schedule");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * Gets the course id.
     * 
     * @return the courseId
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Sets the course id.
     * 
     * @param courseId
     *            the courseId to set
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**
     * Gets the schedule.
     * 
     * @return the schedule
     */
    public CourseSchedule getSchedule() {
        return schedule;
    }

    /**
     * Sets the schedule.
     * 
     * @param schedule
     *            the schedule to set
     */
    public void setSchedule(CourseSchedule schedule) {
        this.schedule = schedule;
    }

}
