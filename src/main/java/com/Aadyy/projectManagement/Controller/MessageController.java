package com.Aadyy.projectManagement.Controller;

import com.Aadyy.projectManagement.Modal.Chat;
import com.Aadyy.projectManagement.Modal.Messages;
import com.Aadyy.projectManagement.Modal.User;
import com.Aadyy.projectManagement.Request.CreateMessageRequest;
import com.Aadyy.projectManagement.services.MessageService;
import com.Aadyy.projectManagement.services.ProjectService;
import com.Aadyy.projectManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @PostMapping("/send")
    public ResponseEntity<Messages>sendMessages(@RequestBody CreateMessageRequest request) throws  Exception
    {
        User user = userService.findUserById(request.getSenderId());
        if(user == null) throw new Exception("User Not found With Id" + request.getSenderId());
        Chat chats = projectService.getProjectById(request.getProjectId()).getChat();
        if(chats == null) throw  new Exception("Chat not Found");
        Messages sentMessages  = messageService.sendMessage(request.getSenderId(),request.getProjectId(),request.getContent());
        return  ResponseEntity.ok(sentMessages);
    }

    @GetMapping("/chat/{projectId}")
    public  ResponseEntity<List<Messages>> getMessagesByChatId(@PathVariable Long projectId) throws  Exception
    {
        List<Messages> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }
}
