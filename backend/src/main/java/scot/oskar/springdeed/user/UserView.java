package scot.oskar.springdeed.user;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserView(
        UUID id,
        String username,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { }
