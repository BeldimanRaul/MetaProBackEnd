package com.example.metaprobackend.utilizator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional (readOnly = true)
public interface UtilizatorRepository extends JpaRepository<Utilizator, UUID>{


    ///echivalentul in sql la SELECT *FROM utilizator WHERE email=?
    Optional<Utilizator> findUtilizatorByEmail(String email);
}
