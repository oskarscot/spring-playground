package scot.oskar.springdeed.listing;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scot.oskar.springdeed.listing.internal.dto.JobListingCreateRequest;
import scot.oskar.springdeed.listing.internal.dto.JobListingUpdateRequest;
import scot.oskar.springdeed.listing.internal.persistence.JobListingEntity;
import scot.oskar.springdeed.listing.internal.persistence.JobListingRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class JobListingService {

    private final JobListingRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public JobListingService(JobListingRepository repository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ListingView findById(UUID id) {
        return this.repository.findById(id)
                .map(this::toListingView)
                .orElseThrow(() -> new NoSuchElementException("Listing %s not found".formatted(id)));
    }

    @Transactional
    public ListingView createListing(JobListingCreateRequest request) {
        final var listingEntity = this.repository.save(
                new JobListingEntity(request.name(), request.description())
        );
        this.eventPublisher.publishEvent(new JobListingCreatedEvent(listingEntity.getId()));
        return this.toListingView(listingEntity);
    }

    @Transactional
    public ListingView updateListing(UUID listingId, JobListingUpdateRequest request) {
        final var listingEntity = this.repository.findById(listingId)
                .orElseThrow(() -> new NoSuchElementException("Listing %s not found".formatted(listingId)));

        listingEntity.setName(request.name());
        listingEntity.setDescription(request.description());

        this.eventPublisher.publishEvent(new JobListingUpdatedEvent(listingId));

        return this.toListingView(listingEntity);
    }

    @Transactional
    public void deleteListing(UUID listingId) {
        final var listingEntity = this.repository.findById(listingId)
                .orElseThrow(() -> new NoSuchElementException("Listing %s not found".formatted(listingId)));

        this.repository.delete(listingEntity);
        this.eventPublisher.publishEvent(new JobListingDeletedEvent(listingId));
    }

    private ListingView toListingView(JobListingEntity entity){
        return new ListingView(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
