package com.teamtreehouse.instateam.converter;

import com.teamtreehouse.instateam.dao.RoleDao;
import com.teamtreehouse.instateam.dao.RoleDaoImpl;
import com.teamtreehouse.instateam.model.Role;
import com.teamtreehouse.instateam.service.RoleService;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringRoleConverter implements Converter<String, Role> {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role convert (String id) {
        return roleDao.findById(Long.valueOf(id));
    }
}
