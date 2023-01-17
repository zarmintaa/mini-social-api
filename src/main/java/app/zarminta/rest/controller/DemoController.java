package app.zarminta.rest.controller;

import app.zarminta.rest.service.EntityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DemoController {
    private EntityService entityService;
    @GetMapping("/demo-controller")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @GetMapping("/test")
    public Object getObject(){
        return entityService.getUserLogged();
    }
}
