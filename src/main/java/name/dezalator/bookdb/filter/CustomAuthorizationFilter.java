package name.dezalator.bookdb.filter;

import lombok.AllArgsConstructor;
import name.dezalator.bookdb.utils.AuthUtils;
import name.dezalator.bookdb.utils.RestUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@AllArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private AuthUtils authUtils;
    private RestUtils restUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getServletPath().equals("/login") && !request.getServletPath().equals("/auth/refresh/")) {
            String authHeader = request.getHeader(AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                try {
                    authUtils.authorize(authHeader);
                    filterChain.doFilter(request, response);
                    return;
                } catch (Exception e) {
                    String errorMessage = e.getMessage();
                    restUtils.setupResponseError(response, errorMessage);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
