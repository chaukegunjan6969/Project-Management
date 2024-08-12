package com.Aadyy.projectManagement.services;

import com.Aadyy.projectManagement.Modal.Chat;
import com.Aadyy.projectManagement.Modal.Project;
import com.Aadyy.projectManagement.Modal.User;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Project createproject(Project project,User user) throws  Exception;

    List<Project> getProjectByTeam(User user, String category, String tag) throws Exception;

    Project getProjectById(Long projectId) throws Exception;



    void  deleteProject(Long projectId, Long userId)throws  Exception;

    Project updatedProject(Project updatedProject, Long id)throws  Exception;

    void addUserToProject(Long projectId, Long userId) throws Exception;
    void removeUserFromProject(Long projectId, Long userId) throws Exception;

    Chat getChatbyProjectId(Long projectId)throws  Exception;

    List<Project> searchProject(String keyword, User user) throws  Exception;
}
