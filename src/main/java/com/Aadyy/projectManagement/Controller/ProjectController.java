package com.Aadyy.projectManagement.Controller;

import com.Aadyy.projectManagement.Modal.*;
import com.Aadyy.projectManagement.Repository.InviteRequest;
import com.Aadyy.projectManagement.Request.MessageResponse;
import com.Aadyy.projectManagement.services.InvitationService;
import com.Aadyy.projectManagement.services.ProjectService;
import com.Aadyy.projectManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required = false)String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfilebyJwt(jwt);
        List<Project> projects = projectService.getProjectByTeam(user,category,tag);
        return  new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectid}")
    public ResponseEntity<Project> getProjects(
            @PathVariable Long projectid,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfilebyJwt(jwt);
        Project projects = projectService.getProjectById(projectid);
        return  new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfilebyJwt(jwt);
        Project createdProject = projectService.createproject(project,user);

        return  new ResponseEntity<>(createdProject, HttpStatus.OK);
    }

    @PatchMapping("/{projectid}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectid,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfilebyJwt(jwt);
        Project updatedProject = projectService.updatedProject(project,projectid);

        return  new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectid}")
    public ResponseEntity<MessageResponse> DeleteMapping(
            @PathVariable Long projectid,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfilebyJwt(jwt);
        projectService.deleteProject(projectid,user.getId());
        MessageResponse msg = new MessageResponse();
        msg.setMessage("Project Deleted Succesfulyy");

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/search")
    public  ResponseEntity<List<Project>> searchProject(
            @RequestParam(required = false) String keyword,
    @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfilebyJwt(jwt);
        List<Project> projects = projectService.searchProject(keyword,user);
        return  new ResponseEntity<>(projects,HttpStatus.OK);
    }

    @GetMapping("/{projectid}/chat")
    public  ResponseEntity<Chat> getChatbyProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfilebyJwt(jwt);
        Chat chat  = projectService.getChatbyProjectId(projectId);

        return  new ResponseEntity<>(chat,HttpStatus.OK);
    }

    @PostMapping("/invite")
    public  ResponseEntity<MessageResponse>inviteProject(
            @RequestBody InviteRequest req,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
            )throws  Exception
    {
        User user = userService.findUserProfilebyJwt(jwt);

        invitationService.sendInvitation(req.getEmail(), req.getProjectId());
        MessageResponse res = new MessageResponse();
        res.setMessage("User Invitation Sent");
        return new  ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/accept_invitation")
    public  ResponseEntity<Invitation>AcceptInvitation(
            @RequestParam String token,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    )throws  Exception
    {
        User user = userService.findUserProfilebyJwt(jwt);
        Invitation invitation = invitationService.acceptInvitation(token, user.getId());
        projectService.addUserToProject(invitation.getProjectId(),user.getId());
        return new  ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }




}
