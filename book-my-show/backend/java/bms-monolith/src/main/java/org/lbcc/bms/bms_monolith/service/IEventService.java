package org.lbcc.bms.bms_monolith.service;

import org.lbcc.bms.bms_monolith.common.response.ApiListResponse;
import org.lbcc.bms.bms_monolith.entity.Event;

public interface IEventService {

    ApiListResponse<Event> getAllEvents(int page, int size);
}
