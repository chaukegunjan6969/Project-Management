package com.Aadyy.projectManagement.Controller;


import com.Aadyy.projectManagement.Modal.Comment;
import com.Aadyy.projectManagement.Modal.User;
import com.Aadyy.projectManagement.Request.CreateCommentRequest;
import com.Aadyy.projectManagement.Request.MessageResponse;
import com.Aadyy.projectManagement.services.CommentService;
import com.Aadyy.projectManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;


    @PostMapping()
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws  Exception
    {
        User user = userService.findUserProfilebyJwt(jwt);
        Comment createdComment = commentService.createComment(
                req.getIssueId(),
                user.getId(),
                req.getContent());
        return  new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public  ResponseEntity<MessageResponse> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("Authorization") String jwt
    )throws  Exception
    {
        User user = userService.findUserProfilebyJwt(jwt);
        commentService.deleteComment(commentId, user.getId());
        MessageResponse res = new MessageResponse();
        res.setMessage("Comment Deleted Successfulyy");
        return  new ResponseEntity<>(res,HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public  ResponseEntity<List<Comment>> getCommnetsByIssueId(@PathVariable Long issueId) throws Exception {
        List<Comment> comments = commentService.findCommentByIssueId(issueId);
        return  new ResponseEntity<>(comments,HttpStatus.OK);
    }
}
