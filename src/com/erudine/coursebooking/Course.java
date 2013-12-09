package com.erudine.coursebooking;

import java.util.Set;

public class Course {

    private final String name;
    private final Set<Course> prerequisites;

    public Course(String name, Set<Course> prerequisites) {
        this.name = name;
        this.prerequisites = prerequisites;
    }

    public String name() {
        return name;
    }

    public Set<Course> prerequisites() {
        return prerequisites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        if (prerequisites != null ? !prerequisites.equals(course.prerequisites) : course.prerequisites != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (prerequisites != null ? prerequisites.hashCode() : 0);
        return result;
    }
}
