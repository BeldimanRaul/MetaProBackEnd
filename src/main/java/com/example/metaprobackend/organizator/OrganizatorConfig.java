package com.example.metaprobackend.organizator;

import com.example.metaprobackend.Registration.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OrganizatorConfig {
    @Bean
    CommandLineRunner organizatorcommandLineRunner(OrganizatorRepository organizatorRepository) {
        return args -> {
            Organizator eventim = new Organizator(
                    "eventim",
                    "eventim123",
                    "eventim@example.com",
                    "Organizator de evenimente culturale",
                    "https://eventim.ro",
                    UserRole.ORGANIZATOR
            );

            Organizator ticketmaster = new Organizator(
                    "ticketmaster",
                    "ticketmaster123",
                    "ticketmaster@example.com",
                    "Organizator evenimente internationale",
                    "https://ticketmaster.ro",
                    UserRole.ORGANIZATOR
            );

            organizatorRepository.saveAll(
                    List.of(eventim, ticketmaster)
            );
        };
    }
}