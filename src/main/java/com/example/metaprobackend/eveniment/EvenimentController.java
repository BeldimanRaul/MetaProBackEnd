package com.example.metaprobackend.eveniment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/eveniment")
public class EvenimentController {

    private final EvenimentService evenimentService;

    @Autowired
    public EvenimentController(EvenimentService evenimentService) {
        this.evenimentService = evenimentService;
    }

    @GetMapping
    public List<Eveniment> getEvenimente() {
        return evenimentService.getEvenimente();
    }

    @PostMapping
    public void registerNewEveniment(@RequestBody Eveniment eveniment) {
        evenimentService.addNewEveniment(eveniment);
    }

    @DeleteMapping(path = {"/{evenimentId}"})
    public void deleteEveniment(@PathVariable("evenimentId") UUID evenimentId) {
        evenimentService.deleteEveniment(evenimentId);
    }

    @PutMapping(path = {"/{evenimentId}"})
    public void updateEveniment(
            @PathVariable("evenimentId") UUID evenimentId,
            @RequestParam(required = false) String nume,
            @RequestParam(required = false) String descriere,
            @RequestParam(required = false) LocalDate dataStart,
            @RequestParam(required = false) LocalDate dataEnd,
            @RequestParam(required = false) String locatie,
            @RequestParam(required = false) String tipEveniment,
            @RequestParam(required = false) Float pretBilet) {
        evenimentService.updateEveniment(evenimentId, nume, descriere, dataStart,
                dataEnd, locatie, tipEveniment, pretBilet);
    }
}