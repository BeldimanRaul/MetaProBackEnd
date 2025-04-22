package com.example.metaprobackend.eveniment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EvenimentRepository extends JpaRepository<Eveniment, UUID> {
    Optional<Eveniment> findEvenimentByNume(String nume);
}