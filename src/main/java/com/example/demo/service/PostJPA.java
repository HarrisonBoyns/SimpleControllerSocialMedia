package com.example.demo.service;

import com.example.demo.enitities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJPA extends JpaRepository<Posts, Long> {
}
