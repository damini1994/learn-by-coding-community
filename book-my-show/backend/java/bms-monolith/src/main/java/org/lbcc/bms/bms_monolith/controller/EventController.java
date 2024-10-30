package org.lbcc.bms.bms_monolith.controller;

import org.lbcc.bms.bms_monolith.common.response.ApiListResponse;
import org.lbcc.bms.bms_monolith.common.response.ApiResponse;
import org.lbcc.bms.bms_monolith.entity.Event;
import org.lbcc.bms.bms_monolith.service.IEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/events", produces = {MediaType.APPLICATION_JSON_VALUE})
public class EventController {

    private final IEventService iEventService;

    public EventController(IEventService iEventService) {
        this.iEventService = iEventService;
    }

    @GetMapping()
    public ResponseEntity<ApiListResponse<Event>> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ApiListResponse<Event> response = iEventService.getAllEvents(page, size);
        return ResponseEntity.ok(response);
    }

}
