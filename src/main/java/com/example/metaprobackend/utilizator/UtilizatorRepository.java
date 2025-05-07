package com.example.metaprobackend.utilizator;
import com.example.metaprobackend.organizator.Organizator;
import org.springframework.validation.Validator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional (readOnly = true)
public interface UtilizatorRepository extends JpaRepository<Utilizator, UUID>{


    ///echivalentul in sql la SELECT *FROM utilizator WHERE email=?
    Optional<Utilizator> findUtilizatorByEmail(String email);




    ///activam contul
    @Transactional
    @Modifying
    @Query
            ("UPDATE Utilizator u " +
                    "SET u.enabled = TRUE WHERE u.email = ?1")
    int enableUtilizator(String email);

}
