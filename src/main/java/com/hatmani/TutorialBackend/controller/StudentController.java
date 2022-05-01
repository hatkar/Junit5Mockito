package com.hatmani.TutorialBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hatmani.TutorialBackend.Exception.BadRequestException;
import com.hatmani.TutorialBackend.datajpa.model.Student;
import com.hatmani.TutorialBackend.datajpa.model.StudentService;
import com.hatmani.TutorialBackend.datajpa.model.Tutorial;

@RestController
@RequestMapping("/student/")
public class StudentController {
	
	@Autowired
	StudentService studentService;

	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}

	@GetMapping
	public ResponseEntity<List<Student>> getAllStudent() {

		 return new ResponseEntity<>(studentService.getAllStudent(),HttpStatus.OK);
		 
		
	}

	
	@GetMapping("{id}")
	public ResponseEntity<Student> getTutorialById(@PathVariable("id") long id)
	{		
		Student st =studentService.getStudentById(id);
		if (st==null) {
			
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}else {
			
			return new ResponseEntity<>(st,HttpStatus.OK);
		}
		
		
	}
	
	

	@PostMapping("subscribe")
	public  ResponseEntity<Student>  createStudent(@RequestBody Student student) throws Exception {
	
		Student st =studentService.addStudent(student);
		
		return new ResponseEntity<>(st,HttpStatus.CREATED);
		
		
		
	}
	// TODO: write /update and /delete endpoint using TDD methode

	@DeleteMapping("{id}")
	public void deleteStudent(@PathVariable("id") long id)
	{
		
	}
}
