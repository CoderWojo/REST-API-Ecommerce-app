package pl.wojo.app.ecommerce_backend.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class TestController {
    
    @GetMapping("test")
    public String getMethodName() {
        return new String("TO JEST TEST");
    }
    
}
