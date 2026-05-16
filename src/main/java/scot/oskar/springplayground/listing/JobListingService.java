package scot.oskar.springplayground.listing;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scot.oskar.springplayground.listing.internal.dto.JobListingCreateRequest;
import scot.oskar.springplayground.listing.internal.persistence.JobListingEntity;
import scot.oskar.springplayground.listing.internal.persistence.JobListingRepository;

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
