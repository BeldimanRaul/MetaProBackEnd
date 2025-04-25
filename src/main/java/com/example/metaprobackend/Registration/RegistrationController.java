package com.example.metaprobackend.Registration;
import org.springframework.validation.Validator;
import org.springframework.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/utilizator/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        String token = registrationService.register(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        String result = registrationService.confirmToken(token);
        return ResponseEntity.ok(result);
    }
}