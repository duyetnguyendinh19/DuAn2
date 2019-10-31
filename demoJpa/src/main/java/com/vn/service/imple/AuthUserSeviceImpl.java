package com.vn.service.imple;

import com.vn.jpa.AuthUser;
import com.vn.jpa.Role;
import com.vn.model.AuthUserModel;
import com.vn.repository.AuthUserRepo;
import com.vn.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("authUserService")
public class AuthUserSeviceImpl implements AuthUserService {

    @Resource
    private AuthUserRepo authUserRepo;

    @Override
    public AuthUser findByUsername(String var) {
        return authUserRepo.findByUserName(var);
    }

    @Override
    public AuthUser findByEmail(String email) {
        return authUserRepo.findByEmail(email);
    }

    @Override
    public AuthUser findByUserName(String username) {
        return authUserRepo.findByUserName(username);
    }

    @Override
    public AuthUser findOne(Long id) {
        return authUserRepo.findOne(id);
    }

    @Override
    public Page<AuthUserModel> listUsers(String username, String email, Pageable pageable) {
        Page<Object[]> pageObjects = authUserRepo.listUsers(username, email, pageable);
        List<AuthUserModel> listResponse = new ArrayList<>();
        for (Object[] eachObj : pageObjects) {
            Long id = (Long) eachObj[0];
            String user_name = (String) eachObj[1];
            String fullName = (String) eachObj[2];
            String e_mail = (String) eachObj[3];
            Byte status = (Byte) eachObj[4];
            Byte user_type = (Byte) eachObj[5];
            Date createdDate = (Date) eachObj[6];
            AuthUserModel eachAuthUser = new AuthUserModel(id, user_name, fullName, e_mail, status, user_type, createdDate);
            listResponse.add(eachAuthUser);
        }
        PageImpl<AuthUserModel> pageResponse = new PageImpl<>(listResponse, pageable, pageObjects.getTotalElements());

        return pageResponse;
    }

    @Override
    public Page<AuthUserModel> listUserByType(String username, String email,byte type, Pageable pageable) {
        Page<Object[]> pageObjects = authUserRepo.listUserByType(username, email,type, pageable);
        List<AuthUserModel> listResponse = new ArrayList<>();
        for (Object[] eachObj : pageObjects) {
            Long id = (Long) eachObj[0];
            String user_name = (String) eachObj[1];
            String fullName = (String) eachObj[2];
            String e_mail = (String) eachObj[3];
            Byte status = (Byte) eachObj[4];
            Date createdDate = (Date) eachObj[5];
            AuthUserModel eachAuthUser = new AuthUserModel(id, user_name, fullName, e_mail, status, createdDate);
            listResponse.add(eachAuthUser);
        }
        PageImpl<AuthUserModel> pageResponse = new PageImpl<>(listResponse, pageable, pageObjects.getTotalElements());

        return pageResponse;
    }

    @Override
    public AuthUser create(AuthUser authUser) {
        return authUserRepo.save(authUser);
    }

    @Override
    public AuthUser update(AuthUser authUser) {
        return authUserRepo.save(authUser);
    }
    @Override
    public void delete(AuthUser authUser){
         authUserRepo.delete(authUser);
    }

    @Override
    public List<Long> findRolesByUserId(Long userId) {
        AuthUser user = authUserRepo.findOne(userId);
        List<Role> userRoles = user.getAuthRoles();
        List<Long> userRoleids = new ArrayList<>();
        for (Role userRole : userRoles) {
            userRoleids.add(userRole.getId());
        }
        return userRoleids;
    }

	@Override
	public AuthUser findByUserNameANDPassword(String user, String pass) {
		return authUserRepo.findByUserNameAndPassword(user, pass);
	}

    @Override
    public boolean checkExistByUserName(String user) {
        boolean result = false;
        Long id = authUserRepo.checkExistByUserName(user);
        if (id != null && id > 0) {
            result = true;
        }
        return result;
    }
}
