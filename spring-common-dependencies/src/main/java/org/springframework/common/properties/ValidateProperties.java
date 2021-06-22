package org.springframework.common.properties;

import lombok.Data;

/**
 * @author Sir.D
 */
@Data
public class ValidateProperties {

    private String type = "IMAGE";

    private Boolean sm2 = false;

    private Boolean enable = false;

    private Boolean localCache = true;

}
