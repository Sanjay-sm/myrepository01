package com.ri.soap.webservices.soapcoursemanagement.soap.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ri.soap.webservices.soapcoursemanagement.soap.bean.Course;
import com.ri.courses.*;

@Component
public class CourseDetailsService {
	
	
	private static List<Course> courses = new ArrayList<>();
	
	static {
		Course course1 = new Course(1,"Spring", "Basic of Spring framework");
		Course course2 = new Course(2,"Sprinn Mvc", "Basic of Spring boot");
		Course course3 = new Course(3,"Spring Boot", "Spring Boot");
		Course course4 = new Course(4,"Maven", "Good Build project in the internet");
		courses.add(course1);
		courses.add(course2);
		courses.add(course3);
		courses.add(course4);
	}
	
	public Course findById(int id) {
		for(Course course:courses) {
			if(course.getId() == id) {
				return course;
			}
		}
		return null;
	}
	
	public List<Course> findAll(){
		return courses;
	}
	
	public Status deleteById(int id) {
		 Iterator<Course> iterator =  courses.iterator();
		 while(iterator.hasNext()) {
			 Course course = iterator.next();
			 if(course.getId() == id) {
				 iterator.remove();
				 return Status.SUCCESS;
			 }
			 }
		 return Status.FAILURE;
		 
	}

}
