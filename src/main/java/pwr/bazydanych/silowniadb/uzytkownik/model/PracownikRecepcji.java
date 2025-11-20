package pwr.bazydanych.silowniadb.uzytkownik.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pracownicy_recepcji")
@Getter
@Setter
@NoArgsConstructor
public class PracownikRecepcji {
    @Id
    @Column(name = "id_pracownik")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name="id_pracownik")
    private Uzytkownik uzytkownik;

    private String zmianaPreferowana;

    public PracownikRecepcji(Uzytkownik u, String zmianaPreferowana){
        this.uzytkownik = u;
        this.zmianaPreferowana = zmianaPreferowana;
    }
}
