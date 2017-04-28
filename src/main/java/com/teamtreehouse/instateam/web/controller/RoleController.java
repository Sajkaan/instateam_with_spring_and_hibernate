package com.teamtreehouse.instateam.web.controller;

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
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CollaboratorService collaboratorService;

    @RequestMapping("/roles")
    public String listRoles (Model model) {

        List<Role> roleList = roleService.findAll();

        model.addAttribute("roles", roleList);

        if (!model.containsAttribute("role")) {
            model.addAttribute("role", new Role());
        }

        return "role/roles";
    }

    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public String addRole(@Valid Role role, BindingResult result) {
        if (result.hasErrors()) {
            // TODO : SG Add flash message
            return "redirect:/roles";
        }
        roleService.save(role);

        return "redirect:/roles";
    }

    @RequestMapping("/roles/{id}/edit")
    public String editRole(@PathVariable Long id, Model model){

        model.addAttribute("role",roleService.findById(id));
        model.addAttribute("action", String.format("/roles/%s/edit", id));
        model.addAttribute("delete", String.format("/roles/%s/delete", id));
        return "role/detail";
    }

    @RequestMapping(value = "/roles/{id}/edit", method = RequestMethod.POST)
    public String changeRole(@Valid Role role, BindingResult result) {
        if (result.hasErrors()){
            return "redirect:/roles";
        }

        roleService.save(role);

        return "redirect:/roles";
    }

    @RequestMapping(value = "/roles/{id}/delete")
    public String deleteRole(@PathVariable Long id) {
        Role role = roleService.findById(id);

        // TODO: SG Must set collaborators with specific role to null or delete them too

        roleService.delete(role);

        return "redirect:/roles";
    }

}
