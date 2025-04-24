package com.example.metaprobackend.Dashboard;



import com.example.metaprobackend.organizator.Organizator;
import com.example.metaprobackend.organizator.OrganizatorRepository;
import com.example.metaprobackend.utilizator.Utilizator;
import com.example.metaprobackend.utilizator.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    private OrganizatorRepository organizatorRepository;

    @GetMapping("/utilizator")
    public ResponseEntity<?> getUtilizatorInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Utilizator utilizator = utilizatorRepository.findUtilizatorByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));

        return ResponseEntity.ok(utilizator);
    }

    @GetMapping("/organizator")
    public ResponseEntity<?> getOrganizatorInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Organizator organizator = organizatorRepository.findOrganizatorByEmail(email)
                .orElseThrow(() -> new RuntimeException("Organizatorul nu a fost găsit"));

        return ResponseEntity.ok(organizator);
    }
}
