package com.example.demo.enitities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Posts {

    @Id
    @Column
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Getter
    @Setter
    @NotNull
    @Size(min=5)
    private String info;

    @ManyToOne
    @Getter
    @Setter
    @JsonIgnore
    private Person person;

    public Posts() {
    }

    public Posts(String info, Person person) {
        this.info = info;
        this.person = person;
    }

    public Posts(Long id, String info, Person person) {
        this.id = id;
        this.info = info;
        this.person = person;
    }
}
