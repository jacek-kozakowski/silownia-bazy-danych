package pwr.bazydanych.silowniadb.auth.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.bazydanych.silowniadb.auth.jwt.JwtService;
import pwr.bazydanych.silowniadb.common.dto.auth.LoginRequestDto;
import pwr.bazydanych.silowniadb.common.dto.auth.LoginResponseDto;
import pwr.bazydanych.silowniadb.common.dto.auth.RegisterDto;
import pwr.bazydanych.silowniadb.common.dto.auth.VerifyDto;
import pwr.bazydanych.silowniadb.common.dto.user.UzytkownikDto;
import pwr.bazydanych.silowniadb.common.exceptions.UserAlreadyExistsException;
import pwr.bazydanych.silowniadb.common.exceptions.UserAlreadyVerifiedException;
import pwr.bazydanych.silowniadb.common.exceptions.UserNotFoundException;
import pwr.bazydanych.silowniadb.mail.service.MailService;
import pwr.bazydanych.silowniadb.uzytkownik.model.Klient;
import pwr.bazydanych.silowniadb.uzytkownik.model.Uzytkownik;
import pwr.bazydanych.silowniadb.uzytkownik.repository.UzytkownikRepository;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final MailService emailService;
    private final UzytkownikRepository uzytkownikRepository;

    private static final int EXPIRATION_TIME_MINUTES = 15;
    private static final int CODE_MIN = 100000;
    private static final int CODE_MAX = 900000;


    @Transactional
    public UzytkownikDto register(RegisterDto input){
        if(uzytkownikRepository.existsByEmail(input.getEmail())){
            throw new UserAlreadyExistsException("Uzytkownik o tym email juz istnieje.");
        }
        input.setHaslo(passwordEncoder.encode(input.getHaslo()));
        Uzytkownik nowyU = new Uzytkownik(input);
        nowyU.setVerification_code(generateVerificationCode());
        nowyU.setVerification_date(LocalDateTime.now().plusMinutes(EXPIRATION_TIME_MINUTES));

        Klient klient = new Klient(nowyU);

        nowyU.setKlient(klient);

        uzytkownikRepository.save(nowyU);
        sendVerificationEmail(nowyU);
        return new UzytkownikDto(nowyU);
    }

    private void sendVerificationEmail(Uzytkownik nowyU) {
        final String email = nowyU.getEmail();

        String subject = "Weryfikacja konta - Silownia";
        final String verificationCode = nowyU.getVerification_code();
        if (email == null || verificationCode == null) {
            throw new IllegalArgumentException("Email or verification code is null");
        }

        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Witaj na silowni!</h2>"
                + "<p style=\"font-size: 16px;\">Wpisz ponizszy kod weryfikacyjny żeby przejsc dalej:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Kod weryfikacyjny:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        try{
            emailService.sendVerificationEmail(email, subject, htmlMessage);
        }catch (MessagingException e ){
            throw new MailSendException("Unable to send verification code", e);
        }
    }

    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        return String.valueOf(random.nextInt(CODE_MAX) + CODE_MIN);
    }


    public LoginResponseDto authenticate(LoginRequestDto input){
        Optional<Uzytkownik> user = uzytkownikRepository.findByEmail(input.getEmail());

        if(user.isPresent()){
            Uzytkownik u = user.get();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getHaslo()));
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

    @Transactional
    public boolean verifyUser(VerifyDto input){
        Optional<Uzytkownik> optUser = uzytkownikRepository.findByEmail(input.getEmail());
        if (optUser.isPresent()){
            Uzytkownik user = optUser.get();
            if(user.isEnabled()){
                throw new UserAlreadyVerifiedException("Uzytkownik jest juz zweryfikowany.");
            }
            if(user.getVerification_code().equals(input.getVerificationCode()) && user.getVerification_date().isAfter(LocalDateTime.now())){
                user.setCzy_aktywny(true);
                user.setVerification_code(null);
                user.setVerification_date(null);
                uzytkownikRepository.save(user);
                return true;
            }
        }else{
            throw new UserNotFoundException("Uzytkownik o podanym email nie istnieje.");
        }
        return false;
    }

    @Transactional
    public boolean resendVerificationEmail(String email){
        Optional<Uzytkownik> optUser = uzytkownikRepository.findByEmail(email);
        if(optUser.isPresent()){
            Uzytkownik user = optUser.get();
            if(user.isEnabled()){
                throw new UserAlreadyVerifiedException("Uzytkownik jest juz zweryfikowany.");
            }
            user.setVerification_code(generateVerificationCode());
            user.setVerification_date(LocalDateTime.now().plusMinutes(EXPIRATION_TIME_MINUTES));
            uzytkownikRepository.save(user);
            sendVerificationEmail(user);
            return true;
        }else{
            throw new UserNotFoundException("Uzytkownik o podanym email nie istnieje.");
        }

    }
}
