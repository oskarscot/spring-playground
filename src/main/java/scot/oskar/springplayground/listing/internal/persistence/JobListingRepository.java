package scot.oskar.springplayground.listing.internal.persistence;

import java.util.UUID;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobListingRepository extends ListCrudRepository<JobListingEntity, UUID> {

}
