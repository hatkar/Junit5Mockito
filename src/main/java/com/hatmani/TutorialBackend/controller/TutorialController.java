package com.hatmani.TutorialBackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hatmani.TutorialBackend.datajpa.model.Tutorial;
import com.hatmani.TutorialBackend.datajpa.repository.TutorialRepository;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class TutorialController {

	@Autowired
	TutorialRepository tutorialRepository;
	@GetMapping("/ping")
	public String ping()
	{
		return "SEVER UP ...";
	}
	
	
	
	
	@GetMapping("/tutorials")
	public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title)
	{
		try {
			List<Tutorial> tutorials = new ArrayList<Tutorial>();
			System.out.println("1");
			if (title==null)
			{
				tutorialRepository.findAll().forEach(tuto->tutorials.add(tuto));
				//tutorialRepository.findAll().stream().map(t->tutorials.add(t));
			System.out.println("2");
			}else
			{	System.out.println("3");
				tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);	
			}
				if (tutorials.isEmpty())
			 {	System.out.println("4");
					return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
			 }{	System.out.println("5");
				 return new ResponseEntity<>(tutorials,HttpStatus.OK);}
		} catch (Exception e) {
			System.out.println("6");
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
		@GetMapping("/tutorial/{id}")
		public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id)
		{		System.out.println("1");
			Tutorial tutorial = tutorialRepository.findById(id).orElse(null);
			System.out.println("2");
			if (tutorial==null) {
				System.out.println("3");
				return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
			}else {
				System.out.println("4");
				return new ResponseEntity<>(tutorial,HttpStatus.OK);
			}
			
			
		}
		
		@PostMapping("/tutorials")
		public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial)
		{
			try {
				
				Tutorial _Tutorial =tutorialRepository.save( new Tutorial(tutorial.getTitle(),tutorial.getDescription(),false) );
			return new ResponseEntity<>(_Tutorial,HttpStatus.CREATED);
			
			} catch (Exception e) {
				return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
		}
		@PutMapping("/tutorial/{id}")
		public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id")Long id,@RequestBody Tutorial tutorial)
		{try {

			Tutorial _Tutorial = tutorialRepository.findById(id).orElse(null);
			if(_Tutorial==null) { return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
			else
			{
				_Tutorial.setTitle(tutorial.getTitle());
				_Tutorial.setDescription(tutorial.getDescription());
				_Tutorial.setPublished(tutorial.isPublished());
				return new ResponseEntity<> (tutorialRepository.save(_Tutorial),HttpStatus.OK);
				
				
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}
		 @DeleteMapping("/tutorials")
		  public ResponseEntity<HttpStatus> deleteAllTutorials() {
		    try {
		      tutorialRepository.deleteAll();
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    } catch (Exception e) {
		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }

		  }
		 @DeleteMapping("/tutorial/{id}")
		  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") Long id) {
		    try {
		      tutorialRepository.deleteById(id);
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    } catch (Exception e) {
		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }

		  }
		  @GetMapping("/tutorials/published")
		  public ResponseEntity<List<Tutorial>> findByPublished() {
		    try {
		      List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

		      if (tutorials.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		      }
		      return new ResponseEntity<>(tutorials, HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		  }

		}

