package com.erudine.coursebooking;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

public class CourseScheduleTest {

    private final Course course = new Course("Java Programming", newHashSet(new Course("Data Structures", null)));
    private final Student studentMeetingPrerequisites = studentMeetingCoursePreRequisites();

    private final Student student = new Student("Mark", new HashSet<Course>());

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void registersStudent() {
        CourseSchedule schedule = emptySchedule();

        assertThat(schedule.bookCourse(studentMeetingPrerequisites), is(true));
    }

    @Test
    public void registeringStudent_AddThemToTheRegisteredStudentsList() {
        CourseSchedule schedule = emptySchedule();

        schedule.bookCourse(studentMeetingPrerequisites);

        assertThat(schedule.registeredStudents(), hasItem(studentMeetingPrerequisites));
    }

    @Test
    public void failureWhenRegisteringStudent_ResultsInNotAddingThemToTheStudentsList() {
        CourseSchedule schedule = fullyBookedSchedule();

        schedule.bookCourse(studentMeetingPrerequisites);

        assertThat(schedule.registeredStudents(), not(hasItem(studentMeetingPrerequisites)));
    }

    @Test
    public void doesNotRegisterStudentIfCourseIfFull() {
        CourseSchedule schedule = fullyBookedSchedule();

        assertThat(schedule.bookCourse(studentMeetingPrerequisites), is(false));
    }

    @Test
    public void bookingStudentSucceeds_IfStudentIsAlreadyRegistered() {
        CourseSchedule schedule = new CourseSchedule(2, newHashSet(studentMeetingPrerequisites), course);

        assertThat(schedule.bookCourse(studentMeetingPrerequisites), is(true));
        assertThat(schedule.registeredStudents().size(), is(1));
    }

    @Test
    public void doesNotRegisterStudentIfCoursePrerequisitesNotMet() {
        CourseSchedule schedule = emptySchedule();

        assertThat(schedule.bookCourse(student), is(false));
    }

    @Test
    public void studentIsWaitListed_IfCourseFull() {
        CourseSchedule schedule = fullyBookedSchedule();

        schedule.bookCourse(studentMeetingPrerequisites);

        assertThat(schedule.waitListedStudents(), hasItem(studentMeetingPrerequisites));
    }

    @Test
    public void studentIsNotWaitListed_IfCourseNotFull() {
        CourseSchedule schedule = emptySchedule();

        schedule.bookCourse(studentMeetingPrerequisites);

        assertThat(schedule.waitListedStudents(), is(Collections.<Student>emptyList()));
    }

    @Test
    public void cancellingStudentRemovesThemFromTheRegisteredList() {
        CourseSchedule schedule = fullyBookedSchedule();

        assertThat(schedule.registeredStudents(), hasItem(student));
        schedule.cancelBooking(student);

        assertThat(schedule.registeredStudents(), not(hasItem(student)));
    }

    @Test
    public void cancellingStudentRemovesThemFromTheWaitingList() {
        CourseSchedule schedule = fullyBookedSchedule();

        schedule.bookCourse(studentMeetingPrerequisites);
        schedule.cancelBooking(studentMeetingPrerequisites);

        assertThat(schedule.waitListedStudents(), is(Collections.<Student>emptyList()));
    }

    @Test
    public void cancellingRegisteredStudentRegistersNexInWaitingList() {
        CourseSchedule schedule = fullyBookedSchedule();

        schedule.bookCourse(studentMeetingPrerequisites);
        assertThat(schedule.waitListedStudents(), is(asList(studentMeetingPrerequisites)));

        schedule.cancelBooking(student);

        assertThat(schedule.registeredStudents(), hasItem(studentMeetingPrerequisites));
    }

    @Test
    public void cancellingRegisteredStudentDoesNotRegisterAnother_IfWaitingListIsEmpty() {
        CourseSchedule schedule = fullyBookedSchedule();

        assertThat(schedule.waitListedStudents(), is(Collections.<Student>emptyList()));
        schedule.cancelBooking(student);

        assertThat(schedule.registeredStudents().isEmpty(), is(true));
    }

    @Test
    public void waitListedStudentsReturnsDefensiveCopy() {
        CourseSchedule schedule = fullyBookedSchedule();
        List<Student> localCopy = schedule.waitListedStudents();

        schedule.bookCourse(studentMeetingPrerequisites);

        assertThat(localCopy, not(hasItem(studentMeetingPrerequisites)));
    }

    @Test
    public void waitListedStudentsReturnsImmutableList() {
        CourseSchedule schedule = fullyBookedSchedule();

        schedule.bookCourse(studentMeetingPrerequisites);

        exception.expect(UnsupportedOperationException.class);

        schedule.waitListedStudents().add(student);
    }

    @Test
    public void registeredStudentsReturnsDefensiveCopy() {
        CourseSchedule schedule = new CourseSchedule(2, newHashSet(student), course);
        Set<Student> localCopy = schedule.registeredStudents();

        schedule.bookCourse(studentMeetingPrerequisites);

        assertThat(localCopy, not(hasItem(studentMeetingPrerequisites)));
    }

    @Test
    public void registeredStudentsReturnsImmutableSet() {
        CourseSchedule schedule = new CourseSchedule(2, newHashSet(student), course);

        exception.expect(UnsupportedOperationException.class);

        schedule.registeredStudents().add(studentMeetingPrerequisites);
    }

    private Student studentMeetingCoursePreRequisites() {
        return new Student("Jo", newHashSet(new Course("Data Structures", null)));
    }

    private CourseSchedule fullyBookedSchedule() {
        return new CourseSchedule(1, newHashSet(student), course);
    }

    private CourseSchedule emptySchedule() {
        return new CourseSchedule(1, new HashSet<Student>(), course);
    }
}
