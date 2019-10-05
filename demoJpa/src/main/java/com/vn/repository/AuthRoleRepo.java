package com.vn.repository;

import com.vn.jpa.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("authRoleRepo")
public interface AuthRoleRepo extends JpaRepository<Role, Long> {

}
