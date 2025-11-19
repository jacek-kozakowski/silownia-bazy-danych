package pwr.bazydanych.silowniadb.common.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserDto {
    private String imie;
    private String nazwisko;
    private String telefon;
    private String email;
    private String haslo;

    public boolean hasImie(){
        return imie != null && !imie.isBlank();
    }
    public boolean hasNazwisko(){
        return nazwisko != null && !nazwisko.isBlank();
    }
    public boolean hasTelefon(){
        return telefon != null && !telefon.isBlank();
    }
    public boolean hasEmail(){
        return email != null && !email.isBlank();
    }
    public boolean hasHaslo(){
        return haslo != null && !haslo.isBlank();
    }

    public boolean hasAnyData(){
        return hasImie() || hasNazwisko() || hasTelefon() || hasEmail() || hasHaslo();
    }
}
