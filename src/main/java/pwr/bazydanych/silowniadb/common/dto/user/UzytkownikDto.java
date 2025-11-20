package pwr.bazydanych.silowniadb.common.dto.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.bazydanych.silowniadb.uzytkownik.model.Uzytkownik;

@Getter
@Setter
@NoArgsConstructor
public class UzytkownikDto {

    private Long id;

    private String email;

    private String imie;

    private String nazwisko;

    private String telefon;

    private String data_urodzenia;

    public UzytkownikDto(Uzytkownik u){
        this.id = u.getId_uzytkownik();
        this.email = u.getEmail();
        this.imie = u.getImie();
        this.nazwisko = u.getNazwisko();
        this.telefon = u.getTelefon();
        this.data_urodzenia = u.getData_urodzenia().toString();
    }
}
