package name.dezalator.bookdb.service;

import lombok.AllArgsConstructor;
import name.dezalator.bookdb.dto.UserSignupDto;
import name.dezalator.bookdb.dto.exception.PasswordsNotMatchException;
import name.dezalator.bookdb.model.Role;
import name.dezalator.bookdb.model.User;
import name.dezalator.bookdb.repository.UserRepository;
import name.dezalator.bookdb.utils.AuthUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private AuthUtils authUtils;


    //    todo use Page
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public Map<String, String> refreshToken(String authHeader, String uri) {
        authUtils.initAuth(authHeader);
        User user = getUser(authUtils.getUserEmail());
        return authUtils.refresh(user.getEmail(), user.getRole().name(), uri);
    }


    public void signup(UserSignupDto userSignupDto) throws PasswordsNotMatchException {
        if (!Objects.equals(userSignupDto.getPassword(), userSignupDto.getRepeatedPassword())) {
            throw new PasswordsNotMatchException("Passwords not match!");
        }
        User user = new User();
        user.setEmail(userSignupDto.getEmail());
        user.setPassword(passwordEncoder.encode(userSignupDto.getPassword()));
        user.setActive(true);
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
    }

    public void banUser(User user) {
        user.setBanned(true);
        userRepository.save(user);
    }

    public void unbanUser(User user) {
        user.setBanned(false);
        userRepository.save(user);
    }
}
