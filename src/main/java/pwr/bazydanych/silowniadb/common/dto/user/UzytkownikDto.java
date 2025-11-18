package pwr.bazydanych.silowniadb.common.dto.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UzytkownikDto {
    private String email;

    private String imie;

    private String nazwisko;

    private String telefon;

    private String data_urodzenia;
}
