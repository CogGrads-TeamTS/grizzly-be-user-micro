package com.ts.user.Web;

import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.users.User;
import com.auth0.net.Request;
import com.ts.user.Model.ApplicationUser;
import com.ts.user.Repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// intercepts http requests, used to check if user exists in database
public class NewUserInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private ManagementAPI managementAPI;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!request.getMethod().equalsIgnoreCase("options")) {
            checkNewUser(request.getUserPrincipal().getName());
        }
        return super.preHandle(request, response, handler);
    }

    void checkNewUser(String username) {
        // checks if user exists in database
        // if user doesnt exist, fetches user info from Auth0 and creates user in database
        ApplicationUser user = applicationUserRepository.findByUsername(username);
        if (user == null) {
            Request<User> userDetailsRequest = managementAPI.users().get(username, null);
            try {
                User userDetails = userDetailsRequest.execute();
                ApplicationUser newUser = new ApplicationUser();
                newUser.setUsername(username);
                newUser.setName(userDetails.getName());
                newUser.setEmail(userDetails.getEmail());
                applicationUserRepository.save(newUser);
            } catch (APIException exception) {
                // api error
            } catch (Auth0Exception exception) {
                // request error
            }
        }
    }
}
