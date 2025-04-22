package com.example.metaprobackend.utilizator;

import com.example.metaprobackend.Registration.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class UtilizatorConfig {
    @Bean
    CommandLineRunner commandLineRunner(UtilizatorRepository utilizatorRepository) {
        return args -> {
            Utilizator raul = new Utilizator(
                    "raul",
                    "raul",
                    "raul@gmail",
                    LocalDate.of(1990, Month.JANUARY, 1),
                    UserRole.USER
            );

            Utilizator alex = new Utilizator(
                    "alex",
                    "parola",
                    "alex@gmail",
                    LocalDate.of(2002, Month.JUNE, 15),
                    UserRole.USER
            );

            utilizatorRepository.saveAll(
                    List.of(raul, alex)
            );
        };
    }
}