package org.lbcc.bms.bms_monolith.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lbcc.bms.bms_monolith.common.entity.BaseAuditingEntity;
import org.lbcc.bms.bms_monolith.entity.enums.OperationalStatus;
import org.lbcc.bms.bms_monolith.entity.enums.VenueType;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venue extends BaseAuditingEntity {

    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer totalSeatingCapacity;

    @Enumerated(EnumType.STRING)
    private VenueType venueType;

    @Enumerated(EnumType.STRING)
    private OperationalStatus operationalStatus;

    @OneToMany(mappedBy = "venue")
    private List<Seat> seats;
}
