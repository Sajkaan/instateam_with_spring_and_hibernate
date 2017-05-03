package com.teamtreehouse.instateam.web.controller;

import com.teamtreehouse.instateam.model.Collaborator;
import com.teamtreehouse.instateam.model.Project;
import com.teamtreehouse.instateam.model.Role;
import com.teamtreehouse.instateam.service.CollaboratorService;
import com.teamtreehouse.instateam.service.ProjectService;
import com.teamtreehouse.instateam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
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
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("action", "/collaborators");
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
        model.addAttribute("action", String.format("/collaborators/add", id));
        model.addAttribute("collaborator", new Collaborator());

        return "project/project_collaborators";
    }

    @RequestMapping(value = "/collaborators",  method = RequestMethod.POST)
    public String addCollaborator(@Valid Collaborator collaborator, BindingResult result) {

        if (result.hasErrors()) {

            // TODO : SG ADD FLASH MESSAGE
            return "redirect:/collaborators";
        }
        Role role = roleService.findById(collaborator.getRole().getId());
        collaborator.setRole(role);
        collaboratorService.save(collaborator);

        return "redirect:/collaborators";
    }

    @RequestMapping("/collaborators/{id}/edit")
    public String editCollaborator(@PathVariable Long id,Model model) {
        model.addAttribute("collaborator",collaboratorService.findById(id));
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("action", String.format("/collaborators/%s/edit", id));

        return "collaborator/detail";
    }

    @RequestMapping(value = "/collaborators/{id}/edit", method = RequestMethod.POST)
    public String changeCollaborator(@Valid Collaborator collaborator, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/collaborators";
        }

        collaboratorService.save(collaborator);

        return "redirect:/collaborators";
    }



}
