package scot.oskar.springplayground.listing;

import java.time.LocalDateTime;
import java.util.UUID;

public record ListingView(
        UUID id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { }
