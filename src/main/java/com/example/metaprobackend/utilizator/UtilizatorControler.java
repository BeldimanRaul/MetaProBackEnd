package com.example.metaprobackend.utilizator;

import com.example.metaprobackend.eveniment.Eveniment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/utilizator")
public class UtilizatorControler {

    private final UtilizatorService utilizatorService;


    @Autowired
    public UtilizatorControler(UtilizatorService utilizatorService) {
        this.utilizatorService = utilizatorService;
    }


    @GetMapping("/me")
    public Utilizator getUtilizatorCurent() {
        return utilizatorService.getUtilizatorCurent();
    }




    @PostMapping
    public void registerNewUtilizator(@RequestBody Utilizator utilizator) {
        utilizatorService.addNewUtilizator(utilizator);
    }


    @DeleteMapping(path = {"/{utilizatorId}"})
    public void deleteUtilizator(@PathVariable ("utilizatorId")  UUID utilizatorId) {
        utilizatorService.deleteUtilizator(utilizatorId);
    }


    @PutMapping(path={"/{utilizatorId}"})
    public void updateUtilizator(
            @PathVariable("utilizatorId")UUID utilizatorId,
            @RequestParam (required = false) String name,
            @RequestParam (required = false) String email){
                utilizatorService.updateUtilizator(utilizatorId,name,email);
    }
    @PostMapping("/eveniment/{evenimentId}")
    public ResponseEntity<?> participaLaEveniment(@PathVariable UUID evenimentId) {
        utilizatorService.adaugaEvenimentLaUtilizator(evenimentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eveniment/{evenimentId}")
    public ResponseEntity<?> stergeEvenimentDinLista(@PathVariable UUID evenimentId) {
        utilizatorService.stergeEvenimentDeLaUtilizator(evenimentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/evenimente")
    public Set<Eveniment> getEvenimenteUtilizator() {
        return utilizatorService.getUtilizatorCurent().getEvenimente();
    }



}
