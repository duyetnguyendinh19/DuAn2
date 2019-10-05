package com.vn.service.imple;

import com.vn.jpa.AuthUser;
import com.vn.repository.AuthUserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;

@Service("userDetailsService")
public class AuthUserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(AuthUserDetailsServiceImpl.class);
    private final String cacheName = "authUsers";

    @Resource
    private AuthUserRepo authUserRepo;

    public AuthUserDetailsServiceImpl() {
    }

    @Transactional(readOnly = true)
    @Cacheable({"authUsers"})
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = this.authUserRepo.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Oops!");
        } else {
            Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) user.getAuthorities();
            logger.debug("authorities[{}]", authorities);
            return user;
        }
    }
}
