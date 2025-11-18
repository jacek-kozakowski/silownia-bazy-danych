package pwr.bazydanych.silowniadb.uzytkownik.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.bazydanych.silowniadb.uzytkownik.service.UzytkownikService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/uzytkownik")
public class UzytkownikController {
    private final UzytkownikService uzytkownikService;
}
