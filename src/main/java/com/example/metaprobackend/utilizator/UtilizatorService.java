package com.example.metaprobackend.utilizator;

import com.example.metaprobackend.Registration.Token.ConfirmationToken;
import com.example.metaprobackend.Registration.Token.ConfirmationTokenService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UtilizatorService implements UserDetailsService {
    private final UtilizatorRepository utilizatorRepository;
    private final static String USER_NOT_FOUND_MESSAGE = "UTILIZATORUL CU MAILUL %s  ESTE INEXISTENT";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    public List<Utilizator> getUtilizator() {
        return utilizatorRepository.findAll();
    }
    public Utilizator getUtilizatorCurent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return utilizatorRepository.findUtilizatorByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizator inexistent"));
    }

    public void addNewUtilizator(Utilizator utilizator) {
        Optional<Utilizator> utilizatorOptional = utilizatorRepository.findUtilizatorByEmail(utilizator.getEmail());
        if (utilizatorOptional.isPresent()) {
            throw new IllegalStateException("Email folosit de alt utilizator");
        }
        utilizatorRepository.save(utilizator);
    }

    public void deleteUtilizator(UUID utilizatorId) {
        boolean exista = utilizatorRepository.existsById(utilizatorId);
        if (!exista) {
            throw new IllegalStateException("Utilizator inexistent");
        }
        utilizatorRepository.deleteById(utilizatorId);
    }

    @Transactional
    public void updateUtilizator(UUID utilizatorId, String username, String email) {
        Utilizator utilizator = utilizatorRepository.findById(utilizatorId)
                .orElseThrow(() -> new IllegalStateException("Utilizator cu id-ul " + utilizatorId + " nu exista"));

        if (username != null && !username.isEmpty() && !Objects.equals(utilizator.getUsername(), username)) {
            utilizator.setUsername(username);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(utilizator.getEmail(), email)) {
            Optional<Utilizator> utilizatorOptional = utilizatorRepository.findUtilizatorByEmail(email);
            if (utilizatorOptional.isPresent()) {
                throw new IllegalStateException("Email folosit de alt utilizator");
            }
            utilizator.setEmail(email);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return utilizatorRepository.findUtilizatorByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MESSAGE, email)));
    }


    public String signUpUser(Utilizator utilizator) {

        boolean este = utilizatorRepository.findUtilizatorByEmail(utilizator.getEmail()).isPresent();
        if (este) {
            throw new IllegalStateException("Email folosit de alt utilizator");

        }
        String codat = bCryptPasswordEncoder.encode(utilizator.getPassword());


        utilizator.setPassword(codat);
        utilizatorRepository.save(utilizator);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                utilizator



        );


confirmationTokenService.saveConfirmationToken(confirmationToken);



        return token;
    }

    public int enableUtilizator(String email) {
        return utilizatorRepository.enableUtilizator(email);
    }



}