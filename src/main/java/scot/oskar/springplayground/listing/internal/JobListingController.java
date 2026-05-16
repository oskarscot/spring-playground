package scot.oskar.springplayground.listing.internal;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scot.oskar.springplayground.listing.JobListingService;
import scot.oskar.springplayground.listing.ListingView;
import scot.oskar.springplayground.listing.internal.dto.JobListingCreateRequest;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(value = "/listings", version = "1.0")
class JobListingController {

    private final JobListingService jobListingService;

    JobListingController(JobListingService jobListingService) {
        this.jobListingService = jobListingService;
    }

    @GetMapping("/{listingId}")
    ListingView findById(@PathVariable UUID listingId) {
        return this.jobListingService.findById(listingId);
    }

    @PostMapping
    ResponseEntity<ListingView> create(@RequestBody @Valid JobListingCreateRequest request) {
        final var listingView = jobListingService.createListing(request);
        return ResponseEntity.created(URI.create("/listings/" + listingView.id()))
                .body(listingView);
    }

}
