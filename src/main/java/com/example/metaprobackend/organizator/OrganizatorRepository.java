package com.example.metaprobackend.organizator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizatorRepository extends JpaRepository<Organizator, UUID> {
    Optional<Organizator> findOrganizatorByEmail(String email);
    Optional<Organizator> findOrganizatorByUsername(String username);



    ///activam ontul
    @Transactional
    @Modifying
    @Query
            ("UPDATE Organizator o " +
                    "SET o.enabled = TRUE WHERE o.email = ?1")
    int enableOrganizator(String email);

}

