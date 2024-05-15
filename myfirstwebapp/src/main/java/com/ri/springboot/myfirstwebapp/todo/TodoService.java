package com.ri.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class TodoService 
{
	private static List<Todo> todo = new ArrayList<Todo>();
	
	static 
	{
	  todo.add(new Todo(1,"tapacad","java",LocalDate.now().plusYears(1),false));   
	  todo.add(new Todo(2,"tapacad","Python",LocalDate.now().plusYears(2),true));   
	  todo.add(new Todo(3,"tapacad","C++",LocalDate.now().plusYears(3),false));   
	}
	
	public List<Todo> findByUsername(String username)
	{
		return todo;
	}
}
