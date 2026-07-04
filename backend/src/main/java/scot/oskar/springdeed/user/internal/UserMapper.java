package scot.oskar.springdeed.user.internal;

import org.springframework.stereotype.Component;
import scot.oskar.springdeed.user.UserView;
import scot.oskar.springdeed.user.internal.persistence.UserEntity;

@Component
public class UserMapper {

    public UserEntity entityFromJwt() {
        return null;
    }

    public UserView toUserView(UserEntity user) {
        return new UserView(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreationTimestamp(),
                user.getUpdateTimestamp()
        );
    }
}
