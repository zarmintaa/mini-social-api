package app.zarminta.rest.service;

import app.zarminta.rest.entity.dto.request.AuthenticationRequest;
import app.zarminta.rest.entity.dto.request.ResetPasswordRequest;
import app.zarminta.rest.entity.dto.response.AuthenticationResponse;
import app.zarminta.rest.entity.dto.request.RegisterRequest;
import app.zarminta.rest.config.JwtService;
import app.zarminta.rest.entity.Role;
import app.zarminta.rest.entity.User;
import app.zarminta.rest.entity.dto.response.MessageResponse;
import app.zarminta.rest.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private EntityService entityService;


    public ResponseEntity<Object> register(RegisterRequest request) {
        if (repository.existsByEmail(request.getEmail())){
            return entityService.jsonResponse(HttpStatus.FOUND, "Email with " + request.getEmail() + " exists!");
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .followingCounter(0)
                .followerCounter(0)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .role(user.getRole().name())
                .token(jwtToken)
                .build();
        return entityService.jsonResponse(HttpStatus.CREATED, response);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .role(user.getRole().name())
                .token(jwtToken)
                .build();
    }

    public ResponseEntity<Object> resetPassword(ResetPasswordRequest resetPasswordRequest){
        if (!repository.existsByEmail(resetPasswordRequest.getEmail())) {
            return entityService
                    .jsonResponse(HttpStatus.NOT_FOUND, MessageResponse.builder()
                            .message("Email not found!")
                            .build());
        }

        User user = repository.findByEmail(resetPasswordRequest.getEmail()).get();
        boolean isMatchPassword = passwordEncoder.matches(resetPasswordRequest.getCurrentPassword(), user.getPassword());
        if (!isMatchPassword){
            return entityService
                    .jsonResponse(HttpStatus.NOT_FOUND, MessageResponse.builder()
                            .message("Wrong Password")
                            .build());
        }
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        repository.save(user);
        return entityService
                .jsonResponse(HttpStatus.NOT_FOUND, MessageResponse.builder()
                        .message("Password updated successfully!")
                        .build());
    }
}
