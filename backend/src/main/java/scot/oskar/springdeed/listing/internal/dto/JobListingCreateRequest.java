package scot.oskar.springdeed.listing.internal.dto;

import jakarta.validation.constraints.NotBlank;

public record JobListingCreateRequest(
    @NotBlank String name,
    @NotBlank String description
) { }
