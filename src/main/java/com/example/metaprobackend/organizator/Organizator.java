package com.example.metaprobackend.organizator;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table (name = "organizatori")
public class Organizator {

    @Id
    @SequenceGenerator(name = "organizator_sequence",
            sequenceName = "organizator_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.UUID

    )
    private UUID id;
    private String username;
    private  String password;
    private String email;
    private String descriere;
    private String linkBilete;

    public Organizator(UUID id, String username, String password, String email, String descriere, String linkBilete) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.descriere = descriere;
        this.linkBilete = linkBilete;
    }

    public Organizator() {

    }

    public Organizator(String username, String password, String email, String descriere, String linkBilete) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.descriere = descriere;
        this.linkBilete = linkBilete;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getLinkBilete() {
        return linkBilete;
    }

    public void setLinkBilete(String linkBilete) {
        this.linkBilete = linkBilete;
    }

    @Override
    public String toString() {
        return "Organizator{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", descriere='" + descriere + '\'' +
                ", linkBilete='" + linkBilete + '\'' +
                '}';
    }
}
