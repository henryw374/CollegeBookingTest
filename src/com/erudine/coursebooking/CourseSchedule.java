/*
 * 
 */
package com.erudine.coursebooking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CourseSchedule {

    /** The course. */
    private Course course;

    /** The registered students. */
    private Set<Student> registeredStudents;

    /** The wait listed students queue. */
    private final Set<Student> waitListedStudentsQueue;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory
        .getLogger(CourseSchedule.class);;

    // the following commented out fields are just there to give more of an idea
    // about what a course schedule is in the real world. For this
    // exercise, there is no need uncomment them and include them
    // in your answer
    // private Date startDate;
    // private Date endDate;
    // private String teacher;

    /**
     * Instantiates a new course schedule. Every course schedule must have an associated course
     * 
     * @param course
     *            the course
     */
    CourseSchedule(Course course) {
        this.course = course;
        registeredStudents =
            Collections.synchronizedSet(new HashSet<Student>());
        waitListedStudentsQueue =
            Collections.synchronizedSet(new LinkedHashSet<Student>());
    }

    /**
     * Book course.
     * 
     * @param studentWantingToJoinCourse
     *            the student wanting to join course
     * @return true if the student successfully registered.
     */
    public boolean bookCourse(Student studentWantingToJoinCourse) {
        if (studentWantingToJoinCourse == null) {
            return false;
        }

        if (studentWantingToJoinCourse.getCoursesPassed().contains(getCourse())) {
            LOGGER
                .info(
                    "Booking cannot be completed. The student {} has already passed this course {}",
                    studentWantingToJoinCourse.getStudentName(), getCourse()
                        .getName());
            return false;
        }

        if (getRegisteredStudents().contains(studentWantingToJoinCourse)) {
            LOGGER
                .info(
                    "Booking cannot be completed. The student {} is already registered for this course {}",
                    studentWantingToJoinCourse.getStudentName(), getCourse()
                        .getName());
            return false;
        }

        if (getWaitListedStudentsQueue().contains(studentWantingToJoinCourse)) {
            LOGGER
                .info(
                    "Booking cannot be completed. The student {} is already in the waiting list for this course {}",
                    studentWantingToJoinCourse.getStudentName(), getCourse()
                        .getName());
            return false;
        }

        if (getRegisteredStudents().size() >= getCourse().getCourseCapacity()) {
            LOGGER
                .info(
                    "Booking cannot be completed. The course {} is filled to its allowed capacity.",
                    getCourse().getName());
            // Make sure that any changes to waitListedStudentsQueue is done in a synchronized block with a lock on
            // waitListedStudentsQueue
            synchronized (waitListedStudentsQueue) {
                getWaitListedStudentsQueue().add(studentWantingToJoinCourse);
            }
            studentWantingToJoinCourse.getCoursesWaitListed().add(getCourse());
            LOGGER
                .info(
                    "The student {} has been added to the waiting list for the course {}. The position in the waiting list is {}",
                    studentWantingToJoinCourse.getStudentName(), getCourse()
                        .getName(), getWaitListedStudentsQueue().size());
            return false;
        }

        if (!validatePreRequisites(studentWantingToJoinCourse)) {
            return false;
        }

        try {
            // Make sure that any changes to registeredStudents is done in a synchronized block with a lock on
            // registeredStudents
            synchronized (registeredStudents) {
                registeredStudents.add(studentWantingToJoinCourse);
            }
            studentWantingToJoinCourse.getCoursesRegistered().add(getCourse());
            LOGGER.info("The student {} has been registered for course {}",
                studentWantingToJoinCourse.getStudentName(), getCourse()
                    .getName());
            return true;
        } catch (Exception e) {
            LOGGER.error(
                "Exception while registering student {} for the course {}.",
                studentWantingToJoinCourse.getStudentName(), getCourse()
                    .getName());
            return false;
        }
    }

    /**
     * Validate pre requisites.
     * 
     * @param studentWantingToJoinCourse
     *            the student wanting to join course
     * @return true, if successful
     */
    private boolean validatePreRequisites(Student studentWantingToJoinCourse) {
        List<String> notCompletedPreRequisiteCoursesList =
            new ArrayList<String>();
        if (studentWantingToJoinCourse != null) {
            for (Course preRequisiteCourse : getCourse().getPreRequisites()) {
                // Lookout for the not sign !
                if (!studentWantingToJoinCourse.getCoursesPassed().contains(
                    preRequisiteCourse)) {
                    notCompletedPreRequisiteCoursesList.add(preRequisiteCourse
                        .getName());
                }
            }
        }
        if (notCompletedPreRequisiteCoursesList.size() == 0) {
            LOGGER.info("PreRequisite course validation successful");
            return true;
        }
        LOGGER
            .info("The student {} has not passed the following  required pre requisite courses");

        StringBuilder builder = new StringBuilder();

        for (String name : notCompletedPreRequisiteCoursesList) {
            builder.append(name);
            builder.append(",");
        }

        LOGGER.info(builder.toString());

        return false;
    }

    /**
     * Cancel booking.
     * 
     * @param studentWantingToCancel
     *            the student wanting to cancel
     */
    public void cancelBooking(Student studentWantingToCancel) {
        if (studentWantingToCancel == null) {
            LOGGER.debug("There is no student object passed!");
            return;
        }

        if (studentWantingToCancel.getCoursesPassed().contains(getCourse())) {
            LOGGER.info(
                "Error: The student {} has already passed this course {}",
                studentWantingToCancel.getStudentName(), getCourse().getName());
            return;
        }

        if (getWaitListedStudentsQueue().contains(studentWantingToCancel)) {
            synchronized (waitListedStudentsQueue) {
                getWaitListedStudentsQueue().remove(studentWantingToCancel);
            }
            studentWantingToCancel.getCoursesWaitListed().remove(getCourse());
            LOGGER
                .info(
                    "Booking has been cancelled. The student {} has been removed from the waiting list for this course {}",
                    studentWantingToCancel.getStudentName(), getCourse()
                        .getName());
            return;
        }

        if (getRegisteredStudents().contains(studentWantingToCancel)) {
            synchronized (registeredStudents) {
                getRegisteredStudents().remove(studentWantingToCancel);
            }
            studentWantingToCancel.getCoursesRegistered().remove(getCourse());
            LOGGER
                .info(
                    "Booking has been cancelled. The student {} has been removed from this course {}",
                    studentWantingToCancel.getStudentName(), getCourse()
                        .getName());
            registerStudentFromWaitingList();
            return;
        }

    }

    /**
     * Register student from waiting list.
     */
    private void registerStudentFromWaitingList() {
        boolean registrationSuccess = false;
        if (getWaitListedStudentsQueue().size() > 0) {
            Student studentToBeRegistered = null;
            // Make sure that any changes to waitListedStudentsQueue is done in a synchronized block with a lock on
            // waitListedStudentsQueue
            synchronized (waitListedStudentsQueue) {
                studentToBeRegistered =
                    getWaitListedStudentsQueue().iterator().next();
            }
            // Make sure that any changes to registeredStudents is done in a synchronized block with a lock on
            // registeredStudents
            synchronized (registeredStudents) {
                if (getRegisteredStudents().size() < getCourse()
                    .getCourseCapacity()) {
                    getRegisteredStudents().add(studentToBeRegistered);
                    studentToBeRegistered.getCoursesRegistered().add(
                        getCourse());
                    registrationSuccess = true;
                }
            }
            // Remove the student from waiting list only after a successful registration
            if (registrationSuccess) {
                LOGGER.info("The student {} has been registered for course {}",
                    studentToBeRegistered.getStudentName(), getCourse()
                        .getName());
                synchronized (waitListedStudentsQueue) {
                    getWaitListedStudentsQueue().remove(studentToBeRegistered);
                }
                studentToBeRegistered.getCoursesWaitListed()
                    .remove(getCourse());
                LOGGER
                    .info(
                        "The student {} has been removed from the waiting list for course {}",
                        studentToBeRegistered.getStudentName(), getCourse()
                            .getName());
            }
        }
    }

    /**
     * Gets the course.
     * 
     * @return the course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the course.
     * 
     * @param course
     *            the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Gets the registered students.
     * 
     * @return the registeredStudents
     */
    public Set<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    /**
     * Sets the registered students.
     * 
     * @param registeredStudents
     *            the registeredStudents to set
     */
    public void setRegisteredStudents(Set<Student> registeredStudents) {
        this.registeredStudents = registeredStudents;
    }

    /**
     * Gets the wait listed students queue.
     * 
     * @return the waitListedStudentsQueue
     */
    public Set<Student> getWaitListedStudentsQueue() {
        return waitListedStudentsQueue;
    }

}
