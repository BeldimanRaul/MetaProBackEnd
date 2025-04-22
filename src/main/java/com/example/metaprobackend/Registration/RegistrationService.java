package com.example.metaprobackend.Registration;

import com.example.metaprobackend.utilizator.Utilizator;
import com.example.metaprobackend.utilizator.UtilizatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final UtilizatorService utilizatorService;



    public String register(RegistrationRequest request) {

        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("Email rău rău de tot");
        }

        Utilizator utilizator = new Utilizator(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getDob(),
                UserRole.USER
        );




        return utilizatorService.signUpUser(utilizator);
    }

}
