package com.ri.soap.webservices.soapcoursemanagement.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ri.courses.CourseDetails;
import com.ri.courses.DeleteCourseDetailsRequest;
import com.ri.courses.DeleteCourseDetailsResponse;
import com.ri.courses.GetAllCourseDetailsRequest;
import com.ri.courses.GetAllCourseDetailsResponse;
import com.ri.courses.GetCourseDetailsRequest;
import com.ri.courses.GetCourseDetailsResponse;
import com.ri.courses.Status;
import com.ri.soap.webservices.soapcoursemanagement.soap.bean.Course;
import com.ri.soap.webservices.soapcoursemanagement.soap.exception.CourseNotFoundException;
import com.ri.soap.webservices.soapcoursemanagement.soap.service.CourseDetailsService;


@Endpoint
public class CourseDetailsEndpoint {
	@Autowired
	private CourseDetailsService service;
	
  @PayloadRoot(namespace = "http://ri.com/courses", localPart = "GetCourseDetailsRequest")
  @ResponsePayload
  public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {
	  Course course =  service.findById(request.getId());
	  if(course == null) {
		  throw new CourseNotFoundException("Inavlid Course id "+ " "+ request.getId());
	  }
	  
	   return mapCourseDetails(course);
  }

private GetCourseDetailsResponse mapCourseDetails(Course course) {
	GetCourseDetailsResponse response = new GetCourseDetailsResponse();
	response.setCourseDetails(mapCourse(course));
	return response;
}

private CourseDetails mapCourse(Course course) {
	CourseDetails courseDetails = new CourseDetails();
	courseDetails.setId(course.getId());
	courseDetails.setName(course.getName());
	courseDetails.setDescription(course.getDescription());
	return courseDetails;
}
private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
	GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
	for(Course course:courses) {
	  CourseDetails mapCourse = mapCourse(course);
	  response.getCourseDetails().add(mapCourse);
	}
	return response;
}

@PayloadRoot(namespace = "http://ri.com/courses", localPart = "GetAllCourseDetailsRequest")
@ResponsePayload
public GetAllCourseDetailsResponse processAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailsRequest request) {
	 List<Course> courses =  service.findAll();
	   return mapAllCourseDetails(courses);
}

@PayloadRoot(namespace = "http://ri.com/courses", localPart = "DeleteCourseDetailsRequest")
@ResponsePayload
public DeleteCourseDetailsResponse processDeleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {
	  Status status = service.deleteById(request.getId());
	  return responseStatus(status);
}


private DeleteCourseDetailsResponse responseStatus(Status status) {
	DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
	response.setStatus((status));
	return response;
}



}
