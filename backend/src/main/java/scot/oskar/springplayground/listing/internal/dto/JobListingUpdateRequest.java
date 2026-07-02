package scot.oskar.springplayground.listing.internal.dto;

import jakarta.validation.constraints.NotBlank;

public record JobListingUpdateRequest(
    @NotBlank String name,
    @NotBlank String description
) { }
