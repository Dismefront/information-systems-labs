package org.dismefront.api.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductsPageRequest {
    private int page;
    private int size;
}
