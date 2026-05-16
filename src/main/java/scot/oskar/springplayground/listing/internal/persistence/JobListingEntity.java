package scot.oskar.springplayground.listing.internal.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class JobListingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    protected JobListingEntity() { }

    public JobListingEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
