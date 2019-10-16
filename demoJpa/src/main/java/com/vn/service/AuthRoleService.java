package com.vn.service;

import com.vn.jpa.AuthUser;
import com.vn.jpa.Role;

import java.util.List;

public interface AuthRoleService {

    List<Role> findAll();

    Role findOne(Long id);
    
}
