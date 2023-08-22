package com.pedrobarauna.restspringboot.services;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedrobarauna.restspringboot.exceptions.ResourceNotFoundException;
import com.pedrobarauna.restspringboot.model.Person;
import com.pedrobarauna.restspringboot.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	public List<Person> findAll() {

		logger.info("Finding all people!");
		return repository.findAll();
	}	
	
	public Person findById(Long id) {
		logger.info("Finding one person!");
		
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Pedro");
		person.setLastName("Barauna");
		person.setAdress("São Paulo");
		person.setGender("Male");
		
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records founds for this ID!"));
	}
	
	public Person create(Person person) {
		logger.info("Creating one person!");
		return repository.save(person);
	}
	
	public Person update(Person person) {
		logger.info("Updating one person!");
		Person entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records founds for this ID!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAdress(person.getAdress());
		entity.setGender(person.getGender());
		
		return repository.save(person);
	}
	
	public void delete(Long id) {
		logger.info("Deleting one person!");
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records founds for this ID!"));
		repository.delete(entity);
	}
}
