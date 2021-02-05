package com.example.demo.controllers;


import com.example.demo.enitities.Person;
import com.example.demo.enitities.Posts;
import com.example.demo.exceptions.PostNotFound;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.service.PersonJpa;
import com.example.demo.service.PostJPA;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class MainController {

    Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private PersonJpa personJpa;

    @Autowired
    private PostJPA postJpa;

    @GetMapping(path = "/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping(path = "/people")
    public List<Person> getPeople() {
        return personJpa.findAll();
    }

    @GetMapping(path = "/posts")
    public List<Posts> getPosts() {
        return postJpa.findAll();
    }

    @GetMapping(path = "/person/{person_id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long person_id) {
        Optional<Person> person_optional = personJpa.findById(person_id);
        if (person_optional.isEmpty()) {
            throw new UserNotFoundException("id-" + person_id);
        }

        Person person = person_optional.get();

        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    @GetMapping(path = "/post/{post_id}")
    public ResponseEntity<Posts> getPost(@PathVariable Long post_id) {
        Optional<Posts> post_optional = postJpa.findById(post_id);
        if (post_optional.isEmpty()) {
            throw new PostNotFound("id-" + post_id);
        }

        Posts post = post_optional.get();

        return new ResponseEntity<Posts>(post, HttpStatus.OK);
    }

    @PostMapping(path = "/add/person")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        personJpa.save(person);

        return new ResponseEntity<Person>(person, HttpStatus.CREATED);
    }

    @PostMapping(path = "/add/post/{person_id}")
    public ResponseEntity<Posts> addPost(@RequestBody Posts posts, @PathVariable Long person_id) {
        Optional<Person> person = personJpa.findById(person_id);
        if (person.isEmpty()) {
            throw new UserNotFoundException("Not possible to add a post without a user id");
        }

        posts.setPerson(person.get());

        postJpa.save(posts);

        return new ResponseEntity<Posts>(posts, HttpStatus.CREATED);
    }

    @PutMapping(path = "/post/edit/info/{post_id}")
    public ResponseEntity<Posts> editPost(@RequestBody Posts posts, @PathVariable Long post_id) {
        Optional<Posts> post_optional = postJpa.findById(post_id);
        if (post_optional.isEmpty()) {
            throw new PostNotFound("id-" + post_id);
        }
        Posts post_edited = post_optional.get();

        post_edited.setInfo(posts.getInfo());

        postJpa.save(post_edited);

        return new ResponseEntity<Posts>(post_edited, HttpStatus.OK);
    }

    @PutMapping(path = "/person/edit/age/{person_id}")
    public ResponseEntity<Person> editPersonAge(@RequestBody Person person, @PathVariable Long person_id) {
        Optional<Person> person_optional = personJpa.findById(person_id);
        if (person_optional.isEmpty()) {
            throw new UserNotFoundException("id-" + person_id);
        }
        Person person_edited = person_optional.get();

        person_edited.setAge(person.getAge());

        personJpa.save(person_edited);

        return new ResponseEntity<Person>(person_edited, HttpStatus.OK);
    }

    @PutMapping(path = "/person/edit/name/{person_id}")
    public ResponseEntity<Person> editPersonName(@RequestBody Person person, @PathVariable Long person_id) {
        Optional<Person> person_optional = personJpa.findById(person_id);
        if (person_optional.isEmpty()) {
            throw new UserNotFoundException("id-" + person_id);
        }
        Person person_edited = person_optional.get();

        person_edited.setAge(person.getAge());

        personJpa.save(person_edited);

        return new ResponseEntity<Person>(person_edited, HttpStatus.OK);
    }

    @DeleteMapping(path = "/post/delete/{post_id}")
    public ResponseEntity<Posts> deletePost(@PathVariable Long post_id) {
        Optional<Posts> post_optional = postJpa.findById(post_id);
        if (post_optional.isEmpty()) {
            throw new PostNotFound("id : " + post_id);
        }
        postJpa.deleteById(post_id);

        return new ResponseEntity<Posts>(post_optional.get(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/person/post/{person_id}")
    public ResponseEntity<Person> deletePerson(@PathVariable Long person_id) {
        Optional<Person> person_optional = personJpa.findById(person_id);
        if (person_optional.isEmpty()) {
            throw new UserNotFoundException("id : " + person_id);
        }

        personJpa.deleteById(person_id);

        return new ResponseEntity<Person>(person_optional.get(), HttpStatus.OK);
    }



    @GetMapping(path = "/get/person/{person_id}/age")
    public ResponseEntity<MappingJacksonValue> filterPerson(@PathVariable Long person_id){
        Optional<Person> person = personJpa.findById(person_id);
        if(person.isEmpty()){
            throw new UserNotFoundException("id" + person_id);
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("age");

        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("PersonFilter", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(person.get());

        mappingJacksonValue.setFilters(filterProvider);

        return new ResponseEntity<MappingJacksonValue>(mappingJacksonValue, HttpStatus.OK);
    }
}
