package com.example.demo.service;

import com.example.demo.enitities.Person;
import org.springframework.data.jpa.mapping.JpaPersistentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonJpa extends JpaRepository<Person, Long>{

}
