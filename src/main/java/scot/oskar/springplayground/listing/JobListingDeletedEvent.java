package scot.oskar.springplayground.listing;

import java.util.UUID;

public record JobListingDeletedEvent(
        UUID listingId
){ }
