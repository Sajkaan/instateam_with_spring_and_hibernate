package com.teamtreehouse.instateam.web.controller;

import com.teamtreehouse.instateam.model.Collaborator;
import com.teamtreehouse.instateam.model.Project;
import com.teamtreehouse.instateam.service.CollaboratorService;
import com.teamtreehouse.instateam.service.ProjectService;
import com.teamtreehouse.instateam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CollaboratorService collaboratorService;

    @RequestMapping("/")
    public String projectList(Model model) {

        List<Project> projects = projectService.findAll();

        model.addAttribute("projects", projects);

        return "index";
    }

    @RequestMapping("/new_project")
    public String newProject(Model model) {
        model.addAttribute("project", new Project());

        return "project/edit_project";
    }

    @RequestMapping("/projects/{id}")
    public String project(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id);
        List<Collaborator> collaborators = project.getCollaborators();

        System.out.println(collaborators.get(0));
        System.out.println(collaborators.get(1));
        model.addAttribute("collaborators", collaborators);
        model.addAttribute("project", project);



        return "project/project_detail";
    }

}
