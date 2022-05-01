package com.hatmani.TutorialBackend.Controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.hatmani.TutorialBackend.controller.StudentController;
import com.hatmani.TutorialBackend.datajpa.model.Student;
import com.hatmani.TutorialBackend.datajpa.model.StudentService;
import com.hatmani.TutorialBackend.datajpa.repository.StudentRepository;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class StudentControllerTest
{
	private MockMvc mockmvc;
	ObjectMapper objectMapper =new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Mock
	StudentService studentService;
	StudentController underTestController;

	
	Student st1 = new Student("student1","student1@gmail.com");
	Student st2 = new Student("student2","student2@gmail.com");
	Student st3 = new Student("student3","student3@gmail.com");

	@BeforeEach
public void setUp()
{
	underTestController = new StudentController(studentService);
	this.mockmvc=MockMvcBuilders.standaloneSetup(underTestController).build();
	//studentService =new StudentService(strep);
//underTestController= new StudentController(studentService);

	
//	studentService = new StudentService(strep);

}
	
	@Test
	
	public void getAllStudent_success() throws Exception
	{
		List<Student> records = new ArrayList<>(Arrays.asList(st1,st2,st3));
		
		BDDMockito.given(studentService.getAllStudent()).willReturn(records);
		
		mockmvc.perform(MockMvcRequestBuilders
				.get("/student/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[2].name",is("student3")));
	}

	
	@Test 
	
	public void getStudentById_success() throws Exception
	{
	 BDDMockito.given(studentService.getStudentById(1L))
				.willReturn(st1);
	mockmvc.perform(MockMvcRequestBuilders
			.get("/student/1"))
	.andExpect(status().isOk())
	.andExpect(jsonPath("$",notNullValue()))
	.andExpect(jsonPath("$.name", is("student1")));
	
	
	}
	@Test 
	public void getStudentById_failure() throws Exception
	{
	 BDDMockito.given(studentService.getStudentById(5L))
				.willReturn(null);
	mockmvc.perform(MockMvcRequestBuilders
			.get("/student/5"))
	.andExpect(status().is(400));
	
	
	
	
	}
	
	@Test 
	public void createStudent_success() throws Exception
	{
		Student mstud= new Student(7L,"student5","student@email.com");
		BDDMockito.given(studentService.addStudent(Matchers.any())).willReturn(mstud);
		 
		String content =objectWriter.writeValueAsString(mstud);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/student/subscribe")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(content);
		 
	mockmvc.perform(mockRequest)
			
	.andExpect(status().isCreated())
	.andExpect(jsonPath("$",notNullValue()))
	.andExpect(jsonPath("$.name", is("student5")));
	verify(studentService).addStudent(Matchers.any());
	
	}	
	
}
