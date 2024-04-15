<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
 <link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet" >
 <meta charset="ISO-8859-1">
 <title>PhoneBook-App</title>
</head>

<body>
<div style = "margin: auto; width:50%">
    <form:form style = "margin:10px; length:10px" method = "post" modelAttribute="contact" >
    <div style = "text-align: center">
    <h1><u><em>Contact Info</em></u></h1>
    </div>
  <div class="form-group" style ="font-size: 1.5rem">
    <label for="name" style = "padding-left: 10px"><i>Name:</i></label>
    <form:input type="text" class="form-control" path="name" required="required"/>
  </div>
  
  <div class="form-group" style ="font-size: 1.5rem">
    <label for="email" style = "padding-left: 10px"><i>Email:</i></label>
    <form:input type="email" class="form-control" path="email" required="required"/>
  </div>
  
  
  <fieldset>
    <div class="form-group" style= "font-size: 1.5rem">
     <form:label path="phno" style = "padding-left: 10px"><i>Phno:</i></form:label>
     <form:input type="number" class="form-control" path="phno" required="required"/>
   </div>
  </fieldset>
  
  <div style ="text-align:center; margin: 10px">
  <button type="submit" class="btn btn-success" ><i>Save</i></button>
  </div>
  
  <div style = "margin:15px; text-align: center; font-size:1.3rem">
  <a href="all-contacts" class="btn btn-outline-secondary"><i>View All Contacts</i> </a>
  </div>
  
  </form:form>
</div>
	<script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
	<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
		        
</body>
</html>