package org.springframework.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Sir.D
 */
@Data
@ConfigurationProperties(prefix = "gzrobot")
public class GzrobotProperties {

    private ValidateProperties validate = new ValidateProperties();

    private HandleDataIntegrityProperties handleDataIntegrity = new HandleDataIntegrityProperties();

    private ImageProperties image = new ImageProperties();

    private EmailProperties email = new EmailProperties();

    private DbProperties db = new DbProperties();

    private InternationalisedProperties internationalise = new InternationalisedProperties();

    private LicenseProperties license = new LicenseProperties();

    private IpWhiteProperties ipWhite = new IpWhiteProperties();

}
