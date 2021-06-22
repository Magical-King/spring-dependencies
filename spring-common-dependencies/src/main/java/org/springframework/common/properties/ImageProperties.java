package org.springframework.common.properties;

import lombok.Data;

/**
 * @author Sir.D
 */
@Data
public class ImageProperties {

	private int expire = 60 * 3;

	private int number = 4;

}
