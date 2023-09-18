package com.shop.ShopApplication.register;

import com.shop.ShopApplication.register.token.ConfirmationResponse;
import com.shop.ShopApplication.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));

    }

    @PostMapping("/log")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request ){
        return ResponseEntity.ok(authService.login(request));

    }

    @GetMapping(path = "confirm")
    public ResponseEntity<ConfirmationResponse> confirm(@RequestParam("conToken") String conToken) {
        ConfirmationResponse response = authService.confirmToken(conToken);
        return ResponseEntity.ok(response);
    }
//    public String confirm(@RequestParam("conToken") String conToken) {
//        return authService.confirmToken(conToken);
//    }

    @PostMapping("/resend-email")
    public ResponseEntity<String> resendEmail(@RequestParam("email") String email) {
        authService.resendConfirmationEmail(email);
        return ResponseEntity.ok("Email has been resent.");
    }

}
