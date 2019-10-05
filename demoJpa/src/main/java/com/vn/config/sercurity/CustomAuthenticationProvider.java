package com.vn.config.sercurity;

import com.vn.jpa.AuthUser;
import com.vn.jpa.Role;
import com.vn.service.AuthUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        try {
            AuthUser authUser = this.authUserService.findByUsername(username);
            if (authUser == null) {
                return null;
            } else if (!this.passwordEncoder.matches(password, authUser.getPassword())) {
                throw new BadCredentialsException("Invalid username or password");
            } else {
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                List<Role> authUsers = authUser.getAuthRoles();
                Iterator roleIterator = authUsers.iterator();
                while (roleIterator.hasNext()) {
                    Role role = (Role) roleIterator.next();
                    grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
                }
                Authentication authentication1 = new UsernamePasswordAuthenticationToken(authUser, "", grantedAuthorities);
                return authentication1;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
