package com.example.demo.servicetests;


import com.example.demo.enitities.Person;
import com.example.demo.enitities.Posts;
import com.example.demo.service.PersonJpa;
import com.example.demo.service.PostJPA;
import org.apache.catalina.core.ApplicationContext;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class ServiceT {

    private Logger logger = LoggerFactory.getLogger(ServiceT.class);

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    private PersonJpa personJpa;

    @Mock
    private Person person;

    @MockBean
    private PostJPA postJPA;



    @Test
    public void testPersonById() throws Exception {

        when(personJpa.findById(1L))
                .thenReturn(
                        java.util.Optional.of(new Person("Harrison", "Boyns")));

        this.mockMvc.perform(get("/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Harrison"));

    }


    @Test
    public void testPeople() throws Exception {

        when(personJpa.findAll()).thenReturn(Arrays.asList(
                new Person("Harrison", "Boyns"),
                new Person("Artem", "Elsiv")));

        this.mockMvc.perform(get("/people"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Harrison"))
                .andExpect(jsonPath("$[1].name").value("Artem"));

    }

    @Test
    public void testGetPosts() throws Exception {

        Posts post = new Posts("Harrison", person);
        Posts post1 = new Posts("Harrison", person);

        when(personJpa.findById(1L)).thenReturn(java.util.Optional.ofNullable(person));

        when(person.getPosts()).thenReturn(Arrays.asList(post,post1));

        this.mockMvc.perform(get("/posts/1"))
               .andDo(print())
                .andExpect(jsonPath("$[0].info").value("Harrison"))
               .andExpect(status().isOk());

    }

    @Test
    @ExceptionHandler
    public void testGetPostsNotOk() throws Exception {

        Posts post = new Posts("Harrison", person);
        Posts post1 = new Posts("Harrison", person);

        when(personJpa.findById(100L)).thenReturn(java.util.Optional.ofNullable(null));

        this.mockMvc.perform(get("/posts/100"))
                .andExpect(content().string(""));


    }
}

