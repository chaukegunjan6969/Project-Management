package com.Aadyy.projectManagement.Controller;

import com.Aadyy.projectManagement.Modal.User;
import com.Aadyy.projectManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(
            @RequestHeader("Authorization") String jwt
    ) throws  Exception
    {
        User user = userService.findUserProfilebyJwt(jwt);
        return  new ResponseEntity<>(user, HttpStatus.OK);
    }
}
