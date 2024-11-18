package org.dismefront.api.organization;

import lombok.Data;

@Data
public class OrganizationRequest {
    private String name;
    private Long officialAddressId;
    private int annualTurnover;
    private Long employeesCount;
    private String fullName;
    private Long postalAddressId;
}
