package pwr.bazydanych.silowniadb.zarzadzaniepersonelem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.bazydanych.silowniadb.common.dto.auth.RegisterDto;
import pwr.bazydanych.silowniadb.common.dto.personel.*;
import pwr.bazydanych.silowniadb.common.dto.user.UzytkownikDto;
import pwr.bazydanych.silowniadb.common.exceptions.EmptyRequestException;
import pwr.bazydanych.silowniadb.common.exceptions.UserAlreadyExistsException;
import pwr.bazydanych.silowniadb.common.exceptions.UserNotFoundException;
import pwr.bazydanych.silowniadb.uzytkownik.model.Kierownik;
import pwr.bazydanych.silowniadb.uzytkownik.model.PracownikRecepcji;
import pwr.bazydanych.silowniadb.uzytkownik.model.Trener;
import pwr.bazydanych.silowniadb.uzytkownik.model.Uzytkownik;
import pwr.bazydanych.silowniadb.uzytkownik.repository.UzytkownikRepository;

@Service
@RequiredArgsConstructor
public class PersonelService {
    private final UzytkownikRepository uzytkownikRepository;
    private final PasswordEncoder passwordEncoder;

    private Uzytkownik stworzUzytkownikaBazowego(RegisterDto dane){
        if (dane == null){
            throw new EmptyRequestException("Brak danych uzytkownika");
        }
        if (uzytkownikRepository.existsByEmail(dane.getEmail())){
            throw new UserAlreadyExistsException("Uzytkownik o podanym email juz istnieje");
        }
        String haslo = passwordEncoder.encode(dane.getHaslo());
        dane.setHaslo(haslo);
        Uzytkownik nowyU = new Uzytkownik(dane);
        nowyU.setCzy_aktywny(true);

        return uzytkownikRepository.save(nowyU);
    }

    @Transactional
    public TrenerDto stworzTrenera(CreateTrenerDto dto){
        Uzytkownik u = stworzUzytkownikaBazowego(dto.getDaneUzytkownika());
        Trener t = new Trener(u, dto.getSpecjalizacja());
        u.setTrener(t);
        uzytkownikRepository.save(u);
        return new TrenerDto(t);
    }

    @Transactional
    public PracownikDto stworzPracownikaRecepcji(CreatePracownikDto dto){
        Uzytkownik u = stworzUzytkownikaBazowego(dto.getDaneUzytkownika());
        PracownikRecepcji p = new PracownikRecepcji(u, dto.getZmianaPreferowana());
        u.setPracownikRecepcji(p);
        uzytkownikRepository.save(u);
        return new PracownikDto(p);
    }

    @Transactional
    public KierownikDto stworzKierownika(CreateKierownikDto dto){
        Uzytkownik u = stworzUzytkownikaBazowego(dto.getDaneUzytkownika());
        Kierownik k = new Kierownik(u);
        u.setKierownik(k);
        uzytkownikRepository.save(u);
        return new KierownikDto(k);
    }

    @Transactional
    public TrenerDto dodajTrenera(Long id, String specjalizacja){
        Uzytkownik u = uzytkownikRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Nie znaleziono uzytkownika o id: "+id)
        );
        if (u.getTrener() != null){
            throw new UserAlreadyExistsException("Uzytkownik o podanym id juz jest trenerem.");
        }
        Trener t = new Trener(u, specjalizacja);
        u.setTrener(t);
        uzytkownikRepository.save(u);
        return new TrenerDto(t);
    }

    @Transactional
    public PracownikDto dodajPracownikaRecepcji(Long id, String zmianaPreferowana){
        Uzytkownik u = uzytkownikRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Nie znaleziono uzytkownika o id: "+id)
        );
        if (u.getPracownikRecepcji() != null){
            throw new UserAlreadyExistsException("Uzytkownik o podanym id juz jest pracownikiem recepcji.");
        }
        PracownikRecepcji p = new PracownikRecepcji(u, zmianaPreferowana);
        u.setPracownikRecepcji(p);
        uzytkownikRepository.save(u);
        return new PracownikDto(p);
    }
}
