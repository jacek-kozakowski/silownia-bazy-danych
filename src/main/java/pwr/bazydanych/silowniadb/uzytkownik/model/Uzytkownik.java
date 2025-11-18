package pwr.bazydanych.silowniadb.uzytkownik.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pwr.bazydanych.silowniadb.common.dto.auth.RegisterDto;
import pwr.bazydanych.silowniadb.common.dto.user.UzytkownikDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "uzytkownicy")
public class Uzytkownik implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_uzytkownik;

    @Email
    private String email;

    private String haslo;

    private String imie;

    private String nazwisko;

    private String telefon;

    private LocalDate data_urodzenia;

    private LocalDate data_rejestracji;

    private boolean czy_aktywny;

    private String verification_code;
    private LocalDateTime verification_date;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isEnabled() {
        return czy_aktywny;
    }

    @Override
    public String getPassword() {
        return haslo;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public Uzytkownik(){
        this.czy_aktywny = false;
    }

    public Uzytkownik(RegisterDto dto){
        this.email = dto.getEmail();
        this.haslo = dto.getHaslo();
        this.imie = dto.getImie();
        this.nazwisko = dto.getNazwisko();
        this.telefon = dto.getTelefon();
        this.data_urodzenia = dto.getData_urodzenia();
        this.data_rejestracji = LocalDate.now();
        this.czy_aktywny = false;
    }
}
