package edu.bookreview.service;

import edu.bookreview.entity.User;
import edu.bookreview.exception.DuplicateNicknameException;
import edu.bookreview.exception.DuplicateUsernameException;
import edu.bookreview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signup(User user) {
        boolean duplicatedUsername = userRepository.findByUsername(user.getUsername())
                .isPresent();
        if (duplicatedUsername)
            throw new DuplicateUsernameException("The username already exists.");

        boolean duplicatedNickname = userRepository.findByNickname(user.getNickname())
                .isPresent();
        if (duplicatedNickname)
            throw new DuplicateNicknameException("The nickname already exists.");

        userRepository.save(user);
    }
}
