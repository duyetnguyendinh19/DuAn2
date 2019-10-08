package com.vn.config.sercurity;

import com.vn.jpa.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AppAuthenticationSuccessHanding implements AuthenticationSuccessHandler {
    private static final Logger LOG = LoggerFactory.getLogger(AppAuthenticationSuccessHanding.class);
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        String targetLink = "/";
        if (authUser.getUserType() == 0){
            targetLink = "/admin/dashboard.html";
        }else if(authUser.getUserType() == 1){
            targetLink = "/user/list.html";
        }
        if(authUser.getUserType() == 2){
            targetLink = "/error/403.html?accessDinied=true";
        }
        if(authUser.getStatus() == 0){
            targetLink = request.getContextPath() + "/error";
            response.sendRedirect(targetLink);
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        }else{
            if(response.isCommitted()){
                LOG.warn("Response has already been committed. Unable to redirect to " + targetLink);
            }else{
                request.getSession().setAttribute("dashboardLink", targetLink);
                request.getSession().setAttribute("account", ((AuthUser) authentication.getPrincipal()).getUserName());
                redirectStrategy.sendRedirect(request,response,targetLink);
            }
        }
    }
}
