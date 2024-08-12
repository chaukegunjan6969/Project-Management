package com.Aadyy.projectManagement.Modal;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    //CHecked in 1 hr ///


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    private  String fullname;
    private  String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private  String password;

    private  int ProjectSize;

    @JsonIgnore
    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
    private List<Issue> assignedIssue = new ArrayList<>();




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getProjectSize() {
        return ProjectSize;
    }

    public void setProjectSize(int projectSize) {
        ProjectSize = projectSize;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }





}
