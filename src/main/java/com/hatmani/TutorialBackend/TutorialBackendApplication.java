package com.hatmani.TutorialBackend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hatmani.TutorialBackend.datajpa.model.Tutorial;
import com.hatmani.TutorialBackend.datajpa.repository.TutorialRepository;

@SpringBootApplication
public class TutorialBackendApplication {

	@Autowired
	TutorialRepository _TutorialRepository;
	public static void main(String[] args) {
		SpringApplication.run(TutorialBackendApplication.class, args);
	}
	
	/*@Bean
	public void addtuto()
	{
		List<Tutorial> tutorials = Arrays.asList(
				new Tutorial("Spring Boot Developpement", "Spring Web ,Spring Batch ,Spring Mvc ,Spring Reactive ...", false),
				new Tutorial("React  Developpement", "React js,React Native,Redux ...", false),
				new Tutorial("CQRS Architecture", "CQRS,Event Sourcing,RabbitMQ ...", false));
		
			_TutorialRepository.saveAll(tutorials);
			_TutorialRepository.findAll().forEach(tuto->System.out.println(tuto.toString()));
	}*/

}
