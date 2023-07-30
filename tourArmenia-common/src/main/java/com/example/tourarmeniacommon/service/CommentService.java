package com.example.tourarmeniacommon.service;

import com.example.tourarmeniacommon.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAllByTourId(int id);

    void save(Comment comment);
}
