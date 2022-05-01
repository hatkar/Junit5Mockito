package com.hatmani.TutorialBackend.datajpa.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hatmani.TutorialBackend.Exception.BadRequestException;
import com.hatmani.TutorialBackend.datajpa.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
	@Mock
	private StudentRepository studentrepository;

	private StudentService underTest;
	@Mock
	private StudentService sudentservice;

	
	@BeforeEach
	void setUp() {// on a besoin de studentrepository
		underTest = new StudentService(studentrepository);
	}

	/*
	 * @AfterEach void tearDown() throws Exception { autoCloseable.close(); }
	 */
	@Test
	void getAllStudent() {
		// when
		underTest.getAllStudent();
		// then
        //verifie that studentrepository was invoked with findall
		verify(studentrepository).findAll();
	}

	@Test
	public void canAdd() throws Exception {
		// given
		String email = "jamila@yahoo.fr";
		Student studenti = new Student("jamil", email);
		// when
		underTest.addStudent(studenti);
		// then
		// verifie that studenservice was invoked by addstudent and capture the value
		ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);
		verify(studentrepository).save(argumentCaptor.capture());
		Student capturedStudent = argumentCaptor.getValue();
		assertEquals(studenti, capturedStudent);
	}

	@Test
	public void willthrowwhenemailexist() throws Exception {
		// given
		String email = "jamila@yahoo.fr";
		Student studenti = new Student("jamil", email);

		BDDMockito.given(studentrepository.selectExistsEmail(ArgumentMatchers.anyString())).willReturn(true);
		// when

		// then
		assertThatThrownBy(() -> underTest.addStudent(studenti)).isInstanceOf(BadRequestException.class)
				.hasMessageContaining("Email taken");

		verify(studentrepository, never()).save(ArgumentMatchers.any());

	}

	@Test
	public void testGetUserById() {
		Optional<Student> st = Optional.of(new Student("sami", "sami@email.com"));
		BDDMockito.given(studentrepository.findById(10L)).willReturn(st);
		assertEquals("sami", underTest.getStudentById(10L).getName());
	}

	@ParameterizedTest
	@ValueSource(ints = { 10, 14, 16, 18, 200 })
	public void testByMultiValue() {
		assertTrue(underTest.ispaire(10));
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
	public void testByMultiValuefile(String input, Boolean expected) {

		assertEquals(expected, underTest.ispaire(Integer.parseInt(input)));
	}

	// testing methode that don't return anything

	@Test
	public void testPrivateMethode() {
		BDDMockito.willDoNothing().given(sudentservice).doProcess();
		sudentservice.doProcess();

	}

}
