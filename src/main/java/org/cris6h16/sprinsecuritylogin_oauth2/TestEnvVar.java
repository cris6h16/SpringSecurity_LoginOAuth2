package org.cris6h16.sprinsecuritylogin_oauth2;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class TestEnvVar {
//    @Value("${my.env.var}")
//    private static String hola;

    public TestEnvVar(Environment env) {
//        System.out.println(hola);//null
        System.out.println("component my.env.var: " + env.getProperty("my.env.var"));
        System.out.println("component client-id: " + env.getProperty(
                "spring.security.oauth2.client.registration.google.client-id"
        ));
        System.out.println("component client-secret: " + env.getProperty(
                "spring.security.oauth2.client.registration.google.client-secret"
        ));}
}
