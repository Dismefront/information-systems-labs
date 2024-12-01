package org.dismefront.api.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dismefront.data.organization.Organization;

@Data
@AllArgsConstructor
public class ProductsByManufacturersCountResponse {
    Organization organization;
    Long count;
}
