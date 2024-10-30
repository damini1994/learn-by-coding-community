package org.lbcc.bms.bms_monolith.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lbcc.bms.bms_monolith.common.entity.BaseAuditingEntity;
import org.lbcc.bms.bms_monolith.entity.enums.VendorStatus;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vendor extends BaseAuditingEntity {

    private String name;
    private String contactNumber;
    private String email;
    private String address;
    private String website;

    @Enumerated(EnumType.STRING)
    private VendorStatus status;

    private String registrationDate;
    private String logoUrl;
}
