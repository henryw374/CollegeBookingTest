package com.erudine.coursebooking;

import java.util.HashSet;
import java.util.Set;

public class Student {

    private final String name;
    private Set<Course> coursesTaken;

    public Student(String name, HashSet<Course> coursesTaken) {
        this.name = name;
        this.coursesTaken = coursesTaken;
    }

    public String name() {
        return name;
    }

    public Set<Course> takenCourses() {
        return coursesTaken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (name != null ? !name.equals(student.name) : student.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
