package com.example.metaprobackend.organizator;

import com.example.metaprobackend.Registration.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "organizatori")
public class Organizator  implements UserDetails {

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
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Boolean locked = false;
    private Boolean enabled=false;


    public Organizator(UUID id, String username, String password, String email, String descriere, String linkBilete) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.descriere = descriere;
        this.linkBilete = linkBilete;
    }

    public Organizator(String username, String password, String email, String descriere, String linkBilete,UserRole userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.descriere = descriere;
        this.linkBilete = linkBilete;
        this.userRole = userRole;
    }



    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return !locked; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return enabled; }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ORGANIZATOR"));
    }


    public String getPassword() {
        return password;
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
