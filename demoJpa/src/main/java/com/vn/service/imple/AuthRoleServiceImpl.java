package com.vn.service.imple;

import com.vn.jpa.Role;
import com.vn.repository.AuthRoleRepo;
import com.vn.service.AuthRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service(value = "authRoleService")
@Transactional
public class AuthRoleServiceImpl implements AuthRoleService {

    @Resource
    private AuthRoleRepo authRoleRepo;

    @Override
    public List<Role> findAll() {
        return authRoleRepo.findAll();
    }

    @Override
    public Role findOne(Long id) {
        return authRoleRepo.findOne(id);
    }
}
