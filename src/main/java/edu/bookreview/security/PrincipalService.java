package edu.bookreview.security;

import edu.bookreview.entity.User;
import edu.bookreview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인 성공시 PrincipalDetails 를 세션에 저장함.
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(PrincipalDetails::new).orElse(null);
    }
}
