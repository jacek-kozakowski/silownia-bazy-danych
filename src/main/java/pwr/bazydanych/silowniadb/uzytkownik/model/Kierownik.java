package pwr.bazydanych.silowniadb.uzytkownik.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "kierownicy")
@Getter
@Setter
@NoArgsConstructor
public class Kierownik {
    @Id
    @Column(name = "id_kierownik")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name="id_kierownik")
    private Uzytkownik uzytkownik;

    public Kierownik(Uzytkownik u){
        this.uzytkownik = u;
    }
}
