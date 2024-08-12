package com.Aadyy.projectManagement.services;


import com.Aadyy.projectManagement.Modal.Comment;
import com.Aadyy.projectManagement.Modal.Issue;
import com.Aadyy.projectManagement.Modal.User;
import com.Aadyy.projectManagement.Repository.CommentRepository;
import com.Aadyy.projectManagement.Repository.IssueRepository;
import com.Aadyy.projectManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements  CommentService{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Comment createComment(Long issueId, Long userId, String content) throws Exception {

        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findById(userId);

        if(issueOptional.isEmpty())
        {
            throw  new Exception("Issue Not Found with Id"+issueId);
        }

        if(userOptional.isEmpty())
        {
            throw new Exception("User Not Found with ID "+ userId);
        }

        Issue issue = issueOptional.get();
        User user = userOptional.get();

        Comment comment = new Comment();

        comment.setIssue(issue);
        comment.setUser(user);
        comment.setCreatedTime(LocalDateTime.now());
        comment.setContent(content);

        Comment savedComment = commentRepository.save(comment);

        issue.getComments().add( savedComment);

        ///4:28 minutes


        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {

        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Optional<User> userOptional = userRepository.findById(userId);

        if(commentOptional.isEmpty())
        {
            throw  new Exception("Comment Not Found with Id"+commentId);
        }

        if(userOptional.isEmpty())
        {
            throw new Exception("User Not Found with ID "+ userId);
        }

        Comment comment = commentOptional.get();
        User user = userOptional.get();

        if(comment.getUser().equals(user))
        {
            commentRepository.delete(comment);
        }
        else {
            throw new Exception("User Does Not Have Permission to Delete this Comment");
        }

    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) throws  Exception {
        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        if (issueOptional.isEmpty()) {
            throw new Exception("Issue Not Found with Id " + issueId);
        }
        Issue issue = issueOptional.get();
        return commentRepository.findByIssue(issue);
    }
}
