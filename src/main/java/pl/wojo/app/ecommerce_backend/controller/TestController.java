package pl.wojo.app.ecommerce_backend.controller;

import org.springframework.web.bind.annotation.RestController;

import pl.wojo.app.ecommerce_backend.api_model.RegistrationBody;
import pl.wojo.app.ecommerce_backend.model.LocalUser;
import pl.wojo.app.ecommerce_backend.service.UserService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class TestController {
    
    private UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/persons/all")
    public List<LocalUser> getMethodName() {
        return userService.findAll();
    }

    @PostMapping("/persons/create")
    public ResponseEntity<LocalUser> postMethodName(@RequestBody RegistrationBody user) {
        
        LocalUser savedUser = userService.register(user);

        return ResponseEntity.ok(savedUser);
    }
    
    
}
