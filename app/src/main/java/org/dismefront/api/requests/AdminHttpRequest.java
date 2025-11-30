package org.dismefront.api.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminHttpRequest {
    private Long userId;

    public AdminHttpRequest() {}
}
