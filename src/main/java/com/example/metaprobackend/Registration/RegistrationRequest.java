package com.example.metaprobackend.Registration;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

   private final String username;
    private final LocalDate dob;
    private final String password;
    private final String email;

}
