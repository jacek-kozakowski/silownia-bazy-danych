package pwr.bazydanych.silowniadb.common.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDto {
    private String email;
    private String haslo;
    private String imie;
    private String nazwisko;
    private String telefon;
    private LocalDate data_urodzenia;
}
