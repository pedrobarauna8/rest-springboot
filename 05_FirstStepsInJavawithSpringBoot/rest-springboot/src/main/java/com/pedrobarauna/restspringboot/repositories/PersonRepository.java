package com.pedrobarauna.restspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pedrobarauna.restspringboot.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{}
