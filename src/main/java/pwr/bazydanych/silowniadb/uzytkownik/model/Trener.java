package pwr.bazydanych.silowniadb.uzytkownik.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trenerzy")
@Getter
@Setter
@NoArgsConstructor
public class Trener {

    @Id
    @Column(name = "id_trenera")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name="id_trenera")
    private Uzytkownik uzytkownik;

    private String specjalizacja;

    public Trener(Uzytkownik u, String specjalizacja){
        this.uzytkownik = u;
        this.specjalizacja = specjalizacja;
    }
}
