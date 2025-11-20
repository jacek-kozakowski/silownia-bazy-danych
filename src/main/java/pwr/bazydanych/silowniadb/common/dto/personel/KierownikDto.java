package pwr.bazydanych.silowniadb.common.dto.personel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.bazydanych.silowniadb.common.dto.user.UzytkownikDto;
import pwr.bazydanych.silowniadb.uzytkownik.model.Kierownik;

@Getter
@Setter
@NoArgsConstructor
public class KierownikDto {
    private UzytkownikDto daneUzytkownika;

    public KierownikDto(Kierownik k){
        this.daneUzytkownika = new UzytkownikDto(k.getUzytkownik());
    }
}
