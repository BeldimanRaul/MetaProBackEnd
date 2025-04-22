package com.example.metaprobackend.eveniment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class EvenimentConfig {

    @Bean
    CommandLineRunner commandLineRunnerEveniment(EvenimentRepository evenimentRepository) {
        return args -> {
            Eveniment festival = new Eveniment(
                    "Festival de vară",
                    "Un festival cu muzică live, food trucks și distracție",
                    LocalDate.of(2021, 8, 25),
                    LocalDate.of(2023, 11, 23),
                    "Brașov, Piața Sfatului",
                    "Festival",
                    33.4F
            );

            Eveniment workshop = new Eveniment(
                    "Workshop Java",
                    "Atelier de programare intensiv pentru începători",
                    LocalDate.of(2021, 8, 25),
                    LocalDate.of(2023, 11, 23),
                    "Universitatea Transilvania, Corpul N",
                    "Workshop",
                    50.0F
            );

            evenimentRepository.saveAll(List.of(festival, workshop));
        };
    }
}