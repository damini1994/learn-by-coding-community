package org.lbcc.bms.bms_monolith.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lbcc.bms.bms_monolith.common.response.ApiListResponse;
import org.lbcc.bms.bms_monolith.entity.Event;
import org.lbcc.bms.bms_monolith.exception.EventServiceException;
import org.lbcc.bms.bms_monolith.repository.EventRepository;
import org.lbcc.bms.bms_monolith.service.impl.EventServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEventsSuccess() {
        Event event = new Event();
        event.setTitle("Sample Event");
        event.setDescription("Sample Description");

        Page<Event> eventPage = new PageImpl<>(Collections.singletonList(event), PageRequest.of(0, 10), 1);
        when(eventRepository.findAllWithDetails(PageRequest.of(0, 10))).thenReturn(eventPage);

        ApiListResponse<Event> response = eventService.getAllEvents(0, 10);

        assertTrue(response.isSuccess());
        assertEquals("Events fetched successfully", response.getMessage());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
        assertEquals("Sample Event", response.getData().get(0).getTitle());
    }

    @Test
    void testGetAllEventsExceptionHandling() {
        when(eventRepository.findAllWithDetails(any())).thenThrow(new RuntimeException("Database error"));
        Exception exception = assertThrows(EventServiceException.class, () -> {
            eventService.getAllEvents(0, 10);
        });

        assertEquals("Failed to fetch events", exception.getMessage());
    }
}
