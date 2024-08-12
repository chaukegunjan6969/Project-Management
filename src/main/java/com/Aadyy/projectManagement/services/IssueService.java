package com.Aadyy.projectManagement.services;

import com.Aadyy.projectManagement.Modal.Issue;
import com.Aadyy.projectManagement.Modal.User;
import com.Aadyy.projectManagement.Request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Issue getIssueById(Long issueId) throws Exception;

    List<Issue> getIssueByProjectId(Long projectId) throws  Exception;

    Issue createIssue(IssueRequest issue, User user) throws  Exception;


    void  deleteIssue(Long issueId, Long userid) throws  Exception;


    Issue addUsertoIssue(Long IssueId, Long userId) throws  Exception;

    Issue UpdateStatus(Long IssueId, String status)throws  Exception;
}
