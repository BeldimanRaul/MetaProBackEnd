package com.example.metaprobackend.organizator;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrganizatorService {
    private final OrganizatorRepository organizatorRepository;

    @Autowired
    public OrganizatorService(OrganizatorRepository organizatorRepository) {
        this.organizatorRepository = organizatorRepository;
    }

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
        boolean exists = organizatorRepository.existsById(organizatorId);
        if (!exists) {
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
}