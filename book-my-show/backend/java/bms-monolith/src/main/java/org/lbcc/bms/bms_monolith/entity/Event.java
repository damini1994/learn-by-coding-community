package org.lbcc.bms.bms_monolith.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lbcc.bms.bms_monolith.common.entity.BaseAuditingEntity;

import java.time.Instant;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Event extends BaseAuditingEntity {

    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventShow> show;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_type_id")
    private EventType eventType;

    private Instant startDate;

    private String thumbnailUrl;

    private Instant endDate;

}
