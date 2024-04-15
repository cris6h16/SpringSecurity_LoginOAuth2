package org.cris6h16.sprinsecuritylogin_oauth2.Controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class Security {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userAuthoritiesMapper(this.userAuthoritiesMapper())
			    ));
        return http.build();
    }

    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach(authority -> {
                if (authority instanceof OidcUserAuthority) { // OpenID Connect
                    OidcUserAuthority oidcUserAuthority = (OidcUserAuthority)authority;

                    OidcIdToken idToken = oidcUserAuthority.getIdToken();
                    OidcUserInfo userInfo = oidcUserAuthority.getUserInfo(); // may be null

                    /*  `idToken.getClaims().toString()`  =-->

                    at_hash : A6ul88U-PFJ5CWieu0lcHA
                    sub : 107427846149272362276
                    email_verified : true
                    iss : https://accounts.google.com
                    given_name : Cristian
                    nonce : BC2J15wob44Zk7d-EL2-R9pG47K_T0Ikc8YsK4WfFgM
                    picture : https://lh3.googleusercontent.com/a/ACg8ocI8x0kyBshENddHIKulYFZsh8PuoYbwBiRPmE6MgQUxvGbp4dYJ=s96-c
                    aud : [904802186889-vgk15e51ooig776vie6jbls61jgj8rsf.apps.googleusercontent.com]
                    azp : 904802186889-vgk15e51ooig776vie6jbls61jgj8rsf.apps.googleusercontent.com
                    name : Cristian Herrera
                    exp : 2024-04-14T23:19:35Z
                    family_name : Herrera
                    iat : 2024-04-14T22:19:35Z
                    email : cristianmherrera21@gmail.com
                     */


                    // Map the claims found in idToken and/or userInfo
                    // to one or more GrantedAuthority's and add it to mappedAuthorities

                    idToken.getClaims().forEach((k, v) -> {
                        if (k.equals("email")) {
                            // we should load it from the database..... but for now we will just add with the role USER
                            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER")); //SimpleGrantedAuthority is a simple String, also exists Complexes versions
                        }
                    });



                } else if (OAuth2UserAuthority.class.isInstance(authority)) {
                    OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority)authority;

                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

                    // Map the attributes found in userAttributes
                    // to one or more GrantedAuthority's and add it to mappedAuthorities

                    /*
                    SAME AS ABOVE
                     */

                }
            });

            return mappedAuthorities;
        };
    }
}
