package com.teamtreehouse.instateam.web.controller;

import com.teamtreehouse.instateam.model.Role;
import com.teamtreehouse.instateam.service.CollaboratorService;
import com.teamtreehouse.instateam.service.ProjectService;
import com.teamtreehouse.instateam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

        roleService.save(role);

        return "redirect:/roles";
    }

}
