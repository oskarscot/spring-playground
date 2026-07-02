package scot.oskar.springplayground.listing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;
import scot.oskar.springplayground.PostgresConfiguration;
import scot.oskar.springplayground.listing.internal.dto.JobListingCreateRequest;
import scot.oskar.springplayground.listing.internal.dto.JobListingUpdateRequest;
import scot.oskar.springplayground.listing.internal.persistence.JobListingRepository;

@ApplicationModuleTest
@Import(PostgresConfiguration.class)
class JobListingServiceTest {

    @Autowired
    JobListingService jobListingService;

    @Autowired
    JobListingRepository jobListingRepository;

    @AfterEach
    void cleanup() {
        this.jobListingRepository.deleteAll();
    }

    @Test
    void publishes_create_event_when_creating_listing(Scenario scenario) {
        scenario.stimulate(() ->
                jobListingService.createListing(
                        new JobListingCreateRequest("Spring Developer", "Build cool stuff!")))
                .andWaitForEventOfType(JobListingCreatedEvent.class)
                .matching(event -> event.id() != null)
                .toArrive();
    }

    @Test
    void publishes_update_event_when_updating_listing(Scenario scenario) {
        var listing = givenListing("Spring Developer", "Build cool stuff");

        scenario.stimulate(() ->
                jobListingService.updateListing(
                        listing.id(), new JobListingUpdateRequest("Spring Developer 2", "Awesome stuff")))
                .andWaitForEventOfType(JobListingUpdatedEvent.class)
                .matching(event -> event.id().equals(listing.id()))
                .toArrive();
    }

    @Test
    void publishes_delete_event_when_deleting_listing(Scenario scenario) {
        var listing = givenListing("Spring Developer", "Build cool stuff");

        scenario.stimulate(() ->
                jobListingService.deleteListing(listing.id()))
                .andWaitForEventOfType(JobListingDeletedEvent.class)
                .matching(event -> event.id().equals(listing.id()))
                .toArrive();
    }

    private ListingView givenListing(String name, String description) {
        return jobListingService.createListing(new JobListingCreateRequest(name, description));
    }
}