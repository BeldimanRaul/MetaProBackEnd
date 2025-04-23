package com.example.metaprobackend.Registration;

import com.example.metaprobackend.Email.EmailSender;
import com.example.metaprobackend.Registration.Token.ConfirmationToken;
import com.example.metaprobackend.Registration.Token.ConfirmationTokenService;
import com.example.metaprobackend.organizator.Organizator;
import com.example.metaprobackend.organizator.OrganizatorRepository;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ORegistrationService2 {

    private final OrganizatorRepository organizatorRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final EmailValidator emailValidator;

    public String register(com.example.metaprobackend.organizator.registration.ORegistrationRequest2 request) {
        boolean valid = emailValidator.test(request.getEmail());
        if (!valid) {
            throw new IllegalStateException("Email invalid");
        }

        if (organizatorRepository.findOrganizatorByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("Email deja folosit");
        }

        String hashedPass = bCryptPasswordEncoder.encode(request.getPassword());

        Organizator organizator = new Organizator(
                request.getUsername(),
                hashedPass,
                request.getEmail(),
                request.getDescriere(),
                request.getLinkBilete()
        );

        organizatorRepository.save(organizator);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                organizator
        );



        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = "http://localhost:8080/api/v1/organizator/registration/confirm?token=" + token;
        emailSender.send(request.getEmail(), buildEmail(request.getUsername(), link));

        return token;
    }
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("Token invalid"));

        if (confirmationToken.getConfirmat() != null) {
            throw new IllegalStateException("Contul este deja confirmat");
        }

        if (confirmationToken.getExpirat().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expirat");
        }

        confirmationToken.setConfirmat(LocalDateTime.now());
        confirmationTokenService.setConfirmat(token);

        Organizator organizator = confirmationToken.getOrganizator();
        if (organizator != null) {
            organizator.setEnabled(true);
            organizatorRepository.save(organizator);
            return "Contul de organizator a fost confirmat cu succes!";
        }

        throw new IllegalStateException("Nu s-a găsit organizator pentru acest token");
    }


    private String buildEmail(String name, String link) {
        return "<p>Salut " + name + ", confirmă contul tău de organizator: <a href=\"" + link + "\">Click aici</a></p>";
    }
}
