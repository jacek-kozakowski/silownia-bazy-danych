package pwr.bazydanych.silowniadb.common.dto.personel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.bazydanych.silowniadb.common.dto.user.UzytkownikDto;
import pwr.bazydanych.silowniadb.uzytkownik.model.PracownikRecepcji;

@Getter
@Setter
@NoArgsConstructor
public class PracownikDto {
    private UzytkownikDto daneUzytkownika;
    private String preferowanaZmiana;

    public PracownikDto(PracownikRecepcji p ){
        this.daneUzytkownika = new UzytkownikDto(p.getUzytkownik());
        this.preferowanaZmiana = p.getZmianaPreferowana();
    }
}
