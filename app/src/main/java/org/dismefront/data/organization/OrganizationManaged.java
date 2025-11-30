package org.dismefront.data.organization;

import lombok.Data;
import org.dismefront.data.address.Address;

@Data
public class OrganizationManaged {
    private Long id;
    private String name;
    private Address officialAddress;
    private int annualTurnover;
    private Long employeesCount;
    private String fullName;
    private Address postalAddress;
    private Boolean editable;

    public OrganizationManaged(Long id, String name, Address officialAddress, int annualTurnover, Long employeesCount, String fullName, Address postalAddress, Boolean editable) {
        this.id = id;
        this.name = name;
        this.officialAddress = officialAddress;
        this.annualTurnover = annualTurnover;
        this.employeesCount = employeesCount;
        this.fullName = fullName;
        this.postalAddress = postalAddress;
        this.editable = editable;
    }

    public OrganizationManaged() {}
}
