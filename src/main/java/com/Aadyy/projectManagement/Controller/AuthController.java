package com.Aadyy.projectManagement.Controller;

import com.Aadyy.projectManagement.Modal.User;
import com.Aadyy.projectManagement.Repository.UserRepository;
import com.Aadyy.projectManagement.Request.LoginRequest;
import com.Aadyy.projectManagement.Response.AuthResponse;
import com.Aadyy.projectManagement.config.JwtProvider;
import com.Aadyy.projectManagement.services.CustomUserDetailsImpl;
import com.Aadyy.projectManagement.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsImpl customUserDetails;

    @Autowired
    private SubscriptionService subscriptionService;

   @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        User isUserExist = userRepository.findByEmail(user.getEmail());

        if(isUserExist!=null)
        {
            throw  new Exception("Email already exist with another account");
        }

        User createdUser = new User();
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setEmail(user.getEmail());
        createdUser.setFullname(user.getFullname());

        User savedUser = userRepository.save(createdUser);
        subscriptionService.createSubscription(savedUser);

       Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
       SecurityContextHolder.getContext().setAuthentication(authentication);

       String jwt = JwtProvider.generateToken(authentication);

       AuthResponse res  = new AuthResponse();
       res.setMessage("SignUp Success");
       res.setJwt(jwt);

        return  new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signing")
    public  ResponseEntity<AuthResponse> SignIn(@RequestBody LoginRequest loginreq)
    {
        String email = loginreq.getEmail();
        String password = loginreq.getPassword();

        Authentication authentication = authenticate(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse res  = new AuthResponse();
        res.setMessage("SignIN Success");
        res.setJwt(jwt);

        return  new ResponseEntity<>(res, HttpStatus.CREATED);

    }

    private Authentication authenticate(String email, String password) {

        UserDetails userDetails = customUserDetails.loadUserByUsername(email);

        if(userDetails==null)
        {
            throw  new BadCredentialsException("Invalid Username");
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword()))
        {
            throw  new BadCredentialsException("InCorrect Password");
        }

        return  new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
