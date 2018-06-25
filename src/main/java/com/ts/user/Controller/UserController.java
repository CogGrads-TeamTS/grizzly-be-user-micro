package com.ts.user.Controller;


import com.ts.user.Model.ApplicationUser;
import com.ts.user.Repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser applicationUser) {
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        applicationUserRepository.save(applicationUser);
    }

    //TESTING PURPOSES
    @GetMapping("/")
    Iterable<ApplicationUser> getAllUsers() {
        return applicationUserRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity getUser(@PathVariable Long id) {
        Optional<ApplicationUser> user = applicationUserRepository.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PostMapping(path = "/add", headers = "Content-Type=application/json")
    public ResponseEntity addNewUser(@RequestBody ApplicationUser applicationUser) {

        applicationUserRepository.save(applicationUser);

        return new ResponseEntity<>(applicationUser, HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity addNewUser(@RequestParam String name, @RequestParam String designation, @RequestParam String office) {

        ApplicationUser applicationUser = new ApplicationUser();
//        applicationUser.setName(name);
//        applicationUser.setDesignation(designation);
//        applicationUser.setOffice(office);
        applicationUserRepository.save(applicationUser);

        return new ResponseEntity<>(applicationUser, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity editUser(@PathVariable long id, @RequestBody ApplicationUser applicationUser) {

        Optional<ApplicationUser> userOptional = applicationUserRepository.findById(id);
        if (!userOptional.isPresent())
            return ResponseEntity.notFound().build();

        applicationUser.setId(id);

        applicationUserRepository.save(applicationUser);

        return new ResponseEntity("Updated ApplicationUser @{" + id + "} successfully", HttpStatus.OK);
    }

    
}
