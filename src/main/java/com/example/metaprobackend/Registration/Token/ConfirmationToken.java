package com.example.metaprobackend.Registration.Token;

import com.example.metaprobackend.organizator.Organizator;
import com.example.metaprobackend.utilizator.Utilizator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String token;

    private LocalDateTime creat;

    @Column(nullable = false)
    private LocalDateTime expirat;

    private LocalDateTime confirmat;

    @ManyToOne
    @JoinColumn(name = "utilizator_id", nullable = true)
    private Utilizator utilizator;

    @ManyToOne
    @JoinColumn(name = "organizator_id", nullable = true)
    private Organizator organizator;

    public ConfirmationToken(String token, LocalDateTime creat, LocalDateTime expirat, Utilizator utilizator) {
        this.token = token;
        this.creat = creat;
        this.expirat = expirat;
        this.utilizator = utilizator;
    }

    public ConfirmationToken(String token, LocalDateTime creat, LocalDateTime expirat, Organizator organizator) {
        this.token = token;
        this.creat = creat;
        this.expirat = expirat;
        this.organizator = organizator;
    }

    @PrePersist
    @PreUpdate
    private void validate() {
        boolean utilizatorSet = utilizator != null;
        boolean organizatorSet = organizator != null;
        if (utilizatorSet == organizatorSet) {
            throw new IllegalStateException("Tokenul trebuie sa fie asociat fie cu un utilizator, fie cu un organizator.");
        }
    }

    @Transient
    public String getAssociatedName() {
        if (utilizator != null) return utilizator.getEmail();
        if (organizator != null) return organizator.getEmail();
        return "N/A";
    }
}
