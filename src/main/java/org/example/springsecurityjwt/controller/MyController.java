package org.example.springsecurityjwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/my")
public class MyController {

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Привет");
    }
}
