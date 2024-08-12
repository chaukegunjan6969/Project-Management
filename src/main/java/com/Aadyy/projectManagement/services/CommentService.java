package com.Aadyy.projectManagement.services;

import com.Aadyy.projectManagement.Modal.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Long issueId, Long userId, String content)throws  Exception;

    void  deleteComment(Long commentId,Long userId) throws  Exception;

    List<Comment> findCommentByIssueId(Long issueId) throws  Exception;
}
