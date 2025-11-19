package pwr.bazydanych.silowniadb.uzytkownik.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pwr.bazydanych.silowniadb.common.dto.user.EditUserDto;
import pwr.bazydanych.silowniadb.common.dto.user.UzytkownikDto;
import pwr.bazydanych.silowniadb.uzytkownik.service.UzytkownikService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/uzytkownik")
public class UzytkownikController {
    private final UzytkownikService uzytkownikService;

    @GetMapping
    public ResponseEntity<UzytkownikDto> pobierzProfilUzytkownika(){
        UzytkownikDto response = getUserFromContext();
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<UzytkownikDto> zaktualizujProfilUzytkownika(@RequestBody EditUserDto dto){
        UzytkownikDto u = getUserFromContext();
        UzytkownikDto response = uzytkownikService.edytujUzytkownika(u.getEmail(), dto);
        return ResponseEntity.ok(response);
    }

    private UzytkownikDto getUserFromContext(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return uzytkownikService.pobierzProfilUzytkownika(email);
    }
}
