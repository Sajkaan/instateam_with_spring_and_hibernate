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
public class CollaboratorController {

    @Autowired
    private CollaboratorService collaboratorService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/collaborators")
    public String collaborators(Model model) {

        model.addAttribute("collaborators", collaboratorService.findAll());
        model.addAttribute("project", null);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("action", "/collaborators/add");
        model.addAttribute("collaborator", new Collaborator());

        return "collaborator/collaborators";
    }

    @RequestMapping("/projects/{id}/collaborators")
    public String editCollaborators(Model model, @PathVariable Long id) {

        Project project = projectService.findById(id);

        List<Collaborator> collaboratorList = project.getCollaborators();

        model.addAttribute("collaborators", collaboratorList);
        model.addAttribute("project",project);
        model.addAttribute("roles", project.getRolesNeeded());
        model.addAttribute("action", String.format("/project/%s/collaborators/add", id);
        model.addAttribute("collaborator", new Collaborator());

        return "project/project_collaborators";
    }



}
