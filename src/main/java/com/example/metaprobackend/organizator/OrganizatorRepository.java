package com.example.metaprobackend.organizator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizatorRepository extends JpaRepository<Organizator, UUID> {
    Optional<Organizator> findOrganizatorByEmail(String email);
    Optional<Organizator> findOrganizatorByUsername(String username);
}