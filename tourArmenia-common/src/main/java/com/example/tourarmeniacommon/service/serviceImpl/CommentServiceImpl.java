package com.example.tourarmeniacommon.service.serviceImpl;

import com.example.tourarmeniacommon.entity.Comment;
import com.example.tourarmeniacommon.repository.CommentRepository;
import com.example.tourarmeniacommon.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findAllByTourId(int id) {
       return commentRepository.findAllByTourId(id);
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }
}
