package com.example.metaprobackend.config;

import com.example.metaprobackend.eveniment.Eveniment;
import com.example.metaprobackend.eveniment.EvenimentRepository;
import com.example.metaprobackend.organizator.Organizator;
import com.example.metaprobackend.organizator.OrganizatorRepository;
import com.example.metaprobackend.Registration.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataInitialiser {

    @Bean
    CommandLineRunner initData(
            OrganizatorRepository organizatorRepository,
            EvenimentRepository evenimentRepository
    ) {
        return args -> {
            // Creează organizatorii
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
                    "Organizator evenimente internaționale",
                    "https://ticketmaster.ro",
                    UserRole.ORGANIZATOR
            );

            organizatorRepository.saveAll(List.of(eventim, ticketmaster));


            Eveniment festival = new Eveniment(
                    "Festival de vară",
                    "Un festival cu muzică live, food trucks și distracție",
                    LocalDate.of(2021, 8, 25),
                    LocalDate.of(2023, 11, 23),
                    "Brașov, Piața Sfatului",
                    "Festival",
                    33.4F
            );
            festival.setOrganizator(eventim);

            Eveniment workshop = new Eveniment(
                    "Workshop Java",
                    "Atelier de programare intensiv pentru începători",
                    LocalDate.of(2021, 8, 25),
                    LocalDate.of(2023, 11, 23),
                    "Universitatea Transilvania, Corpul N",
                    "Workshop",
                    50.0F
            );
            workshop.setOrganizator(ticketmaster);

            evenimentRepository.saveAll(List.of(festival, workshop));
        };
    }
}
