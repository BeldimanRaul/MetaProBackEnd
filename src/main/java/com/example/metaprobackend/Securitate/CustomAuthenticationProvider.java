package com.example.metaprobackend.Securitate;

import com.example.metaprobackend.organizator.OrganizatorService;
import com.example.metaprobackend.utilizator.UtilizatorService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UtilizatorService utilizatorService;
    private final OrganizatorService organizatorService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();


        try {
            UserDetails user = utilizatorService.loadUserByUsername(email);
            if (!user.isEnabled()) throw new DisabledException("Contul nu este activat");
            if (!user.isAccountNonLocked()) throw new LockedException("Cont blocat");

            if (bCryptPasswordEncoder.matches(rawPassword, user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(user, rawPassword, user.getAuthorities());
            } else {
                throw new BadCredentialsException("Parolă greșită");
            }
        } catch (Exception ignored) {}


        try {
            UserDetails org = organizatorService.loadUserByUsername(email);
            if (!org.isEnabled()) throw new DisabledException("Contul de organizator nu este activat");
            if (!org.isAccountNonLocked()) throw new LockedException("Cont de organizator blocat");

            if (bCryptPasswordEncoder.matches(rawPassword, org.getPassword())) {
                return new UsernamePasswordAuthenticationToken(org, rawPassword, org.getAuthorities());
            } else {
                throw new BadCredentialsException("Parolă greșită");
            }
        } catch (Exception ignored) {}

        throw new BadCredentialsException("Cont inexistent sau date incorecte");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
