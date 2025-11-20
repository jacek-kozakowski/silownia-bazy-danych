package pwr.bazydanych.silowniadb.uzytkownik.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "klienci")
@Getter
@Setter
@NoArgsConstructor
public class Klient{
    @Id
    @Column(name = "id_klient")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name="id_klient")
    private Uzytkownik uzytkownik;

    public Klient(Uzytkownik u){
        this.uzytkownik = u;
    }
}
