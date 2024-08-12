package com.Aadyy.projectManagement.services;

import com.Aadyy.projectManagement.Modal.User;
import com.Aadyy.projectManagement.Repository.UserRepository;
import com.Aadyy.projectManagement.config.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public User findUserProfilebyJwt(String jwt) throws Exception {

        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if(user==null)
        {
            throw  new Exception("User Not Found");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {

        Optional<User>  optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty())
        {
            throw  new Exception("User not found");
        }
        return optionalUser.get();
    }

    @Override
    public User UpdateUsersProjectSize(User user, int number) {

        user.setProjectSize(user.getProjectSize()+number);

        return userRepository.save(user);
    }
}
