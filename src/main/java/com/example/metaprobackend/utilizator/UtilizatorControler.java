package com.example.metaprobackend.utilizator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/utilizator")
public class UtilizatorControler {

    private final UtilizatorService utilizatorService;


    @Autowired
    public UtilizatorControler(UtilizatorService utilizatorService) {
        this.utilizatorService = utilizatorService;
    }

//
//    @GetMapping
//    public List<Utilizator> getUtilizator  () {
//       return utilizatorService.getUtilizator();
//    }
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



}
