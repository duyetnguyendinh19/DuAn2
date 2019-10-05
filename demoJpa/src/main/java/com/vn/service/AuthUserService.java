package com.vn.service;

import com.vn.jpa.AuthUser;
import com.vn.model.AuthUserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthUserService  {

    AuthUser findByUsername(String var);

    AuthUser findByEmail(String emamail);

    AuthUser findOne(Long id);

    Page<AuthUserModel> listUsers(String username, String email, Pageable pageable);

    AuthUser create(AuthUser authUser);

    AuthUser update(AuthUser authUser);

    void delete(AuthUser authUser);

    List<Long> findRolesByUserId(Long userId);
}
