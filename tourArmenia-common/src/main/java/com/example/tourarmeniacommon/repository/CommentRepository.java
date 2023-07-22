package com.example.tourarmeniacommon.repository;

import com.example.tourarmeniacommon.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
List<Comment> findAllByTourId(int tourId);
}
