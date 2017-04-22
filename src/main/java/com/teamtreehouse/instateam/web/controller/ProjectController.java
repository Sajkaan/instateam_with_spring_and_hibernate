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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CollaboratorService collaboratorService;

    // Index page
    @RequestMapping("/")
    public String projectIndex(Model model) {

        List<Project> projects = projectService.findAll();

        model.addAttribute("projects", projects);

        return "index";
    }

    // Adding a new project
    @RequestMapping("/new_project")
    public String newProject(Model model, RedirectAttributes redirectAttributes) {

        if (!model.containsAttribute("project")) {
            Project project = new Project();
            model.addAttribute("project", project);
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("action", "/new_project");
            Long[] rolesID = new Long[roleService.findAll().toArray().length];
            model.addAttribute("rolesID[]", rolesID);

            return "project/edit_project";
        } else {
            return "redirect:/edit_project";
        }
    }

    // Adding a new project post method
    @RequestMapping(value = "/new_project", method = RequestMethod.POST)
    public String saveProject(@Valid Project project,
                              @RequestParam(value = "rolesID[]") Long[] rolesID,
                              BindingResult result, RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("project", project);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);
            return "redirect:/";
        }
        List<Role> roles = new ArrayList<>();

        if (rolesID != null) {
            for (Long id : rolesID) {
                roles.add(roleService.findById(id));
            }
        }
        project.setRolesNeeded(roles);

        project.setDateCreated(Date.from(Instant.now()));
        projectService.save(project);

        return "redirect:/";
    }

    // Detail page for the specific project
    @RequestMapping("/projects/{id}")
    public String projectDetails(@PathVariable Long id, Model model) {

        Project project = projectService.findById(id);
        List<Collaborator> collaboratorList = project.getCollaborators();
        List<Role> roles = project.getRolesNeeded();
        model.addAttribute("project", project);
        model.addAttribute("collaborators", collaboratorList);

        return "project/project_detail";
    }

    // Edit specific project
    @RequestMapping("/projects/{id}/editProject")
    public String editProject(@PathVariable Long id, Model model) {

        if (!model.containsAttribute("project")) {
            Project project = projectService.findById(id);
            model.addAttribute("project", project );
            model.addAttribute("collaborators", project.getCollaborators());
        }
        // TODO: SG Create attribute to make button have edit text and one add in the new project
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("action", String.format("/projects/%s/edit", id));

        return "project/edit_project";
    }

    @RequestMapping("projects/{id}/editCollaborators")
    public String editCollaborators(Model model,@PathVariable Long id) {

        Project project = projectService.findById(id);
        List<Collaborator> collaborators = collaboratorService.findAll();
        List<Role> rolesNeeded = project.getRolesNeeded();

        model.addAttribute("project", project);
        model.addAttribute("collaborators", collaborators);
        model.addAttribute("rolesNeeded",rolesNeeded);


        return "project/project_collaborators";
    }
}
