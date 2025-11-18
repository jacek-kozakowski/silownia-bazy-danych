package pwr.bazydanych.silowniadb.uzytkownik.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pwr.bazydanych.silowniadb.uzytkownik.repository.UzytkownikRepository;

@Service
@RequiredArgsConstructor
public class UzytkownikService {
    private final UzytkownikRepository uzytkownikRepository;


}
