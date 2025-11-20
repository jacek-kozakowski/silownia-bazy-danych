package pwr.bazydanych.silowniadb.common.dto.personel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.bazydanych.silowniadb.common.dto.user.UzytkownikDto;
import pwr.bazydanych.silowniadb.uzytkownik.model.Trener;

@Getter
@Setter
@NoArgsConstructor
public class TrenerDto {

    private UzytkownikDto daneUzytkownika;
    private String specjalizacja;

    public TrenerDto(Trener t){
        this.daneUzytkownika = new UzytkownikDto(t.getUzytkownik());
        this.specjalizacja = t.getSpecjalizacja();
    }
}
