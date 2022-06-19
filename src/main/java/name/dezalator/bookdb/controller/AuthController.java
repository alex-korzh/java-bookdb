package name.dezalator.bookdb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import name.dezalator.bookdb.dto.UserSignupDto;
import name.dezalator.bookdb.service.UserService;
import name.dezalator.bookdb.utils.RestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private RestUtils restUtils;

    @PostMapping("/signup")
    public void signup(@RequestBody UserSignupDto userSignupDto, HttpServletResponse response) throws IOException {
        try {
            userService.signup(userSignupDto);
        } catch (Exception e) {
            restUtils.setupResponseError(response, e.getMessage());
        }
    }


    @PostMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                Map<String, String> tokens = userService.refreshToken(authHeader, request.getRequestURI().toString());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                restUtils.setupResponseError(response, e.getMessage());
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
