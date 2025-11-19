package pwr.bazydanych.silowniadb.uzytkownik.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.bazydanych.silowniadb.common.dto.user.EditUserDto;
import pwr.bazydanych.silowniadb.common.dto.user.UzytkownikDto;
import pwr.bazydanych.silowniadb.common.exceptions.UserNotFoundException;
import pwr.bazydanych.silowniadb.mail.service.MailService;
import pwr.bazydanych.silowniadb.uzytkownik.model.Uzytkownik;
import pwr.bazydanych.silowniadb.uzytkownik.repository.UzytkownikRepository;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UzytkownikService {
    private final UzytkownikRepository uzytkownikRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    private static final int CODE_MIN = 100000;
    private static final int CODE_MAX = 900000;

    public UzytkownikDto pobierzProfilUzytkownika(String email){
        Uzytkownik u = uzytkownikRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Uzytkownik o podanym email nie istnieje.")
        );
        return new UzytkownikDto(u);
    }

    @Transactional
    public UzytkownikDto edytujUzytkownika(String email, EditUserDto dto){
        Uzytkownik u = uzytkownikRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Uzytkownik o podanym email nie istnieje.")
        );

        if (!dto.hasAnyData()){
            return new UzytkownikDto(u);
        }
        if (dto.hasImie() && !u.getImie().equals(dto.getImie())){
            u.setImie(dto.getImie());
        }
        if (dto.hasNazwisko() && !u.getNazwisko().equals(dto.getNazwisko())){
            u.setNazwisko(dto.getNazwisko());
        }
        if (dto.hasEmail() && !u.getEmail().equals(dto.getEmail())){
            u.setEmail(dto.getEmail());
            u.setCzy_aktywny(false);
            u.setVerification_code(generateVerificationCode());
            u.setVerification_date(LocalDateTime.now().plusMinutes(15));
        }
        if (dto.hasTelefon() && !u.getTelefon().equals(dto.getTelefon())){
            u.setTelefon(dto.getTelefon());
        }
        if (dto.hasHaslo()){
            u.setHaslo(passwordEncoder.encode(dto.getHaslo()));
        }
        return new UzytkownikDto(u);
    }

    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        return String.valueOf(random.nextInt(CODE_MAX) + CODE_MIN);
    }

    private void sendVerificationEmail(Uzytkownik u){
        final String email = u.getEmail();
        final String imie = u.getImie();
        String subject = "Weryfikacja konta - Silownia";
        final String verificationCode = u.getVerification_code();
        if (email == null || verificationCode == null) {
            throw new IllegalArgumentException("Email or verification code is null");
        }
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Zmiana adresu email.</h2>"
                + "<p style=\"font-size: 16px;\">Hej "+ imie + "! Wpisz ponizszy kod weryfikacyjny żeby zweryfikować swój nowy email:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Kod weryfikacyjny:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        try{
            mailService.sendVerificationEmail(email, subject, htmlMessage);
        }catch (MessagingException e ){
            throw new MailSendException("Unable to send verification code", e);
        }
    }
}
