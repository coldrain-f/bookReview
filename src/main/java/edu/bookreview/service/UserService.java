package edu.bookreview.service;

import edu.bookreview.entity.User;
import edu.bookreview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signup(User user) {
        userRepository.save(user);
    }
}
