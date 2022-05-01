package com.hatmani.TutorialBackend.datajpa.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hatmani.TutorialBackend.Exception.BadRequestException;
import com.hatmani.TutorialBackend.datajpa.repository.StudentRepository;

@Service
public class StudentService {

	private final StudentRepository studentRepository;

	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> getAllStudent() {
		return studentRepository.findAll();
	}

	public Student getStudentById(Long id) {
		return studentRepository.findById(id).orElse(null);
	}

	public Student addStudent(Student student) throws Exception {
		Boolean existEmail = studentRepository.selectExistsEmail(student.getEmail());
		if (!existEmail)

			return studentRepository.save(student);
		else
			throw new BadRequestException("Email taken");
	}

	public Boolean ispaire(int nb) {
		return nb % 2 == 0;
	}

	public void doProcess() {
		System.out.println("doProcess() methode started");
		pushMesageToTopic("msg");
		System.out.println("doProcess() methode ended");
	}

	public void pushMesageToTopic(String string) {
		// TODO Auto-generated method stub
		System.out.println("message pushed to Broker");
	}

	public String formatMsg(String msg) {
		String formatedmessage = upperMsg(msg);
		return formatedmessage;
	}

	private String upperMsg(String msg) {
		// TODO Auto-generated method stub
		return msg.toUpperCase();
	}

}
