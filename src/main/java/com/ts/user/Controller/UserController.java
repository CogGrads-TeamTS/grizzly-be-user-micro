package com.ts.user.Controller;


import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.UserInfo;
import com.auth0.json.mgmt.users.User;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.net.Request;
import com.auth0.spring.security.api.authentication.AuthenticationJsonWebToken;
import com.auth0.spring.security.api.authentication.JwtAuthentication;
import com.ts.user.Model.ApplicationUser;
import com.ts.user.Repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private ManagementAPI managementAPI;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser applicationUser) {
        applicationUserRepository.save(applicationUser);
    }

    @GetMapping("/current")

    ResponseEntity current(Authentication auth, Principal principal) {
        //System.out.println(principal.getName());

        DecodedJWT details = (DecodedJWT) auth.getDetails();
        String accessToken = details.getToken();

        System.out.println(auth.getAuthorities());

        return new ResponseEntity<>(auth, HttpStatus.OK);
    }

    //TESTING PURPOSES
    @GetMapping("/")
    ResponseEntity currentUser(Authentication auth, Principal principal) {
        //System.out.println(principal.getName());

        // DecodedJWT details = (DecodedJWT) auth.getDetails();
        // String accessToken = details.getToken();

        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity getUser(Principal principal, @PathVariable Long id) {
        Optional<ApplicationUser> user = applicationUserRepository.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
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
