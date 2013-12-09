package com.erudine.coursebooking;

import org.junit.Test;

import java.util.HashSet;

import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CourseTest {

    private final Course course = new Course("Java Programming", newHashSet(new Course("Data Structures", null)));

    @Test
    public void getsName() {
        assertThat(course.name(), is("Java Programming"));
    }

    @Test
    public void getsPreRequisites() {
        assertThat((HashSet<Course>) course.prerequisites(), is(newHashSet(new Course("Data Structures", null))));
    }

    @Test
    public void coursesWithSameNameAndPrerequisitesAreConsideredEqual() {
        assertThat(course, is(new Course("Java Programming", newHashSet(new Course("Data Structures", null)))));
    }
}
