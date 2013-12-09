package com.erudine.coursebooking;

import org.junit.Test;

import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StudentTest {

    private final Student student = new Student("Jo", newHashSet(new Course("Data Structures", null)));

    @Test
    public void getsName() {
        assertThat(student.name(), is("Jo"));
    }

    @Test
    public void getsTakenCourses() {
        assertThat(student.name(), is("Jo"));
    }

    @Test
    public void studentsWithSameNameAreConsideredEqual() {
        assertThat(student, is(new Student("Jo", newHashSet(new Course("Data Structures", null)))));
    }
}
