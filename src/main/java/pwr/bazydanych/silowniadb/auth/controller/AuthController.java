package pwr.bazydanych.silowniadb.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pwr.bazydanych.silowniadb.auth.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
}
