package com.Aadyy.projectManagement.services;

import com.Aadyy.projectManagement.Modal.Issue;
import com.Aadyy.projectManagement.Modal.Project;
import com.Aadyy.projectManagement.Modal.User;
import com.Aadyy.projectManagement.Repository.IssueRepository;
import com.Aadyy.projectManagement.Request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService{

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private  ProjectService projectService;

    @Autowired
    private  UserService userService;

    @Override
    public Issue getIssueById(Long issueId) throws Exception {

        Optional<Issue> issue = issueRepository.findById(issueId);
        if(issue.isPresent())
        {
            return  issue.get();

        }
        throw  new Exception("No issue found with Issue Id"+issueId);
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issue createIssue(IssueRequest issueRequest, User user) throws Exception {
        Project project = projectService.getProjectById(issueRequest.getProjectID());

        Issue issue1 = new Issue();
        issue1.setTitle(issueRequest.getTitle());
        issue1.setDescription(issueRequest.getDescription());
        issue1.setStatus(issueRequest.getStatus());
        issue1.setProjectID(issueRequest.getProjectID());
        issue1.setPriority(issueRequest.getPriority());
        issue1.setDueDate(issueRequest.getDueDate());

        issue1.setProject(project);

        return issueRepository.save(issue1);
    }



    @Override
    public void deleteIssue(Long issueId, Long userid) throws Exception {
        getIssueById(issueId);
        issueRepository.deleteById(issueId);
    }





    @Override
    public Issue addUsertoIssue(Long IssueId, Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Issue issue = getIssueById(IssueId);
        issue.setAssignee(user);
        return  issueRepository.save(issue);
    }

    @Override
    public Issue UpdateStatus(Long IssueId, String status) throws Exception {

        Issue issue = getIssueById(IssueId);
        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}
