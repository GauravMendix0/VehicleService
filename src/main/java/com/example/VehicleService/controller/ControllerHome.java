package com.example.VehicleService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")  // Optional, helps group future routes if needed
public class ControllerHome {

    @GetMapping
    public ResponseEntity<String> home() {
        String message = """
        Welcome to Vehicle Service API!<br><br>
        1. Get all Owners data    - /owners <br>
        2. Get all Vehicles data  - /vehicles <br>
        3. Get all Services data  - /services <br>
        """;
        return ResponseEntity.ok().header("Content-Type", "text/html").body(message);
    }

}
