package com.example.demo.enitities;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@JsonFilter(value = "PersonFilter")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column
    @Size(min = 2, max = 20, message = "Name should have atleast 2 characters and less than 20")
    @NotNull(message = "No name provided")
    private String name;


    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "person")
    private List<Posts> posts;

    @Getter
    @Setter
    @Column
    @Size(min = 2,   max = 200, message = "Message too long or too short")
    @NotNull(message = "No age provided")
    private String age;

    public Person() {
    }

    public Person(@NotNull String name, @NotNull String age) {
        this.name = name;
        this.age = age;
    }

    public List<Posts> getPosts() {
        return posts;
    }
}
