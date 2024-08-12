package com.Aadyy.projectManagement.Repository;

import com.Aadyy.projectManagement.Modal.Project;
import com.Aadyy.projectManagement.Modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {

//    List<Project>findByOwner(User user);

    List<Project> findByNameContainingAndTeamContains(String name, User user);


//    @Query("Select p From Project p join p.team t where t:=user")
//    List<Project> findProjectByTeam(@Param("user") User user);

    List<Project> findByTeamContainingOrOwner(User teamMember, User owner);

}
