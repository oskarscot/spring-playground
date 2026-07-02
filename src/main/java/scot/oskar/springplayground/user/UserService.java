package scot.oskar.springplayground.user;

import org.springframework.stereotype.Service;
import scot.oskar.springplayground.user.internal.persistence.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
