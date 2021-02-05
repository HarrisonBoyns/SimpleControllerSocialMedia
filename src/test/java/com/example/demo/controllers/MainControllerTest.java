package com.example.demo.controllers;

import com.example.demo.enitities.Person;
import com.example.demo.enitities.Posts;
import com.example.demo.service.PersonJpa;
import com.example.demo.service.PostJPA;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class MainControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    private PersonJpa personJpa;

    @MockBean
    private PostJPA postJPA;

    @Mock
    private Person person;

    @Mock
    private Posts post1;

    @Mock
    private Posts post2;

    @BeforeEach
    void setUp() {
        person = new Person("Harrison", "22");
    }

    @Test
    void getPosts() throws Exception {
        when(personJpa.findById(1L)).thenReturn(java.util.Optional.ofNullable(person));

        this.mockMvc.perform(get("/get/person/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Harrison"));
    }

    @Test
    void getPostsException() throws Exception {

        this.mockMvc.perform(get("/get/person/{id}", "fail"))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException()
                instanceof MethodArgumentTypeMismatchException));
    }
    @Test
    void getPerson() {
    }

    @Test
    void getPost() {
    }

    @Test
    void addPerson() {
    }

    @Test
    void addPost() {
    }

    @Test
    void editPost() {
    }

    @Test
    void editPersonAge() {
    }

    @Test
    void editPersonName() {
    }

    @Test
    void deletePost() {
    }

    @Test
    void deletePerson() {
    }
}