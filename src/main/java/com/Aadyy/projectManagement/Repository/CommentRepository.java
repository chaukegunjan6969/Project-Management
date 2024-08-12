package com.Aadyy.projectManagement.Repository;

import com.Aadyy.projectManagement.Modal.Comment;
import com.Aadyy.projectManagement.Modal.Issue;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByIssue(Issue issueId);
}
