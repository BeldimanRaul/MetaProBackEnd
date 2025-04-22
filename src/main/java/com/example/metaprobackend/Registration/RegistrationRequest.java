package com.example.metaprobackend.Securitate;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String nume;
    private final String prenume;
    private final String password;
    private final String email;

}
