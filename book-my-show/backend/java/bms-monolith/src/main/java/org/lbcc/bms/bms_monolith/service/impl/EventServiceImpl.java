package org.lbcc.bms.bms_monolith.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.lbcc.bms.bms_monolith.common.constants.BMSConstants;
import org.lbcc.bms.bms_monolith.common.response.ApiListResponse;
import org.lbcc.bms.bms_monolith.entity.Event;
import org.lbcc.bms.bms_monolith.exception.EventServiceException;
import org.lbcc.bms.bms_monolith.repository.EventRepository;
import org.lbcc.bms.bms_monolith.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventServiceImpl implements IEventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public ApiListResponse<Event> getAllEvents(int page, int size) {
        try {
            Page<Event> eventsPage = eventRepository.findAllWithDetails(PageRequest.of(page, size));
            log.info("Fetched {} events for page {} with size {}", eventsPage.getTotalElements(), page, size);

            return ApiListResponse.<Event>builder()
                    .success(true)
                    .message(BMSConstants.EVENT_SUCCESS_MESSAGE)
                    .setPage(eventsPage)
                    .build();
        } catch (Exception e) {
            log.error("Error fetching events: {}", e.getMessage());
            throw new EventServiceException("Failed to fetch events", e);
        }
    }
}
