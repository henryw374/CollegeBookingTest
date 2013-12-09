package com.erudine.coursebooking;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

public class CourseSchedule {

    private final int capacity;
    private final Course course;
    private final Set<Student> registeredStudents;

    private final List<Student> waitListedStudents = newArrayList();
    private final Lock lock = new ReentrantLock();

    public CourseSchedule(int capacity, Set<Student> registeredStudents, Course course) {
        this.capacity = capacity;
        this.registeredStudents = registeredStudents;
        this.course = course;
    }

    public boolean bookCourse(Student student) {
        lock.lock();
        boolean canBook = true;
        try {
            if (preRequisitesNotMet(student)) {
                canBook = false;
            }
            if (courseIsFull()) {
                addStudentToWaitingList(student);
                canBook = false;
            }
            registerStudent(student, canBook);
        } finally {
            lock.unlock();
        }
        return canBook;
    }

    public void cancelBooking(Student student) {
        lock.lock();
        try {
            if (registered(student)) {
                unregister(student);
                registerNextInWaitingList();
            } else {
                if (inWaitingList(student)) {
                    removeFromWaitingList(student);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public Set<Student> registeredStudents() {
        lock.lock();
        Set<Student> immutableSet;
        try {
            immutableSet = unmodifiableSet(newHashSet(registeredStudents));
        } finally {
            lock.unlock();
        }
        return immutableSet;
    }

    public List<Student> waitListedStudents() {
        lock.lock();
        List<Student> immutableList;
        try {
            immutableList = unmodifiableList(newArrayList(waitListedStudents));
        } finally {
            lock.unlock();
        }
        return immutableList;
    }

    private boolean removeFromWaitingList(Student student) {
        return waitListedStudents.remove(student);
    }

    private boolean inWaitingList(Student student) {
        return waitListedStudents.contains(student);
    }

    private void registerNextInWaitingList() {
        if (thereIsWaitingList()) {
            registerFirstFromWaitingList();
        }
    }

    private boolean registerFirstFromWaitingList() {
        return registeredStudents.add(waitListedStudents.get(waitListedStudents.size() - 1));
    }

    private boolean unregister(Student student) {
        return registeredStudents.remove(student);
    }

    private boolean thereIsWaitingList() {
        return !waitListedStudents.isEmpty();
    }

    private boolean registered(Student student) {
        return registeredStudents.contains(student);
    }

    private boolean courseIsFull() {
        return registeredStudents.size() >= capacity;
    }

    private void addStudentToWaitingList(Student studentWantingToJoinCourse) {
        waitListedStudents.add(studentWantingToJoinCourse);
    }

    private boolean preRequisitesNotMet(Student studentWantingToJoinCourse) {
        return !studentWantingToJoinCourse.takenCourses().containsAll(course.prerequisites());
    }

    private void registerStudent(Student studentWantingToJoinCourse, boolean canBook) {
        if (canBook) {
            registeredStudents.add(studentWantingToJoinCourse);
        }
    }
}