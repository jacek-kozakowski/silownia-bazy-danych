package pwr.bazydanych.silowniadb.common.dto.personel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.bazydanych.silowniadb.common.dto.auth.RegisterDto;

@Getter
@Setter
@NoArgsConstructor
public class CreateTrenerDto {
    private RegisterDto daneUzytkownika;
    private String specjalizacja;
}
