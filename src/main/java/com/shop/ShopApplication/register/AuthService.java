package com.shop.ShopApplication.register;

import com.shop.ShopApplication.config.JwtService;
import com.shop.ShopApplication.repo.ConfirmationTokenRepository;
import com.shop.ShopApplication.user.ConfirmationToken;
import com.shop.ShopApplication.user.Role;
import com.shop.ShopApplication.user.User;
import com.shop.ShopApplication.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthResponse register(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if(existingUser != null)
        {
            throw new IllegalStateException();
        }else {
            var user = User.builder()
                    .login(request.getLogin())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("nazaralievaibek003005@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:8082/confirm-account?token="+confirmationToken.getConfirmationToken());
            emailSenderService.sendEmail(mailMessage);
            var jwtToken = jwtService.generateToken(user);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();

        }
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                ));
        var user = userRepository.findByLogin(request.getLogin()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }


}
