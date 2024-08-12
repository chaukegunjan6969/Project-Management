package com.Aadyy.projectManagement.services;

import com.Aadyy.projectManagement.Modal.User;

public interface UserService {

    User findUserProfilebyJwt(String jwt) throws  Exception;

    User findUserByEmail(String email)throws  Exception;

    User findUserById(Long userId)throws  Exception;

    User UpdateUsersProjectSize(User user,int number);
}
