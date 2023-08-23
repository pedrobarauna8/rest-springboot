package com.pedrobarauna.restspringboot.services;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedrobarauna.restspringboot.data.vo.v1.PersonVO;
import com.pedrobarauna.restspringboot.exceptions.ResourceNotFoundException;
import com.pedrobarauna.restspringboot.mapper.DozerMapper;
import com.pedrobarauna.restspringboot.model.Person;
import com.pedrobarauna.restspringboot.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	public List<PersonVO> findAll() {

		logger.info("Finding all people!");
		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class) ;
	}	
	
	public PersonVO findById(Long id) {
		logger.info("Finding one person!");
		
		PersonVO person = new PersonVO();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Pedro");
		person.setLastName("Barauna");
		person.setAdress("São Paulo");
		person.setGender("Male");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records founds for this ID!"));
		
		return DozerMapper.parseObject(entity, PersonVO.class);
	}
	
	public PersonVO create(PersonVO person) {
		logger.info("Creating one person!");
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		logger.info("Updating one person!");
		var entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records founds for this ID!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAdress(person.getAdress());
		entity.setGender(person.getGender());
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
	
	public void delete(Long id) {
		logger.info("Deleting one person!");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records founds for this ID!"));
		repository.delete(entity);
	}
}
