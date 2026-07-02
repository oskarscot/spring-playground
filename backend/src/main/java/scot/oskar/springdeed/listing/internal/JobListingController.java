package scot.oskar.springdeed.listing.internal;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scot.oskar.springdeed.listing.JobListingService;
import scot.oskar.springdeed.listing.ListingView;
import scot.oskar.springdeed.listing.internal.dto.JobListingCreateRequest;
import scot.oskar.springdeed.listing.internal.dto.JobListingUpdateRequest;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/listings")
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
        final var listingView = this.jobListingService.createListing(request);
        return ResponseEntity.created(URI.create("/listings/" + listingView.id()))
                .body(listingView);
    }

    @PutMapping("/{listingId}")
    ListingView update(@PathVariable UUID listingId, @RequestBody @Valid JobListingUpdateRequest request){
        return this.jobListingService.updateListing(listingId, request);
    }

    @DeleteMapping("/{listingId}")
    ResponseEntity<Void> delete(@PathVariable UUID listingId) {
        this.jobListingService.deleteListing(listingId);
        return ResponseEntity.noContent().build();
    }
}
