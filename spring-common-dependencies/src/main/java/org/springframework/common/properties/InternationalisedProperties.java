package org.springframework.common.properties;

import lombok.Data;

/**
 * @author Sir.D
 */
@Data
public class InternationalisedProperties {
    private String language = "zh";
    private String country = "CN";
}
