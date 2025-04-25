package com.example.metaprobackend.Registration;
import org.springframework.validation.Validator;
import com.example.metaprobackend.Email.EmailSender;
import com.example.metaprobackend.Registration.Token.ConfirmationToken;
import com.example.metaprobackend.Registration.Token.ConfirmationTokenService;
import com.example.metaprobackend.organizator.Organizator;
import com.example.metaprobackend.organizator.OrganizatorService;
import com.example.metaprobackend.utilizator.Utilizator;
import com.example.metaprobackend.utilizator.UtilizatorService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final UtilizatorService utilizatorService;

    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;


    public String register(RegistrationRequest request) {

        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("Email  rău de tot");
        }

        String token = utilizatorService.signUpUser(
                new Utilizator(
                        request.getUsername(),
                        request.getPassword(),
                        request.getEmail(),
                        request.getDob(),
                        UserRole.USER
                )

        );




        String link ="http://localhost:8080/api/v1/utilizator/registration/confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                buildEmail(request.getUsername(),link));
        return token;
    }



    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token nu a fost gasit"));
        if (confirmationToken.getConfirmat() != null) {

            return "mail confirmat deja";
        }
        LocalDateTime expirat = confirmationToken.getExpirat();
        if (expirat.isBefore(LocalDateTime.now())) {
            return "token expirat ";
        }
        confirmationToken.setConfirmat(LocalDateTime.now());

        utilizatorService.enableUtilizator(
                confirmationToken.getUtilizator().getEmail());
        return "confirmat";

    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;padding:0;color:#0b0c0c;background-color:#f4f4f4\">" +
                "<table role=\"presentation\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">" +
                "  <tr>" +
                "    <td align=\"center\" style=\"padding: 40px 0; background-color: #0b0c0c;\">" +
                "      <h1 style=\"color: white; font-size: 28px; margin: 0;\">Confirmă adresa ta de email</h1>" +
                "    </td>" +
                "  </tr>" +
                "  <tr>" +
                "    <td align=\"center\">" +
                "      <table role=\"presentation\" width=\"100%\" style=\"max-width: 600px; background-color: white; padding: 40px; border-radius: 8px;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">" +
                "        <tr>" +
                "          <td style=\"font-size: 18px; line-height: 1.6; color: #0b0c0c;\">" +
                "            <p style=\"margin: 0 0 20px 0;\">Salut <strong>" + name + "</strong>,</p>" +
                "            <p style=\"margin: 0 0 20px 0;\">Îți mulțumim pentru înregistrare. Te rugăm să dai click pe butonul de mai jos pentru a-ți activa contul:</p>" +
                "            <p style=\"margin: 20px 0;\">" +
                "              <a href=\"" + link + "\" style=\"background-color: #1D70B8; color: white; padding: 12px 24px; text-decoration: none; border-radius: 5px; display: inline-block;\">Activează contul</a>" +
                "            </p>" +
                "            <p style=\"margin: 20px 0;\">Link-ul este valabil timp de 15 minute.</p>" +
                "            <p style=\"margin: 0;\">Pe curând,</p>" +
                "            <p style=\"margin: 0;\"><em>Echipa MetaProEvents</em></p>" +
                "          </td>" +
                "        </tr>" +
                "      </table>" +
                "    </td>" +
                "  </tr>" +
                "</table>" +
                "</div>";
    }

}

