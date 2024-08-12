package com.Aadyy.projectManagement.Controller;


import com.Aadyy.projectManagement.Modal.Issue;
import com.Aadyy.projectManagement.Modal.IssueDTO;
import com.Aadyy.projectManagement.Modal.User;
import com.Aadyy.projectManagement.Request.IssueRequest;
import com.Aadyy.projectManagement.Request.MessageResponse;
import com.Aadyy.projectManagement.Response.AuthResponse;
import com.Aadyy.projectManagement.services.IssueService;
import com.Aadyy.projectManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIsssueId(@PathVariable Long issueId) throws  Exception
    {
        return  ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjecctId(@PathVariable Long projectId) throws  Exception
    {
        return  ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
    }

    @PostMapping
    public  ResponseEntity<IssueDTO> craeteIssue(@RequestBody IssueRequest issue,
                                                 @RequestHeader("Authorization") String token) throws Exception {
        User tokenUser = userService.findUserProfilebyJwt(token);
        User user = userService.findUserById((tokenUser.getId()));

          Issue createdIssue = issueService.createIssue(issue,tokenUser);

          IssueDTO issueDTO = new IssueDTO();
          issueDTO.setDescription(createdIssue.getDescription());
          issueDTO.setPriority(createdIssue.getPriority());
          issueDTO.setDueDate(createdIssue.getDueDate());
          issueDTO.setStatus(createdIssue.getStatus());
          
          
          issueDTO.setProject(createdIssue.getProject());
          issueDTO.setProjectId(createdIssue.getProjectID());
          issueDTO.setId(createdIssue.getId());
          issueDTO.setTitle(createdIssue.getTitle());
          issueDTO.setTags(createdIssue.getTags());
          issueDTO.setAssignee(createdIssue.getAssignee());

          return  ResponseEntity.ok(issueDTO);


    }

    @DeleteMapping("/{issueId}")
    public  ResponseEntity<MessageResponse> deleteIssue(
            @PathVariable Long issueId,
            @RequestHeader("Authorization") String token
    ) throws  Exception
    {
        User user = userService.findUserProfilebyJwt(token);
        issueService.deleteIssue(issueId, user.getId());

        MessageResponse res = new MessageResponse();
        res.setMessage("Issue Deleted");

        return  ResponseEntity.ok(res);
    }


    @PutMapping("/{issueId}/assignee/{userId}")
    public  ResponseEntity<Issue> addUserToIssue(
            @PathVariable Long issueId,
            @PathVariable Long userId
    ) throws  Exception
    {
        Issue issue = issueService.addUsertoIssue(issueId,userId);

        return  ResponseEntity.ok(issue);

    }


    @PutMapping("/{issueId}/status/{status}")
    public  ResponseEntity<Issue>updateIssueStatus(
             @PathVariable String status,
             @PathVariable Long issueId
    ) throws  Exception
    {
        Issue issue = issueService.UpdateStatus(issueId,status);
        return  ResponseEntity.ok(issue);

    }



























}
