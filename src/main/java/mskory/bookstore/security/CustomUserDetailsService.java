package mskory.bookstore.security;

import jakarta.annotation.PostConstruct;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mskory.bookstore.model.RoleName;
import mskory.bookstore.model.User;
import mskory.bookstore.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final SecurityProperties securityProperties;
    private final UserRepository userRepository;
    private UserDetailsService inMemoryUserDetailsManager;

    @PostConstruct
    protected void init() {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userFromDb = userRepository.findByEmail(username);
        return userFromDb.isPresent() ? userFromDb.get() :
                inMemoryUserDetailsManager.loadUserByUsername(username);
    }

    private InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails root = org.springframework.security.core.userdetails.User.builder()
                .username(securityProperties.getUser().getName())
                .password(securityProperties.getUser().getPassword())
                .authorities(RoleName.ADMIN.name())
                .build();
        return new InMemoryUserDetailsManager(root);
    }
}
