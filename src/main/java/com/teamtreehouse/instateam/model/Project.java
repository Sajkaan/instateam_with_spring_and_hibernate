package com.teamtreehouse.instateam.model;

import com.teamtreehouse.instateam.web.ProjectStatus;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectID")
    private Long id;

    private String projectName;

    private String projectDescription;

    @ManyToMany
    private Role rolesNeeded;

    @OneToMany(mappedBy = "collaborators")
    private List<Collaborator> collaborators;

    @Enumerated
    private ProjectStatus projectStatus;


    public Project() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Role getRolesNeeded() {
        return rolesNeeded;
    }

    public void setRolesNeeded(Role rolesNeeded) {
        this.rolesNeeded = rolesNeeded;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }
}
