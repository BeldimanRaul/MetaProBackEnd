package com.example.metaprobackend.eveniment;

import com.example.metaprobackend.organizator.Organizator;
import com.example.metaprobackend.organizator.OrganizatorRepository;
import com.example.metaprobackend.organizator.OrganizatorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class EvenimentService {

    private final EvenimentRepository evenimentRepository;
    private final OrganizatorService organizatorService;


    @Autowired
    public EvenimentService(EvenimentRepository evenimentRepository, OrganizatorService organizatorService ) {
        this.evenimentRepository = evenimentRepository;
        this.organizatorService = organizatorService;

    }

    public List<Eveniment> getEvenimente() {
        return evenimentRepository.findAll();
    }

    public void addNewEveniment(Eveniment eveniment) {
        Optional<Eveniment> evenimentOptional =
                evenimentRepository.findEvenimentByNume(eveniment.getNume());
        if (evenimentOptional.isPresent()) {
            throw new IllegalStateException("Numele evenimentului este deja folosit");
        }
        eveniment.setOrganizator(organizatorService.getOrganizatorCurent());
        evenimentRepository.save(eveniment);
    }

    public void deleteEveniment(UUID evenimentId) {
        boolean exista = evenimentRepository.existsById(evenimentId);
        if (!exista) {
            throw new IllegalStateException("Eveniment inexistent");
        }
        evenimentRepository.deleteById(evenimentId);
    }

    @Transactional
    public void updateEveniment(UUID evenimentId, String nume, String descriere,
                                LocalDate dataStart, LocalDate dataEnd,
                                String locatie, String tipEveniment, Float pretBilet) {
        Eveniment eveniment = evenimentRepository.findById(evenimentId).orElseThrow(() ->
                new IllegalStateException("Eveniment cu id-ul " + evenimentId + " nu exista"));

        if (nume != null && !nume.isEmpty() && !Objects.equals(eveniment.getNume(), nume)) {
            Optional<Eveniment> evenimentOptional = evenimentRepository.findEvenimentByNume(nume);
            if (evenimentOptional.isPresent()) {
                throw new IllegalStateException("Numele evenimentului este deja folosit");
            }
            eveniment.setNume(nume);
        }

        if (descriere != null && !descriere.isEmpty()) {
            eveniment.setDescriere(descriere);
        }

        if (dataStart != null) {
            eveniment.setDataStart(dataStart);
        }

        if (dataEnd != null) {
            eveniment.setDataEnd(dataEnd);
        }

        if (locatie != null && !locatie.isEmpty()) {
            eveniment.setLocatie(locatie);
        }

        if (tipEveniment != null && !tipEveniment.isEmpty()) {
            eveniment.setTipEveniment(tipEveniment);
        }

        if (pretBilet != null && pretBilet >= 0) {
            eveniment.setPretBilet(pretBilet);
        }
    }
}