package scot.oskar.springplayground.listing.internal.dto;

import jakarta.validation.constraints.NotBlank;

public record JobListingCreateRequest(
    @NotBlank String name,
    @NotBlank String description
) { }
