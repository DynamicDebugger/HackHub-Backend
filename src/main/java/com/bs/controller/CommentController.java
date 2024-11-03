package com.bs.controller;

import com.bs.model.Comment;
import com.bs.model.User;
import com.bs.request.CreateCommentRequest;
import com.bs.response.MessageResponse;
import com.bs.service.CommentService;
import com.bs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest request,
            @RequestHeader("Authorization") String jwt
            ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Comment createdComment = commentService.createComment(request.getIssueId(), user.getId(), request.getContent());
        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("Authorization") String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());
        MessageResponse response = new MessageResponse("comment deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentsByIssueById(@PathVariable Long issueId){
        List<Comment> comments = commentService.findCommentByIssueById(issueId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
