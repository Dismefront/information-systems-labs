package org.dismefront.api.address;

import lombok.Data;

@Data
public class AddressRequest {

    private String zipCode;
    private Long townId;  // To pass town id from the request
}
