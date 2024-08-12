package com.Aadyy.projectManagement.Repository;

import com.Aadyy.projectManagement.Modal.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation,Long> {

    Invitation findByToken(String token);

    Invitation findByEmail(String userEmail);

}
