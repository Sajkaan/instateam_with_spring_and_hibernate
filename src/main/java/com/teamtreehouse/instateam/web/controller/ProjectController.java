package com.teamtreehouse.instateam.web.controller;

import com.teamtreehouse.instateam.model.Project;
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
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CollaboratorService collaboratorService;

    // Index page
    @RequestMapping("/")
    public String projectList(Model model) {

        List<Project> projects = projectService.findAll();

        model.addAttribute("projects", projects);

        return "index";
    }

    // Adding a new project
    @RequestMapping("/new_project")
    public String newProject(Model model) {

        if (!model.containsAttribute("project")) {
            model.addAttribute("project", new Project());
        }

        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("action", "/new_project");

        return "project/edit_project";
    }

    // Adding a new project post method
    @RequestMapping(value = "/new_project", method = RequestMethod.POST)
    public String addProject(@Valid Project project, BindingResult result){

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
        } else {
            projectService.save(project);
        }
        return "redirect:/";
    }

    // Detail page for the specific project
    @RequestMapping("/projects/{id}")
    public String projectDetails(@PathVariable Long id, Model model) {

        Project project = projectService.findById(id);
        model.addAttribute("project", project);

        return "project/project_detail";
    }

    // Edit specific project
    @RequestMapping("/projects/{id}/edit")
    public String editProject(@PathVariable Long id, Model model) {

        Project project = projectService.findById(id);

        if (!model.containsAttribute("project")) {
            model.addAttribute("project", project );
            System.out.println("Hello from the other side");
        }

        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("action", String.format("/projects/%s/edit", id));

        return "project/edit_project";
    }
}
