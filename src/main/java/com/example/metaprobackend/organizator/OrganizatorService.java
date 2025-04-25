package com.example.metaprobackend.organizator;

import com.example.metaprobackend.Registration.Token.ConfirmationToken;
import com.example.metaprobackend.Registration.Token.ConfirmationTokenService;
import com.example.metaprobackend.utilizator.Utilizator;
import com.example.metaprobackend.utilizator.UtilizatorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
public class OrganizatorService implements UserDetailsService {
    private final OrganizatorRepository organizatorRepository;
    private final String ORG_NOT_FOUND_MESSAGE = "ORGANIZATORUL CU MAILUL %s  ESTE INEXISTENT";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService2;

    public List<Organizator> getOrganizatori() {
        return organizatorRepository.findAll();


    }

    public void addNewOrganizator(Organizator organizator) {
        Optional<Organizator> organizatorByEmail = organizatorRepository.findOrganizatorByEmail(organizator.getEmail());
        if (organizatorByEmail.isPresent()) {
            throw new IllegalStateException("Email deja folosit");
        }

        Optional<Organizator> organizatorByUsername = organizatorRepository.findOrganizatorByUsername(organizator.getUsername());
        if (organizatorByUsername.isPresent()) {
            throw new IllegalStateException("Username deja folosit");
        }

        organizatorRepository.save(organizator);
    }

    public void deleteOrganizator(UUID organizatorId) {
        boolean este = organizatorRepository.existsById(organizatorId);
        if (!este) {
            throw new IllegalStateException("Organizator cu id-ul " + organizatorId + " nu exista");
        }
        organizatorRepository.deleteById(organizatorId);
    }

    @Transactional
    public void updateOrganizator(UUID organizatorId,
                                  String username,
                                  String email,
                                  String descriere,
                                  String linkBilete) {
        Organizator organizator = organizatorRepository.findById(organizatorId)
                .orElseThrow(() -> new IllegalStateException("Organizator cu id-ul " + organizatorId + " nu exista"));

        if (username != null && !username.isEmpty() && !Objects.equals(organizator.getUsername(), username)) {
            Optional<Organizator> organizatorOptional = organizatorRepository.findOrganizatorByUsername(username);
            if (organizatorOptional.isPresent()) {
                throw new IllegalStateException("Username deja folosit");
            }
            organizator.setUsername(username);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(organizator.getEmail(), email)) {
            Optional<Organizator> organizatorOptional = organizatorRepository.findOrganizatorByEmail(email);
            if (organizatorOptional.isPresent()) {
                throw new IllegalStateException("Email deja folosit");
            }
            organizator.setEmail(email);
        }

        if (descriere != null && !descriere.isEmpty() && !Objects.equals(organizator.getDescriere(), descriere)) {
            organizator.setDescriere(descriere);
        }

        if (linkBilete != null && !linkBilete.isEmpty() && !Objects.equals(organizator.getLinkBilete(), linkBilete)) {
            organizator.setLinkBilete(linkBilete);
        }
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return organizatorRepository.findOrganizatorByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Organizatorul nu a fost gasit"));
    }

    public String signUpUser(Organizator organizator) {

        boolean este = organizatorRepository.findOrganizatorByEmail(organizator.getEmail()).isPresent();
        if (este) {
            return ("Email folositt de alt utilizator");

        }
        String codat2 = bCryptPasswordEncoder.encode(organizator.getPassword());


        organizator.setPassword(codat2);
        organizatorRepository.save(organizator);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                organizator


        );



        confirmationTokenService2.saveConfirmationToken(confirmationToken);



        return token;
    }

    public int enableOrganizator(String email) {
        return organizatorRepository.enableOrganizator(email);
    }



}