package com.ts.user.Controller;


import com.netflix.discovery.converters.Auto;
import com.ts.user.Model.User;
import com.ts.user.Repository.UserRepository;
import com.ts.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //TESTING PURPOSES
    @GetMapping("/")
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity getUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PostMapping(path = "/add", headers = "Content-Type=application/json")
    public ResponseEntity addNewUser(@RequestBody User user) {

        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity addNewUser(@RequestParam String name, @RequestParam String designation, @RequestParam String office) {

        User user = new User();
        user.setName(name);
        user.setDesignation(designation);
        user.setOffice(office);
        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity editUser(@PathVariable long id, @RequestBody User user) {

        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent())
            return ResponseEntity.notFound().build();

        user.setId(id);

        userRepository.save(user);

        return new ResponseEntity("Updated User @{" + id + "} successfully", HttpStatus.OK);
    }

    
}
