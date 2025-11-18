package pwr.bazydanych.silowniadb.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pwr.bazydanych.silowniadb.auth.jwt.JwtService;
import pwr.bazydanych.silowniadb.common.dto.auth.LoginRequestDto;
import pwr.bazydanych.silowniadb.common.dto.auth.LoginResponseDto;
import pwr.bazydanych.silowniadb.common.dto.auth.RegisterDto;
import pwr.bazydanych.silowniadb.common.dto.user.UzytkownikDto;
import pwr.bazydanych.silowniadb.common.exceptions.UserNotFoundException;
import pwr.bazydanych.silowniadb.uzytkownik.model.Uzytkownik;
import pwr.bazydanych.silowniadb.uzytkownik.repository.UzytkownikRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UzytkownikRepository uzytkownikRepository;


    public UzytkownikDto register(RegisterDto input){
        return null;
    }


    public LoginResponseDto authenticate(LoginRequestDto input){
        Optional<Uzytkownik> user = uzytkownikRepository.findByEmail(input.getEmail());

        if(user.isPresent()){
            Uzytkownik u = user.get();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
            return generateToken(u);
        }else{
            throw new UserNotFoundException("Uzytkownik o podanym email nie istnieje.");
        }
    }

    private LoginResponseDto generateToken(Uzytkownik user){
        LoginResponseDto response = new LoginResponseDto();
        response.setToken(jwtService.generateToken(user));
        response.setExpirationTime(jwtService.getExpirationTime());
        return response;
    }
}
