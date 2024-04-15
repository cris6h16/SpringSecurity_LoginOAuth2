package org.cris6h16.sprinsecuritylogin_oauth2.Controllers;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PrincipalController {
    // `OAuth2AuthenticationToken.getAuthorities()`  =-->  `hasRole('USER')` || `hasRole('ADMIN')`

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()") // logically if is PostAuthorize will be `[ROLE_ANONYMOUS]`
    public String index() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("      "+auth.getAuthorities());
        return "index";
    }


}
