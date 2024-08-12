package com.Aadyy.projectManagement.services;

import com.Aadyy.projectManagement.Modal.Chat;
import com.Aadyy.projectManagement.Modal.Project;
import com.Aadyy.projectManagement.Modal.User;
import com.Aadyy.projectManagement.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProjectServiceImpl implements ProjectService {


    @Autowired
    private  ProjectRepository projectRepository;

    @Autowired
    private  UserService userService;

    @Autowired
    private ChatService chatService;

    ////////Checked//////////

    @Override
    public Project createproject(Project project, User user) throws Exception {

        Project createdProject = new Project();

        createdProject.setOwner(user);
        createdProject.setTags(project.getTags());
        createdProject.setName(project.getName());
        createdProject.setCategory(project.getCategory());
        createdProject.setDescription(project.getDescription());
        createdProject.getTeam().add(user);

        Project savedproject = projectRepository.save(createdProject);

        Chat chat = new Chat();
        chat.setProject(savedproject);

        Chat projectChat = chatService.createchat(chat);

        savedproject.setChat(projectChat);

        return savedproject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {

        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user,user);

        if(category!=null)
        {
            projects = projects.stream().filter(project -> project.getCategory().equals(category))
                    .collect(Collectors.toList());
        }

        if(tag!=null)
        {
            projects = projects.stream().filter(project -> project.getTags().contains(tag))
                    .collect(Collectors.toList());
        }
        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {

        Optional<Project> project = projectRepository.findById(projectId);
          if(project.isEmpty())
          {
              throw  new Exception("NO project available");
          }
        return  project.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {

//        getProjectById(projectId);
        projectRepository.deleteById(projectId);

    }

    @Override
    public Project updatedProject(Project updatedProject, Long id) throws Exception {

        Project project = getProjectById(id);

        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());


        return projectRepository.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {

         Project project = getProjectById(projectId);
         User user = userService.findUserById(userId);

        if(project==null)
        {
            throw new Exception("Invalid project vId");
        }

         if(!project.getTeam().contains(user))
         {
             project.getChat().getUsers().add(user);
             project.getTeam().add(user);
         }

         projectRepository.save(project);

    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {

        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if(project==null)
        {
            throw new Exception("Invalid project vId");
        }

        if(project.getTeam().contains(user))
        {
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }

        projectRepository.save(project);


    }

    @Override
    public Chat getChatbyProjectId(Long projectId) throws Exception {

        Project project = getProjectById(projectId);


        return project.getChat();
    }

    @Override
    public List<Project> searchProject(String keyword, User user) throws  Exception{


        List<Project> projects = projectRepository.findByNameContainingAndTeamContains(keyword,user);

        return projects;
    }

}
