package com.example.metaprobackend.organizator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/organizator")
public class OrganizatorController {

    private final OrganizatorService organizatorService;


    @Autowired
    public OrganizatorController(OrganizatorService organizatorService) {
        this.organizatorService = organizatorService;
    }

    @GetMapping
    public List<Organizator> getOrganizatori() {
        return organizatorService.getOrganizatori();
    }

    @PostMapping
    public void registerNewOrganizator(@RequestBody Organizator organizator) {
        organizatorService.addNewOrganizator(organizator);
    }

    @DeleteMapping(path = {"/{organizatorId}"})
    public void deleteOrganizator(@PathVariable("organizatorId") UUID organizatorId) {
        organizatorService.deleteOrganizator(organizatorId);
    }

    @PutMapping(path = {"/{organizatorId}"})
    public void updateOrganizator(
            @PathVariable("organizatorId") UUID organizatorId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String descriere,
            @RequestParam(required = false) String linkBilete) {
        organizatorService.updateOrganizator(organizatorId, username, email, descriere, linkBilete);
    }
}