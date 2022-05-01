package com.hatmani.TutorialBackend.datajpa.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.hatmani.TutorialBackend.datajpa.model.Student;

@DataJpaTest
class StudentRepositoryTest {
	@Autowired
	private StudentRepository underTest;

	@AfterEach
	void tearDown() {
		underTest.deleteAll();
	}

	@Test
	void itshouldcheckedwhenEmailExist() {
		// given
		String email = "jamila@yahoo.fr";
		Student student = new Student("jamil", email);

		underTest.save(student);
		// when
		boolean expected = underTest.selectExistsEmail(email);
		// then
		assertTrue(expected);
	}

	@Test
	void itshouldcheckedwhenEmailNotExist() {
		// given
		String email = "jamila@yahoo.fr";

		// when
		boolean expected = underTest.selectExistsEmail(email);
		// then
		assertFalse(expected);
	}

}
