package com.example.metaprobackend.Registration;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/organizator/registration")
@AllArgsConstructor
public class ORegistrationController2 {

    private final ORegistrationService2 ORegistrationService2;

    @PostMapping
    public String register(@RequestBody com.example.metaprobackend.organizator.registration.ORegistrationRequest2 request) {
        return ORegistrationService2.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return ORegistrationService2.confirmToken(token);
    }
}
