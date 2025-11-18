package pwr.bazydanych.silowniadb.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.bazydanych.silowniadb.auth.service.AuthService;
import pwr.bazydanych.silowniadb.common.ApiResponse;
import pwr.bazydanych.silowniadb.common.dto.auth.*;
import pwr.bazydanych.silowniadb.common.dto.user.UzytkownikDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UzytkownikDto> registerUser(@RequestBody RegisterDto req){
        UzytkownikDto response = authService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto req){
        LoginResponseDto response = authService.authenticate(req);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse> verifyUser(@RequestBody VerifyDto req){
        boolean verified = authService.verifyUser(req);
        if (verified){
            return ResponseEntity.ok(new ApiResponse("Zweryfikowano konto."));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Nie udało sie zweryfikować konta."));
        }
    }
    @PostMapping("/resend")
    public ResponseEntity<ApiResponse> resendVerificationCode(@RequestBody ResendDto req){
        boolean resent = authService.resendVerificationEmail(req.getEmail());
        if (resent){
            return ResponseEntity.ok(new ApiResponse("Wysłano nowy kod weryfikacji."));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Nie udało sie wysłac nowego kodu weryfikacji."));
        }


    }
}
