package com.teamtreehouse.instateam.web.controller;

import com.teamtreehouse.instateam.model.Collaborator;
import com.teamtreehouse.instateam.model.Role;
import com.teamtreehouse.instateam.service.CollaboratorService;
import com.teamtreehouse.instateam.service.ProjectService;
import com.teamtreehouse.instateam.service.RoleService;
import com.teamtreehouse.instateam.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class CollaboratorController {

    @Autowired
    private CollaboratorService collaboratorService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RoleService roleService;

    // Collaborator list
    @RequestMapping("/collaborators")
    public String collaborators(Model model) {

        model.addAttribute("collaborators", collaboratorService.findAll());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("action", "/collaborators");
        model.addAttribute("collaborator", new Collaborator());

        return "collaborator/collaborators";
    }

    // Add Collaborator
    @RequestMapping(value = "/collaborators", method = RequestMethod.POST)
    public String addCollaborator(@Valid Collaborator collaborator, BindingResult result,RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Invalid input", FlashMessage.Status.FAILURE));
            return "redirect:/collaborators";
        }
        Role role = roleService.findById(collaborator.getRole().getId());
        collaborator.setRole(role);
        collaboratorService.save(collaborator);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Collaborator added.", FlashMessage.Status.SUCCESS));

        return "redirect:/collaborators";
    }

    // Selected Collaborator
    @RequestMapping("/collaborators/{id}/edit")
    public String selectedCollaborator(@PathVariable Long id, Model model) {
        model.addAttribute("collaborator", collaboratorService.findById(id));
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("action", String.format("/collaborators/%s/edit", id));

        return "collaborator/detail";
    }

    // Edit Collaborator
    @RequestMapping(value = "/collaborators/{id}/edit", method = RequestMethod.POST)
    public String editCollaborator(@Valid Collaborator collaborator, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Invalid input", FlashMessage.Status.FAILURE));
            return "redirect:/collaborators";
        }

        collaboratorService.save(collaborator);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Successfully updated collaborator", FlashMessage.Status.SUCCESS));

        return "redirect:/collaborators";
    }

}
