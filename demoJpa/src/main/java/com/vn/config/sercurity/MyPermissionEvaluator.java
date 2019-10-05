package com.vn.config.sercurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class MyPermissionEvaluator implements PermissionEvaluator {
    private Logger LOG = LoggerFactory.getLogger(MyPermissionEvaluator.class);

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        this.LOG.debug("target: {}, permission: {}", targetDomainObject, permission);
        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        this.LOG.debug("target: {}, permission: {}", targetType, permission);
        return false;
    }
}
