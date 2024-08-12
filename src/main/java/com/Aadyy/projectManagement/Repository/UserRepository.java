package com.Aadyy.projectManagement.Repository;

import com.Aadyy.projectManagement.Modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
