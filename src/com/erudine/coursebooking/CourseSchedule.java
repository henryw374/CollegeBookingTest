package com.erudine.coursebooking;

import java.util.Collection;
import java.util.Set;

public class CourseSchedule {

	private Course course;
	private Set<Student> registeredStudents;
	private Collection<Student> waitListedStudents;
	private int courseCapacity;

	// the following commented out fields are just there to give more of an idea
	// about what a course schedule is in the real world. For this
	// exercise, there is no need uncomment them and include them
	// in your answer
	//private Date startDate;
	//private Date endDate;
	//private String teacher;
	
	/**
	 * @return true if the student successfully registered. 
	 */
	public boolean bookCourse(Student studentWantingToJoinCourse){
		return false;		
	}
	
	public void cancelBooking(Student student){}
	
	
}
