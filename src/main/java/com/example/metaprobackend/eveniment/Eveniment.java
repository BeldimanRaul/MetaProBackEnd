package com.example.metaprobackend.eveniment;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table (name = "evenimente")
public class Eveniment {
    @Id
    @SequenceGenerator(
            name = "eveniment_sequence",
            sequenceName = "eveniment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.UUID


    )
    private UUID id;
    private String nume;
    private String descriere;
    private LocalDate dataStart;
    private LocalDate dataEnd;
    private String locatie;
    private String tipEveniment;
    private Float pretBilet;

    public Eveniment() {
    }

    public Eveniment(UUID id, String nume, String descriere, LocalDate dataStart,
                     LocalDate dataEnd, String locatie, String tipEveniment, Float pretBilet) {
        this.id = id;
        this.nume = nume;
        this.descriere = descriere;
        this.dataStart = dataStart;
        this.dataEnd = dataEnd;
        this.locatie = locatie;
        this.tipEveniment = tipEveniment;
        this.pretBilet = pretBilet;
    }

    public Eveniment(String nume, String descriere, LocalDate dataStart,
                     LocalDate dataEnd, String locatie, String tipEveniment, Float pretBilet) {
        this.nume = nume;
        this.descriere = descriere;
        this.dataStart = dataStart;
        this.dataEnd = dataEnd;
        this.locatie = locatie;
        this.tipEveniment = tipEveniment;
        this.pretBilet = pretBilet;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public LocalDate getDataStart() {
        return dataStart;
    }

    public void setDataStart(LocalDate dataStart) {
        this.dataStart = dataStart;
    }

    public LocalDate getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(LocalDate dataEnd) {
        this.dataEnd = dataEnd;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getTipEveniment() {
        return tipEveniment;
    }

    public void setTipEveniment(String tipEveniment) {
        this.tipEveniment = tipEveniment;
    }

    public Float getPretBilet() {
        return pretBilet;
    }

    public void setPretBilet(Float pretBilet) {
        this.pretBilet = pretBilet;
    }

    @Override
    public String toString() {
        return "Eveniment{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", descriere='" + descriere + '\'' +
                ", dataStart=" + dataStart +
                ", dataEnd=" + dataEnd +
                ", locatie='" + locatie + '\'' +
                ", tipEveniment='" + tipEveniment + '\'' +
                ", pretBilet=" + pretBilet +
                '}';
    }
}