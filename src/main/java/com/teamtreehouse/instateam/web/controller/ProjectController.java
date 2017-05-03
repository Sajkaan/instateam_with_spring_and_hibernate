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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    // New project
    @RequestMapping("/new_project")
    public String newProject(Model model) {

        if (!model.containsAttribute("project")) {
            model.addAttribute("project", new Project());
        }
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("button", "Save");
        model.addAttribute("action", "/new_project");
        model.addAttribute("cancel", "/");

        return "project/edit_project";
    }

    // Add a new project post method
    @RequestMapping(value = "/new_project", method = RequestMethod.POST)
    public String saveProject(@Valid Project project,
                              BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);
            redirectAttributes.addFlashAttribute("project", project);
            return "redirect:/new_project";
        }

        project.setDateCreated(Date.from(Instant.now()));
        projectService.save(project);

        return "redirect:/";
    }

    // Detail page for the specific project
    @RequestMapping("/projects/{id}")
    public String projectDetails(@PathVariable Long id, Model model) {

        Project project = projectService.findById(id);
        Map<Role, Collaborator> rolesAssignment = getRoleCollaboratorMap(project);

        model.addAttribute("project", project);
        model.addAttribute("rolesAssignment", rolesAssignment);

        return "project/project_detail";
    }

    // Selected project
    @RequestMapping("/projects/{id}/editProject")
    public String selectedProject(@PathVariable Long id, Model model) {

        if (!model.containsAttribute("project")) {
            model.addAttribute("project", projectService.findById(id));
        }

        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("action", String.format("/projects/%s", id));
        model.addAttribute("button", "Update");
        model.addAttribute("cancel", String.format("/projects/%s", id));

        return "project/edit_project";
    }

    // Edit project
    @RequestMapping(value = "/projects/{id}", method = RequestMethod.POST)
    public String editProject(@Valid Project project,
                                  @PathVariable Long id, BindingResult result) {

        if (result.hasErrors()) {
            return String.format("redirect:/projects/%s", id);
        }

        projectService.save(project);

        return "redirect:/projects/{id}";
    }

    // Edit Collaborators
    @RequestMapping("/projects/{id}/editCollaborators")
    public String editCollaborators(Model model, @PathVariable Long id) {

        Project project = projectService.findById(id);

        model.addAttribute("project", project);
        model.addAttribute("cancel", String.format("/projects/%s", id));

        return "project/project_collaborators";
    }

    // Assign Collaborators
    @RequestMapping(value = "/projects/{id}/editCollaborators", method = RequestMethod.POST)
    public String assignCollaborators(@PathVariable Long id, Project project,
                                      BindingResult result) {

        Project projectForSaving = projectService.findById(id);

        projectForSaving.setCollaborators(project.getCollaborators());

        projectService.save(projectForSaving);

        return String.format("redirect:/projects/{id}", project.getId());
    }

    public Map<Role, Collaborator> getRoleCollaboratorMap(Project project) {
        List<Role> rolesNeeded = project.getRolesNeeded();
        List<Collaborator> collaborators = project.getCollaborators();

        Map<Role, Collaborator> roleCollaborator = new LinkedHashMap<>();

        for (Role roleNeeded : rolesNeeded) {
            roleCollaborator.put(roleNeeded,
                    collaborators.stream()
                            .filter(collaborator -> collaborator.getRole().getId().equals(roleNeeded.getId()))
                            .findFirst()
                            .orElseGet(() -> {
                                Collaborator unassigned = new Collaborator();
                                unassigned.setName("Unassigned");
                                return unassigned;
                            }));
        }
        return roleCollaborator;
    }

}
