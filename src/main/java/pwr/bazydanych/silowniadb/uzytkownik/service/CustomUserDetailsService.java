package pwr.bazydanych.silowniadb.uzytkownik.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pwr.bazydanych.silowniadb.uzytkownik.repository.UzytkownikRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UzytkownikRepository uRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return uRepo.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Nie znaleziono uzytkownika o emailu: "+email)
        );
    }
}
