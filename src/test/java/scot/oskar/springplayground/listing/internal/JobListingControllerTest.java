package scot.oskar.springplayground.listing.internal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import scot.oskar.springplayground.GlobalExceptionHandler;
import scot.oskar.springplayground.listing.JobListingService;
import scot.oskar.springplayground.listing.ListingView;
import scot.oskar.springplayground.listing.internal.dto.JobListingCreateRequest;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobListingController.class)
@Import(GlobalExceptionHandler.class)
class JobListingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    JobListingService jobListingService;

    @Test
    void returns_listing_when_found() throws Exception {
        var id = UUID.randomUUID();
        var view = new ListingView(
                id,
                "Spring Developer",
                "Build cool stuff",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        when(jobListingService.findById(id)).thenReturn(view);

        mockMvc.perform(get("/listings/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Spring Developer"))
                .andExpect(jsonPath("$.description").value("Build cool stuff"));
    }

    @Test
    void returns_404_when_listing_does_not_exist() throws Exception {
        var id = UUID.randomUUID();
        when(jobListingService.findById(id))
                .thenThrow(new NoSuchElementException("Listing %s not found".formatted(id)));

        mockMvc.perform(get("/listings/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("Listing %s not found".formatted(id)));
    }

    @Test
    void creates_listing_with_201_and_location_header() throws Exception {
        var id = UUID.randomUUID();
        var view = new ListingView(
                id,
                "Spring Developer",
                "Build cool stuff",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        when(jobListingService.createListing(any(JobListingCreateRequest.class))).thenReturn(view);

        mockMvc.perform(post("/listings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "name": "Spring Developer", "description": "Build cool stuff" }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/listings/" + id))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Spring Developer"));
    }

    @Test
    void returns_400_when_creating_listing_with_blank_name() throws Exception {
        mockMvc.perform(post("/listings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "name": "", "description": "Build cool stuff" }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletes_listing_with_204() throws Exception {
        var id = UUID.randomUUID();

        mockMvc.perform(delete("/listings/{id}", id))
                .andExpect(status().isNoContent());

        verify(jobListingService).deleteListing(id);
    }
}
