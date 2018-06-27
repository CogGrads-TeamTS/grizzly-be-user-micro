package com.ts.user;


import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.AuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
//@EnableFeignClients
//@EnableSwagger2

public class UserApplication {

    @Value("${auth0.clientId}")
    private String clientId;

    @Value("${auth0.clientSecret}")
    private String clientSecret;

    @Value("${auth0.audience}")
    private String audience;

    @Value("${auth0.domain}")
    private String domain;

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    AuthAPI authenticationAPIClient() {
        return new AuthAPI(domain, clientId, clientSecret);
    }

    @Bean
    @Scope(value = "prototype")
    ManagementAPI managementAPI() {
        // get management token
        AuthRequest request = authenticationAPIClient().requestToken(audience);
        try {
            TokenHolder holder = request.execute();
            return new ManagementAPI(domain, holder.getAccessToken());
        } catch (APIException exception) {
            // api error
            exception.printStackTrace();
        } catch (Auth0Exception exception) {
            // request error
            exception.printStackTrace();
        }
        return null;
    }
}
