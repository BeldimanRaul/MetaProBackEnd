package com.example.metaprobackend.Auth;


import com.example.metaprobackend.Jwt.JwtService;
import com.example.metaprobackend.organizator.OrganizatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/organizator/auth")
@RequiredArgsConstructor
public class OrganizatorAuthController {

    private final AuthenticationManager authenticationManager;
    private final OrganizatorService organizatorService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var organizator = organizatorService.loadUserByUsername(request.getEmail());
        var jwtToken = jwtService.generateToken(organizator);

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

}
