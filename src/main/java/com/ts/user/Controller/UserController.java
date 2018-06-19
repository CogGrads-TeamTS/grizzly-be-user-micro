package com.ts.user.Controller;


import com.netflix.discovery.converters.Auto;
import com.ts.user.Model.User;
import com.ts.user.Repository.UserRepository;
import com.ts.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //TESTING PURPOSES
    @GetMapping(value = "/user")
    Iterable<User> getAllUsers() {
        // retrieves all categories from the category microservice using feign

        return userRepository.findAll();
    }
}
