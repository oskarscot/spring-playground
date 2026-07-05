package scot.oskar.springdeed.user;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scot.oskar.springdeed.user.internal.persistence.UserEntity;
import scot.oskar.springdeed.user.internal.persistence.UserRepository;

import java.util.Objects;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserEntity getOrCreateFromJwt(Jwt jwt) {
        final var id = Objects.requireNonNull(UUID.fromString(jwt.getSubject()), "User ID cannot be null!");
        final var username = Objects.requireNonNull(jwt.getClaimAsString("username"), "Username cannot be null!");
        final var email = Objects.requireNonNull(jwt.getClaimAsString("email"), "Email cannot be null!");
        return userRepository.findById(id).orElseGet(() -> {
            final var userEntity = new UserEntity(
                    id,
                    username,
                    email
            );
            userEntity.setFirstName(jwt.getClaimAsString("first_name"));
            userEntity.setLastName(jwt.getClaimAsString("last_name"));
            return userRepository.saveAndFlush(userEntity);
        });
    }

    UserView toUserView(UserEntity user) {
        return new UserView(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreationTimestamp(),
                user.getUpdateTimestamp()
        );
    }

    @Transactional
    public UserView displayUser(Jwt jwt) {
        return toUserView(getOrCreateFromJwt(jwt));
    }
}
