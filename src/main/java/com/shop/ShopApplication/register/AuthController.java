package com.shop.ShopApplication.register;

import com.shop.ShopApplication.register.token.ConfirmationResponse;
import com.shop.ShopApplication.register.token.ResendRequest;
import com.shop.ShopApplication.register.token.ResendResponse;
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

    @PostMapping(path = "confirm")
    public ResponseEntity<ConfirmationResponse> confirm(@RequestParam("conToken") String conToken) {
        ConfirmationResponse response = authService.confirmToken(conToken);
        return ResponseEntity.ok(response);
    }
//    public String confirm(@RequestParam("conToken") String conToken) {
//        return authService.confirmToken(conToken);
//    }

    @PostMapping("/resend-confirmation")
    public ResponseEntity<ResendResponse> resendConfirmation(@RequestBody ResendRequest request){
        return ResponseEntity.ok(authService.resendConfirmation(request));
    }

}
