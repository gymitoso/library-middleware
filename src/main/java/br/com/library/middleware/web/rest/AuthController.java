package br.com.library.middleware.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.library.middleware.security.jwt.TokenProvider;
import br.com.library.middleware.service.UserService;
import br.com.library.middleware.service.dto.UserDTO;
import br.com.library.middleware.web.rest.vm.LoginVM;
import br.com.library.middleware.web.rest.errors.InternalServerErrorException;

import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    private final TokenProvider tokenProvider;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    public AuthController(TokenProvider tokenProvider, AuthenticationManager authenticationManager, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTAuth> authorize(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        UserDTO user = userService.getUserWithAuthorities()
                .map(UserDTO::new)
                .orElseThrow(() -> new InternalServerErrorException("User could not be found"));

        return new ResponseEntity<>(new JWTAuth(jwt, user), HttpStatus.OK);
    }

    /**
     * Object to return as body in Authentication.
     */
    static class JWTAuth {

        private String token;

        private UserDTO user;

        JWTAuth(String token, UserDTO user) {
            this.token = token;
            this.setUser(user);
        }

        @JsonProperty("token")
        String getIdToken() {
            return token;
        }

        void setIdToken(String token) {
            this.token = token;
        }

        public UserDTO getUser() {
            return user;
        }

        public void setUser(UserDTO user) {
            this.user = user;
        }
    }

}
