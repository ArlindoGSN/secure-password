package dev.arlindo.securepassword.controller;

import dev.arlindo.securepassword.service.PasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PasswordController {

    private PasswordService service;

    public PasswordController(PasswordService service) {
        this.service = service;
    }

    @PostMapping(path = "/password")
    public ResponseEntity<String> verifyPassword(@RequestBody Map<String, String> request) {
        String password = request.get("password");

        if (password == null || password.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is required");
        }

        String response = service.verifyPassword(password);

        if ("Password is valid".equals(response)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


}
