package org.lbcc.bms.bms_monolith.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lbcc.bms.bms_monolith.common.response.ApiListResponse;
import org.lbcc.bms.bms_monolith.entity.Event;
import org.lbcc.bms.bms_monolith.exception.EventServiceException;
import org.lbcc.bms.bms_monolith.service.IEventService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;
import static org.mockito.Mockito.when;

public class EventControllerTest {

    @Mock
    private IEventService eventService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEventsSuccess() {
        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setTitle("Sample Event");
        Page<Event> eventPage = new PageImpl<>(List.of(event), PageRequest.of(0, 10), 1);

        ApiListResponse<Event> apiResponse = ApiListResponse.<Event>builder()
                .success(true)
                .message("Events fetched successfully")
                .data(eventPage.getContent())
                .totalItems((int) eventPage.getTotalElements())
                .totalPages(eventPage.getTotalPages())
                .currentPage(eventPage.getNumber())
                .pageSize(eventPage.getSize())
                .hasNextPage(eventPage.hasNext())
                .hasPreviousPage(eventPage.hasPrevious())
                .build();

        when(eventService.getAllEvents(anyInt(), anyInt())).thenReturn(apiResponse);

        ResponseEntity<ApiListResponse<Event>> response = eventController.getAllEvents(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        assertEquals("Events fetched successfully", response.getBody().getMessage());
        assertEquals(1, response.getBody().getTotalItems());
    }

    @Test
    void testGetAllEventsInvalidPageParameters() {
        ApiListResponse<Event> apiResponse = ApiListResponse.<Event>builder()
                .success(false)
                .message("Invalid page or size parameters")
                .build();

        when(eventService.getAllEvents(-1, 10)).thenReturn(apiResponse);
        ResponseEntity<ApiListResponse<Event>> response = eventController.getAllEvents(-1, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Invalid page or size parameters", response.getBody().getMessage());
        assertEquals(false, response.getBody().isSuccess());
    }

    @Test
    void testGetAllEventsExceptionHandling() {
        when(eventService.getAllEvents(anyInt(), anyInt()))
                .thenThrow(new EventServiceException("Failed to fetch events", new RuntimeException()));

        EventServiceException exception = assertThrows(EventServiceException.class, () -> {
            eventController.getAllEvents(0, 10);
        });

        assertEquals("Failed to fetch events", exception.getMessage());
    }

}
