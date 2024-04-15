<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
 <link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet" >
 <meta charset="ISO-8859-1">
 <title>PhoneBook-App</title>
</head>
<body>
  <div class="container" >
			<div>
			  <a href="contact-info" style = "margin-top:10px; font-size: 1.3rem" class = "btn btn-outline-secondary">
			        +Add New Contacts</a>
			</div>
			 <div style = "text-align: center">
			   <h1><u>All Contacts</u></h1>
			 </div>
			<table class="table table-bordered" style="border: 1.5px">
				<thead style = "font-size: 1.5rem">
					<tr>
						<th>Sr.no</th>
						<th>Name</th>
						<th>Email</th>
						<th>Phno</th>
						<th colspan="2" style="text-align: center"><u>Action</u></th>
						
					</tr>
				</thead>
				<tbody>		
					<c:forEach items="${contacts}" var="contact">
					
						<tr style = "font-size: 1.3rem">
							<td>${contact.id}</td>
							<td>${contact.name}</td>
							<td>${contact.email}</td>
							<td>${contact.phno}</td>
							<td><a href="update-contact?id=${contact.id}" class="btn btn-outline-success" >Edit</a></td> 
							<td><a href="delete-contact?id=${contact.id}" class="btn btn-outline-danger" >Delete</a></td>
						</tr>
						
					</c:forEach>
				</tbody>
			</table>
		
	</div>
		
	<script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
	<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
	
		
</body>
</html>