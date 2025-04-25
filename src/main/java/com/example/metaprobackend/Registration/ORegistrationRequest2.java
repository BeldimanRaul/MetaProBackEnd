package com.example.metaprobackend.Registration;
import org.springframework.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ORegistrationRequest2 {
    private final String username;
    private final String email;
    private final String password;
    private final String descriere;
    private final String linkBilete;
}
