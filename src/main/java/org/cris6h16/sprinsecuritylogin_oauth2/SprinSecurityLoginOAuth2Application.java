package org.cris6h16.sprinsecuritylogin_oauth2;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SprinSecurityLoginOAuth2Application {



    public static void main(String[] args) {
        SpringApplication.run(SprinSecurityLoginOAuth2Application.class, args);
    }

}
