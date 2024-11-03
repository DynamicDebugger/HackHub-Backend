package com.bs.service;

import com.bs.model.Comment;
import com.bs.model.Issue;
import com.bs.model.User;
import com.bs.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private UserService userService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment createComment(Long issueId, Long userId, String content) throws Exception {
        Issue issue = issueService.getIssueById(issueId);
        User user = userService.findUserById(userId);

        Comment createdComment = new Comment();

        createdComment.setContent(content);
        createdComment.setUser(user);
        createdComment.setIssue(issue);
        createdComment.setCreatedDateTime(LocalDateTime.now());

        Comment savedComment = commentRepository.save(createdComment);

        issue.getComments().add(savedComment);

        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        User user = userService.findUserById(userId);

        if (commentOptional.isEmpty()) throw new Exception("comment not found with id " + commentId);

        Comment comment = commentOptional.get();

        if (comment.getUser().equals(user)){
            commentRepository.delete(comment);
        }else {
            throw new Exception("user does not have permission to delete this comment");
        }
    }

    @Override
    public List<Comment> findCommentByIssueById(Long issueId) {
        return commentRepository.findByIssueId(issueId);
    }
}
